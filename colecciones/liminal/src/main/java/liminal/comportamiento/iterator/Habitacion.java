package liminal.comportamiento.iterator;

import java.util.Objects;

/** Entidad recorrida por Iterator; su ID no es un valor de igualdad automática. */
final class Habitacion {
    private final String id;
    private final String nombre;

    Habitacion(String id, String nombre) {
        this.id = Objects.requireNonNull(id);
        this.nombre = Objects.requireNonNull(nombre);
        if (id.isBlank() || nombre.isBlank()) throw new IllegalArgumentException("Hab. inválida");
    }

    String id() { return id; }
    String nombre() { return nombre; }
}
