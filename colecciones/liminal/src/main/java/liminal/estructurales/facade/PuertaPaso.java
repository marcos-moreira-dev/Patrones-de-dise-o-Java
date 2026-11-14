package liminal.estructurales.facade;

import java.io.PrintStream;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;


final class PuertaPaso {
    private final String id;
    private EstadoPaso estado = EstadoPaso.CERRADA;
    PuertaPaso(String id) {
        this.id = Objects.requireNonNull(id);
        if (id.isBlank()) throw new IllegalArgumentException("ID de puerta vacío");
    }
    String id() { return id; }
    EstadoPaso estado() { return estado; }
    void abrir() {
        if (estado != EstadoPaso.CERRADA) throw new IllegalStateException("No puede abrirse");
        estado = EstadoPaso.ABIERTA;
    }
    boolean transitable() { return estado == EstadoPaso.ABIERTA; }
}
