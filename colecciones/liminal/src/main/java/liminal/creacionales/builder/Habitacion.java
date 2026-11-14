package liminal.creacionales.builder;

import java.util.Objects;

/** Producto inmutable del patrón Builder: una habitación configurada. */
public record Habitacion(String id, String ambiente, int luces, boolean conPuerta) {
    public Habitacion {
        Objects.requireNonNull(id);
        Objects.requireNonNull(ambiente);
        if (id.isBlank() || ambiente.isBlank() || luces < 0) {
            throw new IllegalArgumentException("Configuración de habitación inválida");
        }
    }
}
