package liminal.comportamiento.command.variantes.deshacer;

/** Encapsula una acción y la compensación permitida por el dominio. */
interface ComandoReversible {
    void ejecutar();
    void deshacer();
}
