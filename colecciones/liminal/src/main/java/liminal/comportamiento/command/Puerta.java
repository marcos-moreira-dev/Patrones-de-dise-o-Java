package liminal.comportamiento.command;

import java.io.PrintStream;
import java.util.Objects;


final class Puerta {
    private final String id;
    private boolean abierta;
    Puerta(String id) {
        this.id = Objects.requireNonNull(id);
        if (id.isBlank()) throw new IllegalArgumentException("ID vacío");
    }
    void abrir() {
        if (abierta) throw new IllegalStateException("La puerta ya está abierta");
        abierta = true;
    }
    boolean abierta() { return abierta; }
    String id() { return id; }
}
