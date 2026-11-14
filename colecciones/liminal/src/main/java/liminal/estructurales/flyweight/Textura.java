package liminal.estructurales.flyweight;

import java.util.Objects;

/** Objeto Flyweight inmutable y compartido por referencia entre paredes. */
final class Textura {
    private final String material;
    private final String color;

    Textura(String material, String color) {
        this.material = Objects.requireNonNull(material);
        this.color = Objects.requireNonNull(color);
        if (material.isBlank() || color.isBlank()) {
            throw new IllegalArgumentException("Textura inválida");
        }
    }

    String material() { return material; }
    String color() { return color; }
}
