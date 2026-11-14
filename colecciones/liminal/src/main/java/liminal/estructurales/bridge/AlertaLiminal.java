package liminal.estructurales.bridge;

import java.io.PrintStream;
import java.util.Objects;



abstract class AlertaLiminal {
    private final CanalAviso canal;
    AlertaLiminal(CanalAviso canal) { this.canal = Objects.requireNonNull(canal); }

    public final String emitir() { return canal.comunicar(mensaje()); }
    protected abstract String mensaje();
}
