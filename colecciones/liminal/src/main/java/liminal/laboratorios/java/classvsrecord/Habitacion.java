package liminal.laboratorios.java.classvsrecord;

import java.util.Objects;

/** Entidad: el estado de sus luces puede cambiar, pero no cambia su ID. */
public final class Habitacion {
    private final String id;
    private final String ambiente;
    private int lucesEncendidas;

    public Habitacion(String id, String ambiente, int lucesEncendidas) {
        this.id = Objects.requireNonNull(id, "ID requerido");
        this.ambiente = Objects.requireNonNull(ambiente, "Ambiente requerido");
        if (id.isBlank() || ambiente.isBlank()) {
            throw new IllegalArgumentException("ID o ambiente vacío");
        }
        cambiarLuces(lucesEncendidas);
    }

    public String id() { return id; }
    public String ambiente() { return ambiente; }
    public int lucesEncendidas() { return lucesEncendidas; }

    public void cambiarLuces(int cantidad) {
        if (cantidad < 0) throw new IllegalArgumentException("Luces negativas");
        lucesEncendidas = cantidad;
    }

    public boolean mismaIdentidadDeDominio(Habitacion otra) {
        return otra != null && id.equals(otra.id);
    }
}
