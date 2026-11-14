package liminal.comportamiento.state;

public final class PruebaState {
    private PruebaState() {}

    public static void ejecutar() {
        Puerta puerta = new Puerta("P-01", "LLAVE-ROJA");
        igual("CERRADA", puerta.estado());
        comprobar(!puerta.transitable(), "Cerrada no es transitable");
        puerta.abrir();
        comprobar(puerta.transitable(), "Abierta es transitable");
        puerta.cerrar();
        puerta.bloquear();
        igual("BLOQUEADA", puerta.estado());
        esperarFallo(puerta::abrir);
        igual("BLOQUEADA", puerta.estado());
        esperarFallo(() -> puerta.desbloquear("OTRA"));
        igual("BLOQUEADA", puerta.estado());
        puerta.desbloquear("LLAVE-ROJA");
        igual("CERRADA", puerta.estado());
        puerta.abrir();
        igual("ABIERTA", puerta.estado());
    }
    private static void igual(Object esperado, Object actual) {
        if (!esperado.equals(actual)) throw new AssertionError(esperado + " != " + actual);
    }
    private static void comprobar(boolean ok, String detalle) {
        if (!ok) throw new AssertionError(detalle);
    }
    private static void esperarFallo(Runnable accion) {
        try { accion.run(); }
        catch (IllegalStateException | IllegalArgumentException esperado) { return; }
        throw new AssertionError("Falta rechazo de operación inválida");
    }
}
