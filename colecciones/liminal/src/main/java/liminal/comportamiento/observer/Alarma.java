package liminal.comportamiento.observer;

final class Alarma implements ObservadorInterruptor {
    private final String id;
    private int avisos;
    Alarma(String id) { this.id = Interruptor.validarId(id); }
    public String id() { return id; }
    public void alCambiar(boolean activo) { if (activo) avisos++; }
    int avisos() { return avisos; }
}
