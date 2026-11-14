package liminal.comportamiento.interpreter;

import java.io.PrintStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;


final class Puerta {
    private boolean abierta;
    boolean abierta() { return abierta; }
    void abrir() {
        if (abierta) throw new IllegalArgumentException("Ya está abierta");
        abierta = true;
    }
}
