package liminal.comportamiento.observer;

interface ObservadorInterruptor {
    String id();
    void alCambiar(boolean activo);
}
