package liminal.estructurales.facade;

/** Descriptor local de una salida: la puerta es opcional y puede cambiar de estado. */
final class Salida {
    private final PuertaPaso puerta;

    Salida(PuertaPaso puerta) {
        this.puerta = puerta;
    }

    PuertaPaso puerta() { return puerta; }
}
