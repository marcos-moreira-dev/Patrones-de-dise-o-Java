package miniso.aplicacion;

/** Respuesta esperable de un caso de uso: éxito o rechazo, sin excepción. */
public record ResultadoOperacion(boolean exito, String detalle) {
    public static ResultadoOperacion aceptada(String detalle) {
        return new ResultadoOperacion(true, detalle);
    }

    public static ResultadoOperacion rechazada(String detalle) {
        return new ResultadoOperacion(false, detalle);
    }
}
