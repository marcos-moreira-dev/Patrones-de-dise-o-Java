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

    public static void probar() {
        RegistroHabitaciones mapa = new RegistroHabitaciones();
        mapa.agregar(new Habitacion("H-01", "Pasillo"));
        Iterator<Habitacion> i = mapa.iterator();
        mapa.agregar(new Habitacion("H-02", "Piscina"));
        if (!i.hasNext() || !i.next().id().equals("H-01") || i.hasNext()) {
            throw new AssertionError("El iterador debe recorrer su instantánea");
        }
        try {
            i.next();
            throw new AssertionError("Fin de iteración debe fallar");
        } catch (NoSuchElementException esperado) {
            // El estado del registro no cambia.
        }
        try {
            mapa.agregar(new Habitacion("H-01", "Oficina"));
            throw new AssertionError("ID repetido");
        } catch (IllegalArgumentException esperado) {
            if (mapa.cantidad() != 2) throw new AssertionError("Sin registro duplicado");
        }
    }
    public static void main(String[] args) { ejecutar(System.out); }
}
