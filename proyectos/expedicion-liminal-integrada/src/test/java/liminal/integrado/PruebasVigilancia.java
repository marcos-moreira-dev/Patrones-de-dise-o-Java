package liminal.integrado;

import java.util.ArrayList;
import java.util.List;
import liminal.integrado.aplicacion.ProcesarTurno;
import liminal.integrado.aplicacion.ResultadoTurno;
import liminal.integrado.dominio.criaturas.Esperar;
import liminal.integrado.dominio.criaturas.Patrullar;
import liminal.integrado.dominio.criaturas.Perseguir;
import liminal.integrado.dominio.exploracion.Expedicion;
import liminal.integrado.dominio.exploracion.Explorador;
import liminal.integrado.dominio.mundo.Direccion;
import liminal.integrado.dominio.mundo.Habitacion;
import liminal.integrado.dominio.mundo.MapaLiminal;
import liminal.integrado.dominio.mundo.Posicion;
import liminal.integrado.dominio.vigilancia.Alarma;
import liminal.integrado.dominio.vigilancia.CentroVigilancia;
import liminal.integrado.dominio.vigilancia.EventoRuido;
import liminal.integrado.dominio.vigilancia.LuzAlerta;
import liminal.integrado.dominio.vigilancia.ObservadorRuido;
import liminal.integrado.dominio.vigilancia.SensorRuido;

/** Observer y Mediator juntos, independientes del cliente de consola. */
public final class PruebasVigilancia {
    private PruebasVigilancia() {}

    public static void main(String[] args) {
        comprobarCentro();
        comprobarSuscripciones();
        comprobarIntegracionConTurnos();
        System.out.println("OK: Observer, Mediator, eventos y límites de aplicación.");
    }

    private static void comprobarCentro() {
        Habitacion sala = habitacionUnica();
        SensorRuido sensor = new SensorRuido("SR-1", sala);
        LuzAlerta luz = new LuzAlerta();
        Alarma alarma = new Alarma();
        CentroVigilancia centro = new CentroVigilancia("CV-1", sensor.id(), luz, alarma);
        sensor.suscribir(centro);
        esperarFallo(IllegalArgumentException.class, () -> sensor.suscribir(centro));
        comprobar(!luz.encendida() && alarma.avisos() == 0, "Sin señal inicial");
        sensor.detectarMovimiento(sala);
        comprobar(luz.encendida() && alarma.avisos() == 1, "Centro coordina luz y alarma");
        igual(new EventoRuido("SR-1", sala.id()), alarma.ultimo());

        Habitacion distinta = new MapaLiminal("OTRO", 1, 1)
                .agregarHabitacion("H", "Otra sala", new Posicion(0, 0));
        esperarFallo(IllegalArgumentException.class, () -> sensor.detectarMovimiento(distinta));
        igual(1, alarma.avisos());
        esperarFallo(IllegalArgumentException.class,
                () -> centro.alDetectar(new EventoRuido("SR-OTRO", sala.id())));
        igual(1, alarma.avisos());
    }

    private static void comprobarSuscripciones() {
        Habitacion sala = habitacionUnica();
        SensorRuido sensor = new SensorRuido("S", sala);
        List<String> llamadas = new ArrayList<>();
        ObservadorRuido tercero = observador("C", evento -> llamadas.add("C"));
        sensor.suscribir(observador("A", evento -> {
            llamadas.add("A");
            if (llamadas.size() == 1) {
                sensor.desuscribir("B");
                sensor.suscribir(tercero);
            }
        }));
        sensor.suscribir(observador("B", evento -> llamadas.add("B")));
        sensor.detectarMovimiento(sala);
        igual(List.of("A", "B"), llamadas); // B sigue en la ronda actual.
        sensor.detectarMovimiento(sala);
        igual(List.of("A", "B", "A", "C"), llamadas);
        igual(2, sensor.cantidadObservadores());
    }

    private static void comprobarIntegracionConTurnos() {
        MapaLiminal mapa = new MapaLiminal("M", 3, 2);
        Habitacion a = mapa.agregarHabitacion("A", "Pasillo", new Posicion(0, 0));
        mapa.agregarHabitacion("B", "Cruce", new Posicion(1, 0));
        Habitacion c = mapa.agregarHabitacion("C", "Oficina", new Posicion(2, 0));
        Habitacion d = mapa.agregarHabitacion("D", "Observación", new Posicion(1, 1));
        mapa.agregarSalida("S-1", "A", "B");
        mapa.agregarSalida("S-2", "B", "C");
        mapa.agregarSalida("S-3", "D", "B");
        Expedicion sesion = new Expedicion("EXP", mapa, new Explorador("EX", mapa, a), c);
        sesion.incorporarCriatura("M", d, new Patrullar());

        SensorRuido sensor = new SensorRuido("SR-C", c);
        LuzAlerta luz = new LuzAlerta();
        Alarma alarma = new Alarma();
        sensor.suscribir(new CentroVigilancia("CV", sensor.id(), luz, alarma));
        ProcesarTurno turno = new ProcesarTurno(sesion, sensor);

        comprobar(!turno.procesar(Direccion.NORTE).aceptado(), "Rechazo sin evento");
        igual(0, alarma.avisos());
        comprobar(turno.procesar(Direccion.ESTE).aceptado(), "Primera entrada");
        igual(0, alarma.avisos()); // Monstruo en B, sensor en C.

        sesion.cambiarEstrategiaCriatura(new Perseguir());
        ResultadoTurno segundo = turno.procesar(Direccion.ESTE);
        comprobar(segundo.finalizada() && segundo.aceptado(), "Meta con sensor");
        comprobar(segundo.mensaje().contains("ruido detectado"), "Se notifica tras entrar");
        igual(1, alarma.avisos());
        comprobar(luz.encendida(), "Se enciende la luz");
        igual(c.id(), alarma.ultimo().habitacionId());
        igual(2, sesion.turnos());
        comprobar(!turno.procesar(Direccion.OESTE).aceptado(), "Finalizada");
        igual(1, alarma.avisos());

        // Una Strategy sin movimiento no dispara el sensor, aunque la criatura esté en C.
        MapaLiminal solo = new MapaLiminal("M2", 2, 1);
        Habitacion uno = solo.agregarHabitacion("U", "Inicio", new Posicion(0, 0));
        Habitacion dos = solo.agregarHabitacion("V", "Meta", new Posicion(1, 0));
        solo.agregarSalida("S-4", "U", "V");
        Expedicion espera = new Expedicion("EXP2", solo, new Explorador("EX2", solo, uno), dos);
        espera.incorporarCriatura("M2", dos, new Esperar());
        SensorRuido sensorEspera = new SensorRuido("SR-E", dos);
        Alarma alarmaEspera = new Alarma();
        sensorEspera.suscribir(new CentroVigilancia(
                "CV-E", sensorEspera.id(), new LuzAlerta(), alarmaEspera));
        new ProcesarTurno(espera, sensorEspera).procesar(Direccion.ESTE);
        igual(0, alarmaEspera.avisos());
    }

    private static Habitacion habitacionUnica() {
        MapaLiminal mapa = new MapaLiminal("MAP", 1, 1);
        return mapa.agregarHabitacion("H", "Oficina", new Posicion(0, 0));
    }

    private static ObservadorRuido observador(String id,
                                              java.util.function.Consumer<EventoRuido> accion) {
        return new ObservadorRuido() {
            public String id() { return id; }
            public void alDetectar(EventoRuido evento) { accion.accept(evento); }
        };
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
        } catch (Throwable error) {
            if (tipo.isInstance(error)) return;
            throw new AssertionError("Tipo incorrecto: " + error, error);
        }
        throw new AssertionError("Debió rechazarse la acción");
    }
}
