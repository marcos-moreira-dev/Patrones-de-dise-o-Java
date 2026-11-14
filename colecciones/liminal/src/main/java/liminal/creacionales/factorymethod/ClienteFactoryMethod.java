package liminal.creacionales.factorymethod;

import java.io.PrintStream;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/** Factory Method: la clase creadora delega el tipo de criatura. */
public final class ClienteFactoryMethod {
    private ClienteFactoryMethod() {}

    public static void ejecutar(PrintStream out) {
        RegistroApariciones registro = new RegistroApariciones(Set.of("H-01", "H-02"));
        GeneradorCriatura oscuro = new GeneradorOscuro();
        GeneradorCriatura silencioso = new GeneradorSilencioso();
        Criatura a = oscuro.aparecer("M-01", "H-01", registro);
        Criatura b = silencioso.aparecer("M-02", "H-02", registro);
        out.println(a.nombre() + " en " + a.habitacion());
        out.println(b.nombre() + " en " + b.habitacion());
    }

    public static void probar() {
        RegistroApariciones registro = new RegistroApariciones(Set.of("H-01"));
        Criatura a = new GeneradorOscuro().aparecer("M-01", "H-01", registro);
        if (!(a instanceof MonstruoOscuro)) throw new AssertionError("Producto equivocado");
        if (!a.habitacion().equals("H-01")) throw new AssertionError("Ubicación inválida");
        esperarFallo(() -> new GeneradorSilencioso().aparecer("M-01", "H-01", registro));
        esperarFallo(() -> new GeneradorSilencioso().aparecer("M-02", "H-99", registro));
        if (registro.cantidad() != 1) throw new AssertionError("Fallos no deben registrar entidades");
        esperarFallo(() -> new MonstruoOscuro(" ", "H-01"));
        esperarFallo(() -> new MonstruoSilencioso("M-03", ""));
        if (registro.cantidad() != 1) throw new AssertionError("Creación inválida no se registra");
    }

    private static void esperarFallo(Runnable accion) {
        try { accion.run(); }
        catch (IllegalArgumentException esperado) { return; }
        throw new AssertionError("El registro debe rechazar el intento");
    }

    public static void main(String[] args) { ejecutar(System.out); }
}
