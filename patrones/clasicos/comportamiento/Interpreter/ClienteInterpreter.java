package comportamiento.Interpreter;

import java.io.PrintStream;
import java.math.BigDecimal;

/** Cliente: interpreta un lenguaje mínimo de cálculo de pedidos sin evaluar código Java. */
public final class ClienteInterpreter {
    private ClienteInterpreter() {}

    public static void ejecutar(PrintStream salida) {
        AnalizadorPrecio parser = new AnalizadorPrecio();
        ContextoPrecios contexto = new ContextoPrecios(2);
        for (String expresion : new String[]{
                "DESCUENTO RECARGO 100 5 10",
                "RECARGO DESCUENTO 200 20 5"}) {
            ExpresionPrecio arbol = parser.analizar(expresion);
            salida.println("DSL: " + expresion);
            salida.println("Total calculado: $" + arbol.interpretar(contexto));
        }
    }

    public static void probar() {
        AnalizadorPrecio parser = new AnalizadorPrecio();
        ContextoPrecios contexto = new ContextoPrecios(2);
        igual("94.50", parser.analizar("DESCUENTO RECARGO 100 5 10").interpretar(contexto));
        igual("168.00", parser.analizar("RECARGO DESCUENTO 200 20 5").interpretar(contexto));
        igual("72.00", parser.analizar("DESCUENTO DESCUENTO 100 10 20").interpretar(contexto));
        esperarFallo(() -> parser.analizar("DESCUENTO 100"));
        esperarFallo(() -> parser.analizar("RECARGO 100 200"));
        esperarFallo(() -> parser.analizar("DESCUENTO -10 5"));
        esperarFallo(() -> parser.analizar("100 token_extra"));
        esperarFallo(() -> parser.analizar(""));
    }

    private static void igual(String esperado, BigDecimal actual) {
        if (new BigDecimal(esperado).compareTo(actual) != 0) {
            throw new AssertionError(esperado + " != " + actual);
        }
    }
    private static void esperarFallo(Runnable accion) {
        try { accion.run(); }
        catch (IllegalArgumentException esperado) { return; }
        throw new AssertionError("Una expresión inválida fue aceptada");
    }
    public static void main(String[] args) { ejecutar(System.out); probar(); }
}
