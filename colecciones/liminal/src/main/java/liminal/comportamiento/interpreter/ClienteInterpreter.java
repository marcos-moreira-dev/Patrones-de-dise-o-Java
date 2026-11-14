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

    public static void probar() {
        Contexto contexto = escenario();
        InterpreteOrdenes interprete = new InterpreteOrdenes();
        esperarFallo(() -> interprete.analizar("ABRIR"));
        esperarFallo(() -> interprete.analizar("BORRAR P-01"));
        esperarFallo(() -> interprete.analizar("ENCENDER L-99").interpretar(contexto));
        if (contexto.puerta("P-01").abierta() || contexto.luz("L-01").encendida()) {
            throw new AssertionError("Instrucción inválida no modifica el contexto");
        }
        interprete.analizar("ABRIR P-01").interpretar(contexto);
        interprete.analizar("ENCENDER L-01").interpretar(contexto);
        if (!contexto.puerta("P-01").abierta() || !contexto.luz("L-01").encendida()) {
            throw new AssertionError("Expresiones no se ejecutaron");
        }
    }
    public static void main(String[] args) { ejecutar(System.out); }

    private static Contexto escenario() {
        Contexto contexto = new Contexto();
        contexto.agregarPuerta("P-01");
        contexto.agregarLuz("L-01");
        return contexto;
    }
    private static void esperarFallo(Runnable operacion) {
        try { operacion.run(); }
        catch (IllegalArgumentException esperado) { return; }
        throw new AssertionError("Debe rechazar instrucción inválida");
    }
}
