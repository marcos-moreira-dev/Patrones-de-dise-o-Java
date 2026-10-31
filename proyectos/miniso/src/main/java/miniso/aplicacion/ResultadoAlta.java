package miniso.aplicacion;

/** PID igual a cero indica un alta rechazada, nunca un proceso existente. */
public record ResultadoAlta(boolean aceptado, long pid, String detalle) {}
