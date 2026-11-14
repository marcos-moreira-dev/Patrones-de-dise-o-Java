package liminal.comportamiento.interpreter;

import java.io.PrintStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/** Interpreter: mini lenguaje ABRIR P1 / ENCENDER L1 con AST de expresiones. */
public final class ClienteInterpreter {
    private ClienteInterpreter() {}

    public static void ejecutar(PrintStream out) {
        Contexto contexto = escenario();
        InterpreteOrdenes interprete = new InterpreteOrdenes();
        out.println(interprete.analizar("ABRIR P-01").interpretar(contexto));
        out.println(interprete.analizar("ENCENDER L-01").interpretar(contexto));
    }


    public static void main(String[] args) { ejecutar(System.out); }

    private static Contexto escenario() {
        Contexto contexto = new Contexto();
        contexto.agregarPuerta("P-01");
        contexto.agregarLuz("L-01");
        return contexto;
    }

}
