package liminal.creacionales.factorymethod;

import java.util.Set;

/** Pruebas del patrón, fuera del cliente pedagógico. */
public final class PruebaFactoryMethod {
    private PruebaFactoryMethod() {}

    public static void ejecutar() {
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
}
