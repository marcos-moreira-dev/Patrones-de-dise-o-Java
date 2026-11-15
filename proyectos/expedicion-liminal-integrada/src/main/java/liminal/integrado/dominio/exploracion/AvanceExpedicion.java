package liminal.integrado.dominio.exploracion;

import java.util.Objects;
import liminal.integrado.dominio.criaturas.EstadoCriatura;
import liminal.integrado.dominio.mundo.Habitacion;

/**
 * Resultado de un turno válido. criatura es null solo si la sesión no la tiene.
 * No simula una transacción global entre ambas acciones.
 */
public record AvanceExpedicion(Habitacion habitacion, EstadoCriatura criatura,
                               boolean criaturaSeMovio, boolean criaturaRechazada) {
    public AvanceExpedicion {
        Objects.requireNonNull(habitacion);
        if (criatura == null && (criaturaSeMovio || criaturaRechazada)) {
            throw new IllegalArgumentException("Sin criatura no puede haber acción");
        }
        if (criaturaSeMovio && criaturaRechazada) {
            throw new IllegalArgumentException("Movimiento y rechazo incompatibles");
        }
    }
}
