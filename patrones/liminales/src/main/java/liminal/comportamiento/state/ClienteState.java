package liminal.comportamiento.state;

import java.io.PrintStream;

/** State: una puerta delega acciones al objeto que representa su estado. */
public final class ClienteState {
    private ClienteState() {}

    public static void main(String[] args) { ejecutar(System.out); }

    public static void ejecutar(PrintStream out) {
        Puerta puerta = new Puerta("P-01", "LLAVE-ROJA");
        mostrarEstadoInicial(puerta, out);
        bloquearEIntentarAbrir(puerta, out);
        desbloquearYAbrir(puerta, out);
    }

    private static void mostrarEstadoInicial(Puerta puerta, PrintStream out) {
        out.println("Puerta P-01: " + puerta.estado());
    }

    private static void bloquearEIntentarAbrir(Puerta puerta, PrintStream out) {
        puerta.bloquear();
        out.println("Bloquear -> " + puerta.estado());
        try {
            puerta.abrir();
        } catch (IllegalStateException e) {
            out.println("Abrir bloqueada -> denegado");
        }
    }

    private static void desbloquearYAbrir(Puerta puerta, PrintStream out) {
        puerta.desbloquear("LLAVE-ROJA");
        out.println("Desbloquear -> " + puerta.estado());
        puerta.abrir();
        out.println("Abrir -> " + puerta.estado());
        out.println("¿Se puede pasar? " + (puerta.transitable() ? "Sí" : "No"));
    }
}
