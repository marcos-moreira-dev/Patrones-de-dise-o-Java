package liminal.comportamiento.observer.variantes.pushpull;

import java.io.PrintStream;

/** Dos tipos de notificación para el mismo interruptor ficticio. */
public final class ClientePushPull {
    private ClientePushPull() {}

    public static void main(String[] args) { ejecutar(System.out); }

    public static void ejecutar(PrintStream out) {
        Interruptor interruptor = new Interruptor("I-01");
        LuzPush luzPush = new LuzPush("L-01");
        LuzPull luzPull = new LuzPull("L-02");
        interruptor.suscribir(luzPush);
        interruptor.suscribir(luzPull);
        interruptor.pulsar();
        out.println("PUSH tras pulsar: " + (luzPush.encendida() ? "ENCENDIDA" : "APAGADA"));
        out.println("PULL tras pulsar: " + (luzPull.encendida() ? "ENCENDIDA" : "APAGADA"));
        interruptor.retirar(luzPull.id());
        interruptor.pulsar();
        out.println("PUSH después: " + (luzPush.encendida() ? "ENCENDIDA" : "APAGADA"));
        out.println("PULL sin suscripción: " + (luzPull.encendida() ? "ENCENDIDA" : "APAGADA"));
    }
}
