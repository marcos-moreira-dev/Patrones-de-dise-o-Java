package liminal.estructurales.bridge;

import java.util.Objects;

abstract class AlertaLiminal {
    // El mensaje y su medio de transmisión varían de forma independiente:
    // esta referencia evita una subclase por cada combinación de ambos.
    private final CanalAviso canal;
    AlertaLiminal(CanalAviso canal) { this.canal = Objects.requireNonNull(canal); }

    public final String emitir() { return canal.comunicar(mensaje()); }
    protected abstract String mensaje();
}
