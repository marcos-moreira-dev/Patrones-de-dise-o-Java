package liminal.creacionales.factorymethod;

import java.util.Objects;

record MonstruoOscuro(String id, String habitacion) implements Criatura {
    MonstruoOscuro { validarDatos(id, habitacion); }
    public String nombre() { return "Monstruo oscuro"; }
    private static void validarDatos(String id, String habitacion) {
        Objects.requireNonNull(id);
        Objects.requireNonNull(habitacion);
        if (id.isBlank() || habitacion.isBlank()) throw new IllegalArgumentException("ID o ubicación vacíos");
    }
}
