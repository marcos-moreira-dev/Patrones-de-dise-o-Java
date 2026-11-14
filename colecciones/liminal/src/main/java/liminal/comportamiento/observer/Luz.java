package liminal.comportamiento.observer;

final class Luz implements ObservadorInterruptor {
    private final String id;
    private boolean encendida;
    Luz(String id) { this.id = Interruptor.validarId(id); }
    public String id() { return id; }
    public void alCambiar(boolean activo) { encendida = activo; }
    boolean encendida() { return encendida; }
}
