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
