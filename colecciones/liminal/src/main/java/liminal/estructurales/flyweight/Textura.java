package liminal.estructurales.flyweight;

import java.util.Objects;

record Textura(String material, String color) {
    Textura {
        Objects.requireNonNull(material);
        Objects.requireNonNull(color);
        if (material.isBlank() || color.isBlank()) throw new IllegalArgumentException("Textura inválida");
    }
}
