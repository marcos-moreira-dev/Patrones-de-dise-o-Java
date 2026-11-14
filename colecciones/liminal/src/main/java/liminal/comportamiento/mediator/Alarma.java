package liminal.comportamiento.mediator;

import java.io.PrintStream;
import java.util.Objects;


final class Alarma {
    private final String id;
    private int avisos;
    Alarma(String id) {
        this.id = Objects.requireNonNull(id);
        if (id.isBlank()) throw new IllegalArgumentException("ID vacío");
    }
    void avisar() { avisos++; }
    int avisos() { return avisos; }
}
