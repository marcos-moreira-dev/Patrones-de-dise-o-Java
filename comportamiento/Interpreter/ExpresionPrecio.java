package comportamiento.Interpreter;

import java.math.BigDecimal;

/** Expresión abstracta: cada nodo interpreta su parte de una gramática de precios. */
public interface ExpresionPrecio {
    BigDecimal interpretar(ContextoPrecios contexto);
}
