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

final class Puerta {
    private final String id;
    private final String codigoLlave;
    private EstadoPuerta estado = new EstadoCerrada();

    Puerta(String id, String codigoLlave) {
        this.id = validar(id);
        this.codigoLlave = validar(codigoLlave);
    }
    String id() { return id; }
    String estado() { return estado.nombre(); }
    boolean transitable() { return estado.transitable(); }
    void abrir() { estado = estado.abrir(); }
    void cerrar() { estado = estado.cerrar(); }
    void bloquear() { estado = estado.bloquear(); }
    void desbloquear(String llave) { estado = estado.desbloquear(validar(llave), codigoLlave); }

    private static String validar(String valor) {
        Objects.requireNonNull(valor);
        if (valor.isBlank()) throw new IllegalArgumentException("Texto vacío");
        return valor;
    }
}

interface EstadoPuerta {
    String nombre();
    default boolean transitable() { return false; }
    default EstadoPuerta abrir() { throw invalido("abrir"); }
    default EstadoPuerta cerrar() { throw invalido("cerrar"); }
    default EstadoPuerta bloquear() { throw invalido("bloquear"); }
    default EstadoPuerta desbloquear(String llave, String esperada) {
        throw invalido("desbloquear");
    }
    private IllegalStateException invalido(String accion) {
        return new IllegalStateException("No se puede " + accion + " desde " + nombre());
    }
}

final class EstadoCerrada implements EstadoPuerta {
    public String nombre() { return "CERRADA"; }
    public EstadoPuerta abrir() { return new EstadoAbierta(); }
    public EstadoPuerta bloquear() { return new EstadoBloqueada(); }
}
final class EstadoAbierta implements EstadoPuerta {
    public String nombre() { return "ABIERTA"; }
    public boolean transitable() { return true; }
    public EstadoPuerta cerrar() { return new EstadoCerrada(); }
}
final class EstadoBloqueada implements EstadoPuerta {
    public String nombre() { return "BLOQUEADA"; }
    public EstadoPuerta desbloquear(String llave, String esperada) {
        if (!llave.equals(esperada)) throw new IllegalArgumentException("Llave no autorizada");
        return new EstadoCerrada();
    }
}
