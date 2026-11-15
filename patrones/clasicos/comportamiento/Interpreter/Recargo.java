package comportamiento.Interpreter;

import java.math.BigDecimal;
import java.util.Objects;

/** Expresión no terminal: aplica un recargo sobre una expresión anidada. */
public record Recargo(ExpresionPrecio base, BigDecimal porcentaje) implements ExpresionPrecio {
    public Recargo {
        Objects.requireNonNull(base);
        Descuento.validarPorcentaje(porcentaje);
    }

    public BigDecimal interpretar(ContextoPrecios contexto) {
        BigDecimal subtotal = base.interpretar(contexto);
        BigDecimal adicional = subtotal.multiply(porcentaje).divide(BigDecimal.valueOf(100));
        return contexto.normalizar(subtotal.add(adicional));
    }
}
