package liminal.integrado;

import java.util.ArrayList;
import java.util.List;
import liminal.integrado.aplicacion.ProcesarTurno;
import liminal.integrado.aplicacion.ResultadoTurno;
import liminal.integrado.dominio.exploracion.Expedicion;
import liminal.integrado.dominio.exploracion.Explorador;
import liminal.integrado.dominio.mundo.Direccion;
import liminal.integrado.dominio.mundo.Habitacion;
import liminal.integrado.dominio.mundo.MapaLiminal;
import liminal.integrado.dominio.mundo.Posicion;

/** Pruebas independientes de la presentación y de las demás colecciones GoF. */
public final class PruebasExpedicion {
    private PruebasExpedicion() {}

    public static void main(String[] args) {
        verificarMapa();
        verificarIdentidadYDireccion();
        verificarTurnosYObjetivo();
        comprobar(ejecutarGuion().equals(ejecutarGuion()), "El guion es reproducible");
        System.out.println("OK: dominio integrado, rechazos, salida dirigida y expedición reproducible.");
    }

    private static void verificarMapa() {
        esperarFallo(IllegalArgumentException.class, () -> new MapaLiminal("M", 0, 3));
        esperarFallo(IllegalArgumentException.class, () -> new Posicion(-1, 0));
        MapaLiminal mapa = new MapaLiminal("M", 4, 3);
        Habitacion a = mapa.agregarHabitacion("A", "Pasillo", new Posicion(0, 1));
        Habitacion b = mapa.agregarHabitacion("B", "Oficina", new Posicion(1, 1));
        mapa.agregarHabitacion("C", "Señal", new Posicion(2, 0));
        esperarFallo(IllegalArgumentException.class,
                () -> mapa.agregarHabitacion("A", "Duplicada", new Posicion(3, 2)));
        esperarFallo(IllegalArgumentException.class,
                () -> mapa.agregarHabitacion("D", "Celda ocupada", new Posicion(0, 1)));
        esperarFallo(IllegalArgumentException.class,
                () -> mapa.agregarHabitacion("D", "Fuera", new Posicion(4, 1)));
        igual(3, mapa.cantidadHabitaciones());

        mapa.agregarSalida("S-01", "A", "B");
        esperarFallo(IllegalArgumentException.class,
                () -> mapa.agregarSalida("S-02", "A", "C"));
        esperarFallo(IllegalArgumentException.class,
                () -> mapa.agregarSalida("S-01", "B", "A"));
        esperarFallo(IllegalArgumentException.class,
                () -> mapa.agregarSalida("S-02", "A", "B"));
        esperarFallo(IllegalArgumentException.class,
                () -> mapa.agregarSalida("S-03", "B", "Inexistente"));
        igual(1, mapa.cantidadSalidas());
        igual(b, mapa.salidaDesde(a, Direccion.ESTE).destino());
        esperarFallo(IllegalStateException.class, () -> mapa.salidaDesde(b, Direccion.OESTE));
    }

    private static void verificarIdentidadYDireccion() {
        MapaLiminal mapa = new MapaLiminal("M-1", 2, 1);
        Habitacion a = mapa.agregarHabitacion("A", "Pasillo", new Posicion(0, 0));
        Habitacion b = mapa.agregarHabitacion("B", "Pasillo", new Posicion(1, 0));
        igual(new Posicion(0, 0), a.posicion());
        comprobar(a != b, "Habitaciones con mismo nombre son entidades distintas");
        Explorador explorador = new Explorador("E", mapa, b);
        esperarFallo(IllegalStateException.class, () -> explorador.mover(Direccion.OESTE));
        igual(b, explorador.ubicacion());
        mapa.agregarSalida("S-1", "B", "A");
        igual(a, explorador.mover(Direccion.OESTE));
        igual(a, explorador.ubicacion());

        MapaLiminal otro = new MapaLiminal("M-2", 2, 1);
        Habitacion otraA = otro.agregarHabitacion("A", "Pasillo", new Posicion(0, 0));
        esperarFallo(IllegalArgumentException.class,
                () -> new Explorador("E-2", mapa, otraA));
        esperarFallo(IllegalArgumentException.class,
                () -> new Expedicion("EXP", otro, explorador, otraA));
    }

    private static void verificarTurnosYObjetivo() {
        Expedicion expedicion = prepararEscenario();
        Explorador mismoExplorador = expedicion.explorador();
        ProcesarTurno proceso = new ProcesarTurno(expedicion);
        ResultadoTurno invalido = proceso.procesar(Direccion.NORTE);
        comprobar(!invalido.aceptado(), "No hay salida al norte desde H-01");
        igual(0, expedicion.turnos());
        igual("H-01", mismoExplorador.ubicacion().id());
        List<Direccion> camino = List.of(Direccion.ESTE, Direccion.ESTE,
                Direccion.NORTE, Direccion.ESTE);
        int i = 0;
        for (Direccion direccion : camino) {
            i++;
            ResultadoTurno resultado = proceso.procesar(direccion);
            comprobar(resultado.aceptado(), "Paso permitido " + i);
            igual(i, resultado.turnos());
        }
        comprobar(expedicion.explorador() == mismoExplorador, "Conserva identidad");
        comprobar(expedicion.finalizada(), "Llegó a la meta");
        igual("H-05", mismoExplorador.ubicacion().id());
        ResultadoTurno posterior = proceso.procesar(Direccion.OESTE);
        comprobar(!posterior.aceptado() && posterior.finalizada(),
                "Después de completar, no se mueve");
        igual(4, posterior.turnos());
        igual("H-05", posterior.habitacionId());
        esperarFallo(NullPointerException.class, () -> proceso.procesar(null));
    }

    private static List<ResultadoTurno> ejecutarGuion() {
        ProcesarTurno proceso = new ProcesarTurno(prepararEscenario());
        List<ResultadoTurno> resultados = new ArrayList<>();
        for (Direccion orden : List.of(Direccion.NORTE, Direccion.ESTE,
                Direccion.ESTE, Direccion.NORTE, Direccion.ESTE, Direccion.OESTE)) {
            resultados.add(proceso.procesar(orden));
        }
        return List.copyOf(resultados);
    }

    private static Expedicion prepararEscenario() {
        MapaLiminal mapa = new MapaLiminal("MAP", 4, 3);
        Habitacion inicio = mapa.agregarHabitacion("H-01", "Pasillo amarillo", new Posicion(0, 1));
        mapa.agregarHabitacion("H-02", "Cruce de fluorescentes", new Posicion(1, 1));
        mapa.agregarHabitacion("H-03", "Oficinas silenciosas", new Posicion(2, 1));
        mapa.agregarHabitacion("H-04", "Habitación de la señal", new Posicion(2, 0));
        Habitacion meta = mapa.agregarHabitacion("H-05", "Salida segura", new Posicion(3, 0));
        mapa.agregarHabitacion("H-06", "Puesto de observación", new Posicion(1, 0));
        mapa.agregarSalida("S-01", "H-01", "H-02");
        mapa.agregarSalida("S-02", "H-02", "H-01");
        mapa.agregarSalida("S-03", "H-02", "H-03");
        mapa.agregarSalida("S-04", "H-03", "H-02");
        mapa.agregarSalida("S-05", "H-03", "H-04");
        mapa.agregarSalida("S-06", "H-04", "H-05");
        mapa.agregarSalida("S-07", "H-02", "H-06");
        mapa.agregarSalida("S-08", "H-06", "H-02");
        return new Expedicion("EXP", mapa, new Explorador("EX", mapa, inicio), meta);
    }

    private static void comprobar(boolean condicion, String mensaje) {
        if (!condicion) throw new AssertionError(mensaje);
    }
    private static void igual(Object esperado, Object actual) {
        if (!esperado.equals(actual)) throw new AssertionError(esperado + " != " + actual);
    }
    private static void esperarFallo(Class<? extends Throwable> tipo, Runnable accion) {
        try {
            accion.run();
        } catch (Throwable e) {
            if (tipo.isInstance(e)) return;
            throw new AssertionError("Tipo incorrecto: " + e, e);
        }
        throw new AssertionError("Debió rechazarse la acción");
    }
}

