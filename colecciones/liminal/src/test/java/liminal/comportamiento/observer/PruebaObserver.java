package liminal.comportamiento.observer;

public final class PruebaObserver {
    private PruebaObserver() {}

    public static void ejecutar() {
        Interruptor interruptor = new Interruptor("I-01");
        Luz luz = new Luz("L-01");
        Alarma alarma = new Alarma("A-01");
        interruptor.suscribir(luz);
        interruptor.suscribir(alarma);
        comprobar(interruptor.cantidadObservadores() == 2, "Dos suscriptores");
        esperarFallo(() -> interruptor.suscribir(new Luz("L-01")));
        comprobar(interruptor.cantidadObservadores() == 2, "Sin duplicados");
        interruptor.pulsar();
        comprobar(luz.encendida(), "Luz encendida");
        comprobar(alarma.avisos() == 1, "Alarma notificada");
        interruptor.pulsar();
        comprobar(!luz.encendida(), "Luz apagada");
        comprobar(alarma.avisos() == 1, "No hay nuevo aviso al apagar");
        interruptor.desuscribir("A-01");
        interruptor.pulsar();
        comprobar(alarma.avisos() == 1, "Baja de suscripción");
        comprobar(luz.encendida(), "La luz sigue suscrita");
    }
    private static void comprobar(boolean ok, String detalle) {
        if (!ok) throw new AssertionError(detalle);
    }
    private static void esperarFallo(Runnable accion) {
        try { accion.run(); }
        catch (IllegalArgumentException esperado) { return; }
        throw new AssertionError("Falta rechazo de ID repetido");
    }
}
