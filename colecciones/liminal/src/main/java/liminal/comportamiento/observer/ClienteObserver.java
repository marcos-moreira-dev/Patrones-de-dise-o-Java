package liminal.comportamiento.observer;

import java.io.PrintStream;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/** Observer: un interruptor notifica a luz y alarma. */
public final class ClienteObserver {
    private ClienteObserver() {}

    public static void ejecutar(PrintStream out) {
        Interruptor interruptor = new Interruptor("I-01");
        Luz luz = new Luz("L-01");
        Alarma alarma = new Alarma("A-01");
        interruptor.suscribir(luz);
        interruptor.suscribir(alarma);
        interruptor.pulsar();
        out.println("Pulsar -> " + (interruptor.activado() ? "ENCENDIDO" : "APAGADO"));
        out.println("Luz -> " + (luz.encendida() ? "ENCENDIDA" : "APAGADA"));
        out.println("Avisos de alarma -> " + alarma.avisos());
        interruptor.pulsar();
        out.println("Volver a pulsar -> " + (interruptor.activado() ? "ENCENDIDO" : "APAGADO"));
        out.println("Luz -> " + (luz.encendida() ? "ENCENDIDA" : "APAGADA"));
    }

    public static void main(String[] args) { ejecutar(System.out); }
}
