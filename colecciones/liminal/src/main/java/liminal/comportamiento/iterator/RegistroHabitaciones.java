package liminal.comportamiento.iterator;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Set;

final class RegistroHabitaciones implements Iterable<Habitacion> {
    private final List<Habitacion> habitaciones = new ArrayList<>();
    private final Set<String> ids = new HashSet<>();

    void agregar(Habitacion habitacion) {
        Objects.requireNonNull(habitacion);
        if (!ids.add(habitacion.id())) throw new IllegalArgumentException("ID repetido");
        habitaciones.add(habitacion);
    }
    int cantidad() { return habitaciones.size(); }
    public Iterator<Habitacion> iterator() {
        return new RecorridoHabitaciones(List.copyOf(habitaciones));
    }
}
