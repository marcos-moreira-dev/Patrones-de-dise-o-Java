package liminal.creacionales.builder;

import java.util.Objects;

/** Producto de Builder. Clase inmutable con identidad explícita de habitación. */
public final class Habitacion {
    private final String id;
    private final String ambiente;
    private final int luces;
    private final boolean conPuerta;

    public Habitacion(String id, String ambiente, int luces, boolean conPuerta) {
        this.id = Objects.requireNonNull(id, "ID requerido");
        this.ambiente = Objects.requireNonNull(ambiente, "Ambiente requerido");
        if (id.isBlank() || ambiente.isBlank() || luces < 0) {
            throw new IllegalArgumentException("Configuración de habitación inválida");
        }
        this.luces = luces;
        this.conPuerta = conPuerta;
    }

    public String id() { return id; }
    public String ambiente() { return ambiente; }
    public int luces() { return luces; }
    public boolean conPuerta() { return conPuerta; }
}
