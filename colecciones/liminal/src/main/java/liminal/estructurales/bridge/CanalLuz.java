package liminal.estructurales.bridge;

final class CanalLuz implements CanalAviso {
    public String comunicar(String mensaje) { return "Luz: " + mensaje; }
}
