package liminal.estructurales.bridge;

final class CanalRadio implements CanalAviso {
    public String comunicar(String mensaje) { return "Radio: " + mensaje; }
}
