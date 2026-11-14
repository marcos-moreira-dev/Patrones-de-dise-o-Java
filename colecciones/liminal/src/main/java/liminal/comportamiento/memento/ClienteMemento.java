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

final class Linterna {
    private int intensidad;
    private boolean filtroAzul;

    Linterna(int intensidad, boolean filtroAzul) { configurar(intensidad, filtroAzul); }
    int intensidad() { return intensidad; }
    boolean filtroAzul() { return filtroAzul; }

    void configurar(int intensidadNueva, boolean filtroNuevo) {
        if (intensidadNueva < 0 || intensidadNueva > 10) {
            throw new IllegalArgumentException("Intensidad fuera de 0..10");
        }
        intensidad = intensidadNueva;
        filtroAzul = filtroNuevo;
    }
    String descripcion() { return "intensidad " + intensidad + ", filtro azul " + filtroAzul; }

    Recuerdo guardarEstado() { return new Recuerdo(intensidad, filtroAzul); }
    void restaurar(Recuerdo recuerdo) {
        Objects.requireNonNull(recuerdo);
        configurar(recuerdo.intensidad, recuerdo.filtroAzul);
    }

    /** Solo Linterna conoce la estructura de la instantánea. */
    static final class Recuerdo {
        private final int intensidad;
        private final boolean filtroAzul;
        private Recuerdo(int intensidad, boolean filtroAzul) {
            this.intensidad = intensidad;
            this.filtroAzul = filtroAzul;
        }
    }
}

final class Historial {
    private final Deque<Linterna.Recuerdo> anteriores = new ArrayDeque<>();
    void guardar(Linterna linterna) {
        anteriores.push(Objects.requireNonNull(linterna).guardarEstado());
    }
    void deshacer(Linterna linterna) {
        Objects.requireNonNull(linterna);
        if (anteriores.isEmpty()) throw new IllegalStateException("Historial vacío");
        linterna.restaurar(anteriores.peek());
        anteriores.pop();
    }
}
