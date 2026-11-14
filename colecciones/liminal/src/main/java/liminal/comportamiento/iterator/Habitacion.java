package liminal.comportamiento.iterator;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Set;



record Habitacion(String id, String nombre) {
    Habitacion {
        Objects.requireNonNull(id);
        Objects.requireNonNull(nombre);
        if (id.isBlank() || nombre.isBlank()) throw new IllegalArgumentException("Hab. inválida");
    }
}
