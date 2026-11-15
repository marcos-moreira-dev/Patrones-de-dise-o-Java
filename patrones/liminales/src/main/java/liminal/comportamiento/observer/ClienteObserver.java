package liminal.comportamiento.observer;

import java.io.PrintStream;

/** Observer: un interruptor notifica a luz y alarma sin poseerlas. */
public final class ClienteObserver {
    private ClienteObserver() {}

    public static void main(String[] args) { ejecutar(System.out); }

    public static void ejecutar(PrintStream out) {
        Interruptor interruptor = new Interruptor("I-01");
        Luz luz = new Luz("L-01");
        Alarma alarma = new Alarma("A-01");

        suscribirDispositivos(interruptor, luz, alarma);
        encender(interruptor, luz, alarma, out);
        apagar(interruptor, luz, out);
    }

    private static void suscribirDispositivos(
            Interruptor interruptor, Luz luz, Alarma alarma) {
        interruptor.suscribir(luz);
        interruptor.suscribir(alarma);
    }

    private static void encender(
            Interruptor interruptor, Luz luz, Alarma alarma, PrintStream out) {
        interruptor.pulsar();
        out.println("Pulsar -> " + (interruptor.activado() ? "ENCENDIDO" : "APAGADO"));
        out.println("Luz -> " + (luz.encendida() ? "ENCENDIDA" : "APAGADA"));
        out.println("Avisos de alarma -> " + alarma.avisos());
    }

    private static void apagar(Interruptor interruptor, Luz luz, PrintStream out) {
        interruptor.pulsar();
        out.println("Volver a pulsar -> " +
                (interruptor.activado() ? "ENCENDIDO" : "APAGADO"));
        out.println("Luz -> " + (luz.encendida() ? "ENCENDIDA" : "APAGADA"));
    }
}
