package liminal.comportamiento.command.variantes.deshacer;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Objects;

/** Invocador con historial; mueve entradas solo después de una acción válida. */
final class HistorialComandos {
    private final Deque<ComandoReversible> realizados = new ArrayDeque<>();
    private final Deque<ComandoReversible> deshechos = new ArrayDeque<>();

    void ejecutar(ComandoReversible comando) {
        Objects.requireNonNull(comando).ejecutar();
        realizados.push(comando);
        deshechos.clear();
    }

    void deshacer() {
        if (realizados.isEmpty()) throw new IllegalStateException("Historial vacío");
        ComandoReversible ultimo = realizados.peek();
        ultimo.deshacer();
        realizados.pop();
        deshechos.push(ultimo);
    }

    void rehacer() {
        if (deshechos.isEmpty()) throw new IllegalStateException("Nada que rehacer");
        ComandoReversible ultimo = deshechos.peek();
        ultimo.ejecutar();
        deshechos.pop();
        realizados.push(ultimo);
    }

    int realizados() { return realizados.size(); }
    int deshechos() { return deshechos.size(); }
}
