package liminal.integrado.dominio.mundo;

/**
 * Rechazo previsto de un movimiento: no existe una salida válida, la
 * expedición terminó o una Strategy propuso un destino ilegal.
 * Otros fallos de estado deben propagarse, no tratarse como turnos rechazados.
 */
public final class MovimientoRechazadoException extends IllegalStateException {
    public MovimientoRechazadoException(String mensaje) {
        super(mensaje);
    }
}
