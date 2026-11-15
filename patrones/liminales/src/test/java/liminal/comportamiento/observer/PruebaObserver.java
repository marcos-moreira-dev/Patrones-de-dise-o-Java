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

        // El observador puede cambiar las suscripciones mientras recibe un aviso.
        Interruptor dinamico = new Interruptor("I-02");
        Luz luzPermanente = new Luz("L-02");
        Alarma alarmaNueva = new Alarma("A-02");
        int[] llamadas = {0};
        dinamico.suscribir(new ObservadorInterruptor() {
            public String id() { return "O-01"; }
            public void alCambiar(boolean activo) {
                llamadas[0]++;
                dinamico.desuscribir(id());
                dinamico.suscribir(alarmaNueva);
            }
        });
        dinamico.suscribir(luzPermanente);
        dinamico.pulsar();
        comprobar(llamadas[0] == 1 && luzPermanente.encendida(),
                "Auto-retiro no interrumpe la notificación de otros receptores");
        comprobar(alarmaNueva.avisos() == 0 && dinamico.cantidadObservadores() == 2,
                "El nuevo observador no participa en el aviso que lo suscribió");
        dinamico.pulsar();
        comprobar(!luzPermanente.encendida() && llamadas[0] == 1,
                "El observador retirado no participa en el aviso siguiente");
        dinamico.pulsar();
        comprobar(alarmaNueva.avisos() == 1 && luzPermanente.encendida(),
                "El observador nuevo participa desde los avisos siguientes");
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
