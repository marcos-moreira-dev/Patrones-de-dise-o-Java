package liminal.integrado;

import java.util.List;
import liminal.integrado.aplicacion.ProcesarTurno;
import liminal.integrado.aplicacion.ResultadoTurno;
import liminal.integrado.dominio.criaturas.Esperar;
import liminal.integrado.dominio.criaturas.MonstruoOscuro;
import liminal.integrado.dominio.criaturas.Patrullar;
import liminal.integrado.dominio.criaturas.Perseguir;
import liminal.integrado.dominio.exploracion.Expedicion;
import liminal.integrado.dominio.exploracion.Explorador;
import liminal.integrado.dominio.mundo.Direccion;
import liminal.integrado.dominio.mundo.Habitacion;
import liminal.integrado.dominio.mundo.MapaLiminal;
import liminal.integrado.dominio.mundo.Posicion;

/** Pruebas del Strategy integrado: misma entidad, mapa y orden de acciones. */
public final class PruebasCriaturas {
    private PruebasCriaturas() {}

    public static void main(String[] args) {
        probarEstrategiasYMapa();
        probarRegistroYTurnos();
        probarPropuestaInvalida();
        probarEsperaSinSalidas();
        System.out.println("OK: Strategy integrada, identidad, rutas, encuentro y rechazos.");
    }

    private static void probarEstrategiasYMapa() {
        Escena escena = prepararEscena();
        MonstruoOscuro monstruo = new MonstruoOscuro("M-01", escena.mapa,
                escena.observacion, new Patrullar());
        igual(escena.cruce, monstruo.avanzar(escena.inicio));
        monstruo.cambiarEstrategia(new Perseguir());
        igual(escena.oficinas, monstruo.avanzar(escena.oficinas));
        monstruo.cambiarEstrategia(new Esperar());
        igual(escena.oficinas, monstruo.avanzar(escena.cruce));
        igual("M-01", monstruo.id());
        esperarFallo(NullPointerException.class, () -> monstruo.cambiarEstrategia(null));

        // La estrategia recibe una lista que no puede modificar ni insertar rutas.
        List<Habitacion> opciones = escena.mapa.destinosDesde(escena.cruce);
        esperarFallo(UnsupportedOperationException.class, () -> opciones.add(escena.inicio));

        // El objetivo no es alcanzable desde la sala de oficinas mediante una salida.
        monstruo.cambiarEstrategia(new Perseguir());
        igual(escena.oficinas, monstruo.avanzar(escena.inicio));
        monstruo.cambiarEstrategia((actual, objetivo, permitidos) -> escena.inicio);
        esperarFallo(IllegalStateException.class, () -> monstruo.avanzar(escena.cruce));
        igual(escena.oficinas, monstruo.ubicacion());

        MapaLiminal extranjero = new MapaLiminal("EXTRANJERO", 3, 2);
        Habitacion duplicado = extranjero.agregarHabitacion(
                "B", "Copia", new Posicion(1, 0));
        esperarFallo(IllegalArgumentException.class, () -> monstruo.avanzar(duplicado));
        esperarFallo(IllegalArgumentException.class,
                () -> new MonstruoOscuro("M-02", escena.mapa, duplicado, new Patrullar()));
        igual(escena.oficinas, monstruo.ubicacion());
    }

    private static void probarRegistroYTurnos() {
        Escena escena = prepararEscena();
        Explorador explorador = new Explorador("EX-01", escena.mapa, escena.inicio);
        Expedicion expedicion = new Expedicion("EXP-01", escena.mapa, explorador, escena.oficinas);
        MonstruoOscuro monstruo = new MonstruoOscuro("M-01", escena.mapa,
                escena.observacion, new Patrullar());
        expedicion.incorporarCriatura(monstruo);
        esperarFallo(IllegalStateException.class, () -> expedicion.incorporarCriatura(monstruo));
        MapaLiminal otro = new MapaLiminal("OTRO", 1, 1);
        Habitacion suya = otro.agregarHabitacion("X", "Habitación", new Posicion(0, 0));
        MonstruoOscuro intruso = new MonstruoOscuro("M-02", otro, suya, new Esperar());
        esperarFallo(IllegalArgumentException.class, () -> expedicion.incorporarCriatura(intruso));
        igual(monstruo, expedicion.criatura());

        ProcesarTurno caso = new ProcesarTurno(expedicion);
        ResultadoTurno invalido = caso.procesar(Direccion.NORTE);
        comprobar(!invalido.aceptado(), "Movimiento inicial debe rechazarse");
        igual(0, expedicion.turnos());
        igual(escena.observacion, monstruo.ubicacion());

        ResultadoTurno primero = caso.procesar(Direccion.ESTE);
        comprobar(primero.aceptado() && primero.mensaje().contains("silueta"),
                "Primer encuentro narrado sin combate");
        igual(escena.cruce, monstruo.ubicacion());
        igual(1, expedicion.turnos());
        igual(explorador, expedicion.explorador());
        esperarFallo(IllegalStateException.class, () -> expedicion.incorporarCriatura(intruso));

        monstruo.cambiarEstrategia(new Perseguir());
        ResultadoTurno segundo = caso.procesar(Direccion.ESTE);
        comprobar(segundo.aceptado() && segundo.finalizada(), "Meta alcanzada");
        igual(escena.oficinas, monstruo.ubicacion());
        igual(2, expedicion.turnos());
        ResultadoTurno posterior = caso.procesar(Direccion.OESTE);
        comprobar(!posterior.aceptado(), "No se puede continuar tras la meta");
        igual(escena.oficinas, monstruo.ubicacion());
        igual(2, posterior.turnos());
    }

    private static void probarPropuestaInvalida() {
        Escena escena = prepararEscena();
        Expedicion expedicion = new Expedicion("EXP-02", escena.mapa,
                new Explorador("EX-02", escena.mapa, escena.inicio), escena.oficinas);
        MonstruoOscuro monstruo = new MonstruoOscuro("M-03", escena.mapa, escena.observacion,
                (actual, objetivo, opciones) -> escena.oficinas);
        expedicion.incorporarCriatura(monstruo);
        ResultadoTurno resultado = new ProcesarTurno(expedicion).procesar(Direccion.ESTE);
        // El explorador tuvo éxito; solo se rechazó la acción independiente de la criatura.
        comprobar(resultado.aceptado(), "No revertir un paso válido por la Strategy");
        comprobar(resultado.mensaje().contains("Movimiento de criatura rechazado"),
                "El error se informa sin ocultar el rechazo de la criatura");
        igual(1, resultado.turnos());
        igual(escena.cruce, expedicion.explorador().ubicacion());
        igual(escena.observacion, monstruo.ubicacion());
    }

    private static void probarEsperaSinSalidas() {
        MapaLiminal solo = new MapaLiminal("SOLO", 1, 1);
        Habitacion a = solo.agregarHabitacion("A", "Sala vacía", new Posicion(0, 0));
        MonstruoOscuro monstruo = new MonstruoOscuro("M-04", solo, a, new Patrullar());
        igual(a, monstruo.avanzar(a));
        monstruo.cambiarEstrategia(new Esperar());
        igual(a, monstruo.avanzar(a));
    }

    private static Escena prepararEscena() {
        MapaLiminal mapa = new MapaLiminal("M", 3, 2);
        Habitacion inicio = mapa.agregarHabitacion("A", "Pasillo", new Posicion(0, 0));
        Habitacion cruce = mapa.agregarHabitacion("B", "Cruce", new Posicion(1, 0));
        Habitacion oficinas = mapa.agregarHabitacion("C", "Oficinas", new Posicion(2, 0));
        Habitacion observacion = mapa.agregarHabitacion("D", "Observación", new Posicion(1, 1));
        mapa.agregarSalida("S-01", "A", "B");
        mapa.agregarSalida("S-02", "B", "A");
        mapa.agregarSalida("S-03", "B", "C");
        mapa.agregarSalida("S-04", "C", "B");
        mapa.agregarSalida("S-05", "B", "D");
        mapa.agregarSalida("S-06", "D", "B");
        return new Escena(mapa, inicio, cruce, oficinas, observacion);
    }

    private record Escena(MapaLiminal mapa, Habitacion inicio, Habitacion cruce,
                          Habitacion oficinas, Habitacion observacion) {}

    private static void comprobar(boolean condicion, String mensaje) {
        if (!condicion) throw new AssertionError(mensaje);
    }
    private static void igual(Object esperado, Object actual) {
        if (!esperado.equals(actual)) throw new AssertionError(esperado + " != " + actual);
    }
    private static void esperarFallo(Class<? extends Throwable> tipo, Runnable accion) {
        try {
            accion.run();
        } catch (Throwable error) {
            if (tipo.isInstance(error)) return;
            throw new AssertionError("Tipo incorrecto: " + error, error);
        }
        throw new AssertionError("Debió rechazarse la acción");
    }
}
