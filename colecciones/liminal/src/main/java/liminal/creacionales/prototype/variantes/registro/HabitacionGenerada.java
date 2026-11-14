package liminal.creacionales.prototype.variantes.registro;

import java.util.Objects;

/** Entidad creada a partir de una plantilla. */
final class HabitacionGenerada {
    private final String id;
    private final String ambiente;
    private final int luces;
    private final boolean conPuerta;

    HabitacionGenerada(String id, String ambiente, int luces, boolean conPuerta) {
        this.id = Objects.requireNonNull(id, "ID requerido");
        this.ambiente = Objects.requireNonNull(ambiente, "Ambiente requerido");
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
