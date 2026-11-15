package liminal.comportamiento.command;

import java.util.Objects;

final class Luz {
    private final String id;
    private boolean encendida;
    Luz(String id) {
        this.id = Objects.requireNonNull(id);
        if (id.isBlank()) throw new IllegalArgumentException("ID vacío");
    }
    void encender() { encendida = true; }
    boolean encendida() { return encendida; }
    String id() { return id; }
}
