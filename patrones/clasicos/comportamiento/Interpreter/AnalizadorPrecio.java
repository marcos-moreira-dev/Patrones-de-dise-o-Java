package comportamiento.Interpreter;

import java.math.BigDecimal;
import java.util.Objects;

/** Analizador recursivo: monto | DESCUENTO expresión pct | RECARGO expresión pct. */
public final class AnalizadorPrecio {
    public ExpresionPrecio analizar(String fuente) {
        Objects.requireNonNull(fuente);
        if (fuente.isBlank()) throw new IllegalArgumentException("Expresión vacía");
        Cursor c = new Cursor(fuente.trim().split("\\s+"));
        ExpresionPrecio arbol = leerExpresion(c);
        if (c.pendiente()) throw new IllegalArgumentException("Tokens sobrantes");
        return arbol;
    }

    private ExpresionPrecio leerExpresion(Cursor c) {
        String token = c.leer();
        return switch (token) {
            case "DESCUENTO" -> new Descuento(leerExpresion(c), numero(c.leer()));
            case "RECARGO" -> new Recargo(leerExpresion(c), numero(c.leer()));
            default -> new PrecioBase(numero(token));
        };
    }

    private static BigDecimal numero(String token) {
        try {
            return new BigDecimal(token);
        } catch (NumberFormatException ex) {
            throw new IllegalArgumentException("Se esperaba un número: " + token, ex);
        }
    }

    private static final class Cursor {
        private final String[] tokens;
        private int indice;
        Cursor(String[] tokens) { this.tokens = tokens; }
        boolean pendiente() { return indice < tokens.length; }
        String leer() {
            if (!pendiente()) throw new IllegalArgumentException("Falta un elemento en la expresión");
            return tokens[indice++];
        }
    }
}
