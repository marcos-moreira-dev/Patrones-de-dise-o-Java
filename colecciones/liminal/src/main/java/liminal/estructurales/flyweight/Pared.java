package liminal.estructurales.flyweight;

import java.util.Objects;

/** Entidad con ID propio, independiente de la textura compartida. */
final class Pared {
    private final String id;
    private final Textura textura;

    Pared(String id, Textura textura) {
        this.id = Objects.requireNonNull(id);
        this.textura = Objects.requireNonNull(textura);
        if (id.isBlank()) throw new IllegalArgumentException("ID vacío");
    }

    String id() { return id; }
    Textura textura() { return textura; }
}
