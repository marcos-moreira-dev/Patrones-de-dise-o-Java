package liminal.estructurales.composite;

import java.util.Objects;

final class LuzIndividual implements Encendible {
    private final String id;
    private boolean encendida;

    LuzIndividual(String id) {
        this.id = Objects.requireNonNull(id);
        if (id.isBlank()) throw new IllegalArgumentException("ID vacío");
    }
    String id() { return id; }
    boolean encendida() { return encendida; }
    public void encender() { encendida = true; }
    public void apagar() { encendida = false; }
}
