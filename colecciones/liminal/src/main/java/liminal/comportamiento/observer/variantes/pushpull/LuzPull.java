package liminal.comportamiento.observer.variantes.pushpull;

import java.util.Objects;

/** El observador consulta el estado del mismo emisor del evento. */
final class LuzPull implements ObservadorPull {
    private final String id;
    private boolean encendida;
    private int avisos;

    LuzPull(String id) { this.id = Objects.requireNonNull(id); }
    public String id() { return id; }

    public void notificarCambio(Interruptor emisor) {
        encendida = emisor.activado();
        avisos++;
    }

    boolean encendida() { return encendida; }
    int avisos() { return avisos; }
}
