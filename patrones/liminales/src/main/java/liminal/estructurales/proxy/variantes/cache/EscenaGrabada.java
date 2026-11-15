package liminal.estructurales.proxy.variantes.cache;

import java.util.Objects;

/** Escena ficticia con revisión discreta, sin reloj ni red. */
final class EscenaGrabada {
    private final String nombre;
    private int revision;

    EscenaGrabada(String nombre) {
        this.nombre = Objects.requireNonNull(nombre);
        if (nombre.isBlank()) throw new IllegalArgumentException("Nombre vacío");
    }

    String nombre() { return nombre; }
    int revision() { return revision; }

    void actualizar() {
        revision = Math.addExact(revision, 1);
    }
}
