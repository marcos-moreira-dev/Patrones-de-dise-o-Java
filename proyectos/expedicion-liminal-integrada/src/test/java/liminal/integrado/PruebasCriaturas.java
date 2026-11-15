package liminal.integrado;

import java.util.List;
import liminal.integrado.aplicacion.ProcesarTurno;
import liminal.integrado.aplicacion.ResultadoTurno;
import liminal.integrado.dominio.criaturas.Esperar;
import liminal.integrado.dominio.criaturas.EstadoCriatura;
import liminal.integrado.dominio.criaturas.MonstruoOscuro;
import liminal.integrado.dominio.criaturas.Patrullar;
import liminal.integrado.dominio.criaturas.Perseguir;
import liminal.integrado.dominio.exploracion.Expedicion;
import liminal.integrado.dominio.exploracion.Explorador;
import liminal.integrado.dominio.mundo.Direccion;
import liminal.integrado.dominio.mundo.Habitacion;
import liminal.integrado.dominio.mundo.MapaLiminal;
import liminal.integrado.dominio.mundo.Posicion;

/** Pruebas de Strategy, límites de sesión y rutas del mapa. */
public final class PruebasCriaturas {
    private PruebasCriaturas() {}

    public static void main(String[] args) {
        probarEstrategiasAisladas();
        probarSesionProtegida();
        probarPropuestaInvalida();
        probarEsperaSinSalidas();
        System.out.println("OK: Strategy, identidad, encapsulamiento, rutas y turnos.");
    }

    private static void probarEstrategiasAisladas() {
        Escena e = escena();
        MonstruoOscuro m = new MonstruoOscuro("M-01", e.mapa, e.observacion, new Patrullar());
        igual(e.cruce, m.avanzar(e.inicio));
        m.cambiarEstrategia(new Perseguir());
        igual(e.oficinas, m.avanzar(e.oficinas));
        m.cambiarEstrategia(new Esperar());
        igual(e.oficinas, m.avanzar(e.cruce));
        igual("M-01", m.id());
        esperarFallo(NullPointerException.class, () -> m.cambiarEstrategia(null));
        List<Habitacion> rutas = e.mapa.destinosDesde(e.cruce);
        esperarFallo(UnsupportedOperationException.class, () -> rutas.add(e.inicio));
        m.cambiarEstrategia(new Perseguir());
        igual(e.oficinas, m.avanzar(e.inicio));
        m.cambiarEstrategia((actual, objetivo, opciones) -> e.inicio);
        esperarFallo(IllegalStateException.class, () -> m.avanzar(e.cruce));
        igual(e.oficinas, m.ubicacion());

        MapaLiminal otro = new MapaLiminal("OTRO", 1, 1);
        Habitacion duplicada = otro.agregarHabitacion("A", "Copia", new Posicion(0, 0));
        esperarFallo(IllegalArgumentException.class, () -> m.avanzar(duplicada));
        esperarFallo(IllegalArgumentException.class,
                () -> new MonstruoOscuro("M-02", e.mapa, duplicada, new Patrullar()));
    }

    private static void probarSesionProtegida() {
        Escena e = escena();
        Explorador explorador = new Explorador("EX", e.mapa, e.inicio);
        Expedicion sesion = new Expedicion("EXP", e.mapa, explorador, e.oficinas);
        comprobar(sesion.estadoCriatura().isEmpty(), "Sin monstruo inicialmente");
        sesion.incorporarCriatura("M-01", e.observacion, new Patrullar());
        EstadoCriatura fotografiaInicial = sesion.estadoCriatura().orElseThrow();
        igual(e.observacion, fotografiaInicial.ubicacion());
        igual("M-01", fotografiaInicial.id());

        esperarFallo(IllegalStateException.class,
                () -> sesion.incorporarCriatura("M-02", e.observacion, new Esperar()));
        MapaLiminal otro = new MapaLiminal("EXTRAÑO", 2, 1);
        Habitacion ajena = otro.agregarHabitacion("A", "Otro mapa", new Posicion(0, 0));
        esperarFallo(IllegalArgumentException.class,
                () -> sesion.incorporarCriatura("M-02", ajena, new Esperar()));
        esperarFallo(NullPointerException.class, () -> sesion.cambiarEstrategiaCriatura(null));

        ProcesarTurno caso = new ProcesarTurno(sesion);
        ResultadoTurno rechazo = caso.procesar(Direccion.NORTE);
        comprobar(!rechazo.aceptado(), "Paso inicial rechazado");
        igual(0, sesion.turnos());
        igual(e.observacion, sesion.estadoCriatura().orElseThrow().ubicacion());

        ResultadoTurno primero = caso.procesar(Direccion.ESTE);
        comprobar(primero.aceptado() && primero.mensaje().contains("silueta"),
                "Encuentro sin combate");
        igual(1, sesion.turnos());
        igual(e.cruce, sesion.estadoCriatura().orElseThrow().ubicacion());
        igual(e.observacion, fotografiaInicial.ubicacion()); // La vista previa NO se actualiza.
        comprobar(fotografiaInicial != sesion.estadoCriatura().orElseThrow(),
                "Cada consulta devuelve un dato, no la entidad");
        comprobar(sesion.explorador() == explorador, "Explorador con identidad estable");

        sesion.cambiarEstrategiaCriatura(new Perseguir());
        ResultadoTurno segundo = caso.procesar(Direccion.ESTE);
        comprobar(segundo.aceptado() && segundo.finalizada(), "Meta alcanzada");
        igual(e.oficinas, sesion.estadoCriatura().orElseThrow().ubicacion());
        igual(2, sesion.turnos());
        esperarFallo(IllegalStateException.class,
                () -> sesion.cambiarEstrategiaCriatura(new Esperar()));
        comprobar(!caso.procesar(Direccion.OESTE).aceptado(), "Turno posterior rechazado");
        igual(2, sesion.turnos());

        Expedicion tardia = new Expedicion("EXP-T", e.mapa,
                new Explorador("EX-T", e.mapa, e.inicio), e.oficinas);
        tardia.avanzar(Direccion.ESTE);
        esperarFallo(IllegalStateException.class,
                () -> tardia.incorporarCriatura("M-X", e.observacion, new Esperar()));
    }

    private static void probarPropuestaInvalida() {
        Escena e = escena();
        Expedicion sesion = new Expedicion("EXP-X", e.mapa,
                new Explorador("EX-X", e.mapa, e.inicio), e.oficinas);
        sesion.incorporarCriatura("M-X", e.observacion,
                (actual, objetivo, rutas) -> e.oficinas);
        ResultadoTurno resultado = new ProcesarTurno(sesion).procesar(Direccion.ESTE);
        comprobar(resultado.aceptado(), "El paso del explorador ya fue válido");
        comprobar(resultado.mensaje().contains("Movimiento de criatura rechazado"),
                "Se informa fallo secundario");
        igual(1, sesion.turnos());
        igual(e.cruce, sesion.explorador().ubicacion());
        igual(e.observacion, sesion.estadoCriatura().orElseThrow().ubicacion());
    }

    private static void probarEsperaSinSalidas() {
        MapaLiminal mapa = new MapaLiminal("SOLO", 1, 1);
        Habitacion a = mapa.agregarHabitacion("A", "Sala", new Posicion(0, 0));
        MonstruoOscuro monstruo = new MonstruoOscuro("M-04", mapa, a, new Patrullar());
        igual(a, monstruo.avanzar(a));
        monstruo.cambiarEstrategia(new Esperar());
        igual(a, monstruo.avanzar(a));
    }

    private static Escena escena() {
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
