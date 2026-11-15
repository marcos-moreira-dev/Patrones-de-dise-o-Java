package liminal.integrado.dominio.criaturas;

import java.util.Objects;
import liminal.integrado.dominio.mundo.Habitacion;

/** Vista de consulta: no permite mutar la criatura de una expedición. */
public record EstadoCriatura(String id, Habitacion ubicacion) {
    public EstadoCriatura {
        Objects.requireNonNull(id);
        Objects.requireNonNull(ubicacion);
        if (id.isBlank()) throw new IllegalArgumentException("ID vacío");
    }
}
