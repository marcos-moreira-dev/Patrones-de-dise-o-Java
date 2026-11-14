package liminal.estructurales.bridge;

import java.io.PrintStream;
import java.util.Objects;


final class CanalLuz implements CanalAviso {
    public String comunicar(String mensaje) { return "Luz: " + mensaje; }
}
