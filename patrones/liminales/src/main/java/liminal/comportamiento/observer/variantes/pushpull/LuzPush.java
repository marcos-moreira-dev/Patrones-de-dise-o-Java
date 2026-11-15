package liminal.comportamiento.observer.variantes.pushpull;

import java.util.Objects;

/** El observador recibe directamente el estado nuevo. */
final class LuzPush implements ObservadorPush {
    private final String id;
    private boolean encendida;
    private int avisos;

    LuzPush(String id) { this.id = Objects.requireNonNull(id); }
    public String id() { return id; }

    public void actualizar(boolean nuevoEstado) {
        encendida = nuevoEstado;
        avisos++;
    }

    boolean encendida() { return encendida; }
    int avisos() { return avisos; }
}
