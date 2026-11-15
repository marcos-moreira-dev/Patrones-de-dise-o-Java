package liminal.comportamiento.memento;

import java.io.PrintStream;

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

    public static void main(String[] args) { ejecutar(System.out); }
}
