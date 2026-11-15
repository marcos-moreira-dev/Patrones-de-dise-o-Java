package comportamiento.Interpreter;

import java.math.BigDecimal;
import java.util.Objects;

/** Expresión terminal: un precio literal. */
public record PrecioBase(BigDecimal importe) implements ExpresionPrecio {
    public PrecioBase {
        Objects.requireNonNull(importe);
        if (importe.signum() < 0) throw new IllegalArgumentException("Precio negativo");
    }

    public BigDecimal interpretar(ContextoPrecios contexto) {
        return Objects.requireNonNull(contexto).normalizar(importe);
    }
}
