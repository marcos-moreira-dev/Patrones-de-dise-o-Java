package arena.aplicacion;

import arena.dominio.EventoCombate;

/**
 * Observer: la presentación puede escuchar acontecimientos sin que el dominio
 * conozca la consola. También permite capturarlos en las pruebas.
 */
@FunctionalInterface
public interface ObservadorPartida {
    void alOcurrir(EventoCombate acontecimiento);
}
