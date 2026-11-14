package liminal.creacionales.factorymethod;

import java.util.Objects;

record MonstruoSilencioso(String id, String habitacion) implements Criatura {
    MonstruoSilencioso {
        Objects.requireNonNull(id);
        Objects.requireNonNull(habitacion);
        if (id.isBlank() || habitacion.isBlank()) throw new IllegalArgumentException("ID o ubicación vacíos");
    }
    public String nombre() { return "Monstruo silencioso"; }
}
