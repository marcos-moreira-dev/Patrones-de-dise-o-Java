package liminal.comportamiento.mediator;

import java.io.PrintStream;
import java.util.Objects;

/** Mediator: un centro coordina sensores y dispositivos sin enlaces entre ellos. */
public final class ClienteMediator {
    private ClienteMediator() {}

    public static void ejecutar(PrintStream out) {
        Luz luz = new Luz("L-01");
        Alarma alarma = new Alarma("A-01");
        CentroControl centro = new CentroControl("S-01", luz, alarma);
        SensorRuido sensor = new SensorRuido("S-01", centro);
        sensor.detectar();
        out.println("Luz: " + (luz.encendida() ? "ENCENDIDA" : "APAGADA"));
        out.println("Avisos de alarma: " + alarma.avisos());
    }

    public static void probar() {
        Luz luz = new Luz("L-01");
        Alarma alarma = new Alarma("A-01");
        CentroControl centro = new CentroControl("S-01", luz, alarma);
        SensorRuido sensor = new SensorRuido("S-01", centro);
        sensor.detectar();
        if (!luz.encendida() || alarma.avisos() != 1) {
            throw new AssertionError("Mediator no coordinó los dispositivos");
        }
        try {
            centro.notificarRuido("S-99");
            throw new AssertionError("No se admite un sensor desconocido");
        } catch (IllegalArgumentException esperado) {
            if (alarma.avisos() != 1) throw new AssertionError("El rechazo no debe notificar");
        }
        try {
            new SensorRuido(" ", centro);
            throw new AssertionError("Un sensor necesita un ID");
        } catch (IllegalArgumentException esperado) {
            // R-01.
        }
        try {
            new Luz("");
            throw new AssertionError("La luz necesita un ID");
        } catch (IllegalArgumentException esperado) {
            // R-01.
        }
    }
    public static void main(String[] args) { ejecutar(System.out); }
}

interface Mediador {
    void notificarRuido(String idSensor);
}
final class SensorRuido {
    private final String id;
    private final Mediador mediador;
    SensorRuido(String id, Mediador mediador) {
        this.id = Objects.requireNonNull(id);
        if (id.isBlank()) throw new IllegalArgumentException("ID vacío");
        this.mediador = Objects.requireNonNull(mediador);
    }
    void detectar() { mediador.notificarRuido(id); }
}
final class Luz {
    private final String id;
    private boolean encendida;
    Luz(String id) {
        this.id = Objects.requireNonNull(id);
        if (id.isBlank()) throw new IllegalArgumentException("ID vacío");
    }
    void encender() { encendida = true; }
    boolean encendida() { return encendida; }
}
final class Alarma {
    private final String id;
    private int avisos;
    Alarma(String id) {
        this.id = Objects.requireNonNull(id);
        if (id.isBlank()) throw new IllegalArgumentException("ID vacío");
    }
    void avisar() { avisos++; }
    int avisos() { return avisos; }
}
final class CentroControl implements Mediador {
    private final String sensorPermitido;
    private final Luz luz;
    private final Alarma alarma;

    CentroControl(String sensorPermitido, Luz luz, Alarma alarma) {
        this.sensorPermitido = Objects.requireNonNull(sensorPermitido);
        if (sensorPermitido.isBlank()) throw new IllegalArgumentException("Sensor sin ID");
        this.luz = Objects.requireNonNull(luz);
        this.alarma = Objects.requireNonNull(alarma);
    }

    public void notificarRuido(String idSensor) {
        if (!sensorPermitido.equals(idSensor)) throw new IllegalArgumentException("Sensor no registrado");
        luz.encender();
        alarma.avisar();
    }
}
