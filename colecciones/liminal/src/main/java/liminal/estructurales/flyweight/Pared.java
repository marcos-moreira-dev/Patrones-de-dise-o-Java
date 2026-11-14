package liminal.estructurales.flyweight;

import java.io.PrintStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;


record Pared(String id, Textura textura) {
    Pared {
        Objects.requireNonNull(id);
        Objects.requireNonNull(textura);
        if (id.isBlank()) throw new IllegalArgumentException("ID vacío");
    }
}
