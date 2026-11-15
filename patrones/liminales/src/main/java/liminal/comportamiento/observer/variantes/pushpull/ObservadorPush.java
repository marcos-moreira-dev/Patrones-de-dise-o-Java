package liminal.comportamiento.observer.variantes.pushpull;

/** Push: el sujeto envía el nuevo valor en la notificación. */
interface ObservadorPush {
    String id();
    void actualizar(boolean encendido);
}
