package comportamiento.Interpreter;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

/** Contexto de interpretación: política de decimales y redondeo. */
public record ContextoPrecios(int decimales) {
    public ContextoPrecios {
        if (decimales < 0 || decimales > 4) {
            throw new IllegalArgumentException("Decimales fuera de 0..4");
        }
    }

    BigDecimal normalizar(BigDecimal importe) {
        return Objects.requireNonNull(importe).setScale(decimales, RoundingMode.HALF_UP);
    }
}
