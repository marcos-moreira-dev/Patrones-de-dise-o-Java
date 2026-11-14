package liminal.comportamiento.visitor;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


record Linterna(String id) implements Elemento {
    Linterna {
        Objects.requireNonNull(id);
        if (id.isBlank()) throw new IllegalArgumentException("ID vacío");
    }
    public void aceptar(Visitante visitante) { visitante.visitar(this); }
}
