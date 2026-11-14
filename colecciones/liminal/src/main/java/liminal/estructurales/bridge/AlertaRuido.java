package liminal.estructurales.bridge;

import java.io.PrintStream;
import java.util.Objects;


final class AlertaRuido extends AlertaLiminal {
    AlertaRuido(CanalAviso canal) { super(canal); }
    protected String mensaje() { return "ruido en el pasillo"; }
}
