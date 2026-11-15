package liminal.integrado.dominio.vigilancia;

import java.util.Objects;

/** Valor de un evento simulado; no es audio ni tráfico de red. */
public record EventoRuido(String sensorId, String habitacionId) {
    public EventoRuido {
        Objects.requireNonNull(sensorId);
        Objects.requireNonNull(habitacionId);
        if (sensorId.isBlank() || habitacionId.isBlank()) {
            throw new IllegalArgumentException("ID de evento vacío");
        }
    }
}
