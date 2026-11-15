package liminal.integrado.aplicacion;

import java.util.Objects;

/** Valor de salida de un caso de uso; no contiene reglas de movimiento. */
public record ResultadoTurno(
        boolean aceptado, String habitacionId, int turnos,
        boolean finalizada, String mensaje) {
    public ResultadoTurno {
        Objects.requireNonNull(habitacionId);
        Objects.requireNonNull(mensaje);
        if (turnos < 0) throw new IllegalArgumentException("Turnos negativos");
    }
}

