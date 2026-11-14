package liminal.comportamiento.interpreter;

import java.io.PrintStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;


final class Luz {
    private boolean encendida;
    boolean encendida() { return encendida; }
    void encender() { encendida = true; }
}
