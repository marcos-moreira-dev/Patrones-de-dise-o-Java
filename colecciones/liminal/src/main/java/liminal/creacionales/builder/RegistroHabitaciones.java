package liminal.creacionales.builder;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/** Regla R-01: registra IDs únicos; no forma parte del mecanismo Builder. */
public final class RegistroHabitaciones {
    private final Set<String> ids = new HashSet<>();

    public void agregar(Habitacion habitacion) {
        Objects.requireNonNull(habitacion);
        if (!ids.add(habitacion.id())) {
            throw new IllegalArgumentException("Habitación duplicada");
        }
    }

    public int cantidad() { return ids.size(); }
}
