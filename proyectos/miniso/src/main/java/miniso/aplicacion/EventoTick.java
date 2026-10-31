package miniso.aplicacion;

/** Un único paso de tiempo del simulador, con tipo estructurado para las pruebas. */
public record EventoTick(long reloj, Tipo tipo, long pid, String detalle) {
    public enum Tipo { OCIOSO, EJECUCION, FINALIZACION }
}
