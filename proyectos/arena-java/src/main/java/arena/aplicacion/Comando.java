package arena.aplicacion;

/**
 * Command: encapsula una petición de la persona que juega.
 * La consola decide cuál construir; cada comando conoce el caso de uso a invocar.
 */
@FunctionalInterface
public interface Comando {
    ResultadoAccion ejecutar(ServicioPartida servicio);
}
