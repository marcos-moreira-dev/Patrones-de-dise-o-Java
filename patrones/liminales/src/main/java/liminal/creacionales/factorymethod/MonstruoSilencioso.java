package liminal.creacionales.factorymethod;

import java.util.Objects;

/** Segunda clase concreta, no un valor de datos. */
final class MonstruoSilencioso implements Criatura {
    private final String id;
    private final String habitacion;

    MonstruoSilencioso(String id, String habitacion) {
        this.id = Objects.requireNonNull(id);
        this.habitacion = Objects.requireNonNull(habitacion);
        if (id.isBlank() || habitacion.isBlank()) {
            throw new IllegalArgumentException("ID o ubicación vacíos");
        }
    }

    public String id() { return id; }
    public String habitacion() { return habitacion; }
    public String nombre() { return "Monstruo silencioso"; }
}
