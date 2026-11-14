package liminal.comportamiento.interpreter;

final class Puerta {
    private boolean abierta;
    boolean abierta() { return abierta; }
    void abrir() {
        if (abierta) throw new IllegalArgumentException("Ya está abierta");
        abierta = true;
    }
}
