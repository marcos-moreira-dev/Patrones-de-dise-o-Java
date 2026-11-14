package liminal.comportamiento.memento;

import java.io.PrintStream;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Objects;



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
