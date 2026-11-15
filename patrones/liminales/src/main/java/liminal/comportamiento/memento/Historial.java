package liminal.comportamiento.memento;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Objects;

final class Historial {
    private final Deque<Linterna.Recuerdo> anteriores = new ArrayDeque<>();
    private Linterna origen;
    void guardar(Linterna linterna) {
        Objects.requireNonNull(linterna);
        exigirOrigen(linterna);
        anteriores.push(linterna.guardarEstado());
        origen = linterna;
    }
    void deshacer(Linterna linterna) {
        Objects.requireNonNull(linterna);
        exigirOrigen(linterna);
        if (anteriores.isEmpty()) throw new IllegalStateException("Historial vacío");
        linterna.restaurar(anteriores.peek());
        anteriores.pop();
    }

    /** Las instantáneas son configuraciones de una única linterna, no un almacén global. */
    private void exigirOrigen(Linterna linterna) {
        if (origen != null && origen != linterna) {
            throw new IllegalArgumentException("El historial pertenece a otra linterna");
        }
    }
}
