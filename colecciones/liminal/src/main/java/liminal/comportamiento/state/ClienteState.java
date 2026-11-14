package liminal.comportamiento.state;

import java.io.PrintStream;
import java.util.Objects;

/** Demostración State: una puerta delega acciones según su estado. */
public final class ClienteState {
    private ClienteState() {}

    public static void ejecutar(PrintStream out) {
        Puerta puerta = new Puerta("P-01", "LLAVE-ROJA");
        out.println("Puerta P-01: " + puerta.estado());
        puerta.bloquear();
        out.println("Bloquear -> " + puerta.estado());
        try {
            puerta.abrir();
        } catch (IllegalStateException e) {
            out.println("Abrir bloqueada -> denegado");
        }
        puerta.desbloquear("LLAVE-ROJA");
        out.println("Desbloquear -> " + puerta.estado());
        puerta.abrir();
        out.println("Abrir -> " + puerta.estado());
        out.println("¿Se puede pasar? " + (puerta.transitable() ? "Sí" : "No"));
    }

    public static void main(String[] args) { ejecutar(System.out); }
}
