package liminal.comportamiento.visitor;

import java.util.Objects;

record Puerta(String id) implements Elemento {
    Puerta {
        Objects.requireNonNull(id);
        if (id.isBlank()) throw new IllegalArgumentException("ID vacío");
    }
    public void aceptar(Visitante visitante) { visitante.visitar(this); }
}
