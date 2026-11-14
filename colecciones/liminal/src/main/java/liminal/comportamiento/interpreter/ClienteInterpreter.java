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

interface Expresion {
    String interpretar(Contexto contexto);
}

record Abrir(String id) implements Expresion {
    public String interpretar(Contexto contexto) {
        Puerta puerta = contexto.puerta(id);
        puerta.abrir();
        return "Puerta " + id + " ABIERTA";
    }
}
record Encender(String id) implements Expresion {
    public String interpretar(Contexto contexto) {
        Luz luz = contexto.luz(id);
        luz.encender();
        return "Luz " + id + " ENCENDIDA";
    }
}

final class InterpreteOrdenes {
    Expresion analizar(String fuente) {
        Objects.requireNonNull(fuente);
        String[] partes = fuente.trim().split("\\s+");
        if (partes.length != 2 || partes[1].isBlank()) {
            throw new IllegalArgumentException("Sintaxis: ACCION ID");
        }
        return switch (partes[0]) {
            case "ABRIR" -> new Abrir(partes[1]);
            case "ENCENDER" -> new Encender(partes[1]);
            default -> throw new IllegalArgumentException("Operación desconocida");
        };
    }
}
final class Contexto {
    private final Map<String, Puerta> puertas = new HashMap<>();
    private final Map<String, Luz> luces = new HashMap<>();

    void agregarPuerta(String id) {
        validarId(id);
        if (puertas.putIfAbsent(id, new Puerta()) != null) throw new IllegalArgumentException("ID repetido");
    }
    void agregarLuz(String id) {
        validarId(id);
        if (luces.putIfAbsent(id, new Luz()) != null) throw new IllegalArgumentException("ID repetido");
    }
    Puerta puerta(String id) {
        Puerta p = puertas.get(id);
        if (p == null) throw new IllegalArgumentException("Puerta inexistente");
        return p;
    }
    Luz luz(String id) {
        Luz l = luces.get(id);
        if (l == null) throw new IllegalArgumentException("Luz inexistente");
        return l;
    }
    private static void validarId(String id) {
        Objects.requireNonNull(id);
        if (id.isBlank()) throw new IllegalArgumentException("ID vacío");
    }
}
final class Puerta {
    private boolean abierta;
    boolean abierta() { return abierta; }
    void abrir() {
        if (abierta) throw new IllegalArgumentException("Ya está abierta");
        abierta = true;
    }
}
final class Luz {
    private boolean encendida;
    boolean encendida() { return encendida; }
    void encender() { encendida = true; }
}
