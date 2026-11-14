package liminal.comportamiento.iterator;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Set;


final class RecorridoHabitaciones implements Iterator<Habitacion> {
    private final List<Habitacion> instantanea;
    private int indice;
    RecorridoHabitaciones(List<Habitacion> instantanea) {
        this.instantanea = List.copyOf(instantanea);
    }
    public boolean hasNext() { return indice < instantanea.size(); }
    public Habitacion next() {
        if (!hasNext()) throw new NoSuchElementException();
        return instantanea.get(indice++);
    }
}
