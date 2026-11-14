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


    public static void main(String[] args) { ejecutar(System.out); }
}
