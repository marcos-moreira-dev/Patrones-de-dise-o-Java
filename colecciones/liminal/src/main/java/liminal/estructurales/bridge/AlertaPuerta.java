package liminal.estructurales.bridge;

import java.io.PrintStream;
import java.util.Objects;


final class AlertaPuerta extends AlertaLiminal {
    AlertaPuerta(CanalAviso canal) { super(canal); }
    protected String mensaje() { return "puerta bloqueada"; }
}
