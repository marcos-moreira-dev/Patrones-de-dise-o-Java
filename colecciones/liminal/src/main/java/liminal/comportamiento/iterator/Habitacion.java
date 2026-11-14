package liminal.comportamiento.iterator;

import java.util.Objects;

record Habitacion(String id, String nombre) {
    Habitacion {
        Objects.requireNonNull(id);
        Objects.requireNonNull(nombre);
        if (id.isBlank() || nombre.isBlank()) throw new IllegalArgumentException("Hab. inválida");
    }
}
