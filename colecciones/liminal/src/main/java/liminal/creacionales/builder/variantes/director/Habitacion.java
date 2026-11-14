package liminal.creacionales.builder.variantes.director;

import java.util.Objects;

/** Producto de la construcción; es una clase, no un record. */
final class Habitacion {
    private final String id;
    private final String ambiente;
    private final int luces;
    private final boolean conPuerta;

    Habitacion(String id, String ambiente, int luces, boolean conPuerta) {
        this.id = Objects.requireNonNull(id);
        this.ambiente = Objects.requireNonNull(ambiente);
        if (id.isBlank() || ambiente.isBlank() || luces < 0) {
            throw new IllegalArgumentException("Habitación inválida");
        }
        this.luces = luces;
        this.conPuerta = conPuerta;
    }

    String id() { return id; }
    String ambiente() { return ambiente; }
    int luces() { return luces; }
    boolean conPuerta() { return conPuerta; }
}
