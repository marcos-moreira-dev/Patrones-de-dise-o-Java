package liminal.estructurales.bridge;

import java.io.PrintStream;
import java.util.Objects;


final class CanalRadio implements CanalAviso {
    public String comunicar(String mensaje) { return "Radio: " + mensaje; }
}
