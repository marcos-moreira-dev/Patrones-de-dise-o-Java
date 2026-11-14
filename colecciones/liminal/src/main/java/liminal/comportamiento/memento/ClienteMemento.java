package liminal.comportamiento.memento;

import java.io.PrintStream;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Objects;

/** Memento: recuperar un ajuste anterior sin exponer sus campos internos. */
public final class ClienteMemento {
    private ClienteMemento() {}

    public static void ejecutar(PrintStream out) {
        Linterna linterna = new Linterna(4, false);
        Historial ajustes = new Historial();
        ajustes.guardar(linterna);
        linterna.configurar(8, true);
        out.println("Ajuste nuevo: " + linterna.descripcion());
        ajustes.deshacer(linterna);
        out.println("Ajuste restaurado: " + linterna.descripcion());
    }

    public static void probar() {
        Linterna linterna = new Linterna(3, false);
        Historial historial = new Historial();
        historial.guardar(linterna);
        linterna.configurar(9, true);
        historial.deshacer(linterna);
        if (linterna.intensidad() != 3 || linterna.filtroAzul()) {
            throw new AssertionError("No se restauró el estado");
        }
        try {
            linterna.configurar(-1, true);
            throw new AssertionError("No se permite intensidad negativa");
        } catch (IllegalArgumentException esperado) {
            if (linterna.intensidad() != 3) throw new AssertionError("Sin mutación parcial");
        }
        try {
            historial.deshacer(linterna);
            throw new AssertionError("No existe otra instantánea");
        } catch (IllegalStateException esperado) {
            if (linterna.intensidad() != 3) throw new AssertionError("No cambia el objeto");
        }
    }
    public static void main(String[] args) { ejecutar(System.out); }
}
