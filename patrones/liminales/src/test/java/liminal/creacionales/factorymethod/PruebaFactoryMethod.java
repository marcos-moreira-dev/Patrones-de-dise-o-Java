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

        // La comprobación de entrada no basta si el Factory Method devuelve otros datos.
        RegistroApariciones variosNiveles = new RegistroApariciones(Set.of("H-01", "H-02"));
        GeneradorCriatura idEquivocado = new GeneradorCriatura() {
            @Override
            protected Criatura crearCriatura(String id, String habitacion) {
                return new MonstruoOscuro("M-99", habitacion);
            }
        };
        GeneradorCriatura destinoEquivocado = new GeneradorCriatura() {
            @Override
            protected Criatura crearCriatura(String id, String habitacion) {
                return new MonstruoSilencioso(id, "H-02");
            }
        };
        GeneradorCriatura sinProducto = new GeneradorCriatura() {
            @Override
            protected Criatura crearCriatura(String id, String habitacion) {
                return null;
            }
        };
        esperarProductoInvalido(() -> idEquivocado.aparecer("M-04", "H-01", variosNiveles));
        esperarProductoInvalido(() -> destinoEquivocado.aparecer("M-04", "H-01", variosNiveles));
        esperarProductoInvalido(() -> sinProducto.aparecer("M-04", "H-01", variosNiveles));
        if (variosNiveles.cantidad() != 0) {
            throw new AssertionError("Una fábrica defectuosa no debe registrar criaturas");
        }
        Criatura valida = new GeneradorOscuro().aparecer("M-04", "H-01", variosNiveles);
        if (!valida.id().equals("M-04") || !valida.habitacion().equals("H-01")
                || variosNiveles.cantidad() != 1) {
            throw new AssertionError("Los rechazos deben preservar el ID solicitado");
        }
    }

    private static void esperarProductoInvalido(Runnable accion) {
        try { accion.run(); }
        catch (IllegalStateException esperado) { return; }
        throw new AssertionError("Se aceptó un producto que no coincide con la solicitud");
    }

    private static void esperarFallo(Runnable accion) {
        try { accion.run(); }
        catch (IllegalArgumentException esperado) { return; }
        throw new AssertionError("El registro debe rechazar el intento");
    }
}
