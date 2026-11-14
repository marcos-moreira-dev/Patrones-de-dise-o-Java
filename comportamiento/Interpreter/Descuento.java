package comportamiento.Interpreter;

import java.math.BigDecimal;
import java.util.Objects;

/** Expresión no terminal: modifica el resultado de otra expresión. */
public record Descuento(ExpresionPrecio base, BigDecimal porcentaje) implements ExpresionPrecio {
    public Descuento {
        Objects.requireNonNull(base);
        validarPorcentaje(porcentaje);
    }

    public BigDecimal interpretar(ContextoPrecios contexto) {
        BigDecimal subtotal = base.interpretar(contexto);
        BigDecimal descuento = subtotal.multiply(porcentaje).divide(BigDecimal.valueOf(100));
        return contexto.normalizar(subtotal.subtract(descuento));
    }

    static void validarPorcentaje(BigDecimal p) {
        Objects.requireNonNull(p);
        if (p.compareTo(BigDecimal.ZERO) < 0 || p.compareTo(BigDecimal.valueOf(100)) > 0) {
            throw new IllegalArgumentException("Porcentaje fuera de 0..100");
        }
    }
}
