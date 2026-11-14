package liminal.comportamiento.iterator;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Set;

/** Iterator: recorrer habitaciones sin exponer la lista interna. */
public final class ClienteIterator {
    private ClienteIterator() {}

    public static void ejecutar(PrintStream out) {
        RegistroHabitaciones mapa = new RegistroHabitaciones();
        mapa.agregar(new Habitacion("H-01", "Pasillo amarillo"));
        mapa.agregar(new Habitacion("H-02", "Piscina vacía"));
        for (Habitacion h : mapa) out.println(h.id() + ": " + h.nombre());
    }


    public static void main(String[] args) { ejecutar(System.out); }
}
