package liminal.laboratorios.java.classvsrecord;

import java.util.Objects;

/** Objeto-valor usado como clave estable de una caché de texturas. */
public record ClaveTextura(String material, String patron) {
    public ClaveTextura {
        Objects.requireNonNull(material, "Material requerido");
        Objects.requireNonNull(patron, "Patrón requerido");
        if (material.isBlank() || patron.isBlank()) {
            throw new IllegalArgumentException("Una textura necesita material y patrón");
        }
    }
}
