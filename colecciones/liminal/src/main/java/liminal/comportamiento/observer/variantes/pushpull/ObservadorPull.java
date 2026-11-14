package liminal.comportamiento.observer.variantes.pushpull;

/** Pull: el sujeto solo anuncia el cambio y el observador consulta su estado. */
interface ObservadorPull {
    String id();
    void notificarCambio(Interruptor emisor);
}
