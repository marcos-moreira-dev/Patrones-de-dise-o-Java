package liminal.comportamiento.visitor;

import java.util.Objects;

/** Elemento concreto de Visitor: doble despacho explícito. */
final class Luz implements Elemento {
    private final String id;

    Luz(String id) {
        this.id = Objects.requireNonNull(id);
        if (id.isBlank()) throw new IllegalArgumentException("ID vacío");
    }

    String id() { return id; }

    public void aceptar(Visitante visitante) { visitante.visitar(this); }
}
