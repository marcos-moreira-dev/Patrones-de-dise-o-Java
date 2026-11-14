package liminal.comportamiento.mediator;

/** Prueba independiente del recorrido pedagógico. */
public final class PruebaMediator {
    private PruebaMediator() {}

    public static void ejecutar() {
        Luz luz = new Luz("L-01");
        Alarma alarma = new Alarma("A-01");
        CentroControl centro = new CentroControl("S-01", luz, alarma);
        SensorRuido sensor = new SensorRuido("S-01", centro);
        sensor.detectar();
        if (!luz.encendida() || alarma.avisos() != 1) {
            throw new AssertionError("Mediator no coordinó los dispositivos");
        }
        try {
            centro.notificarRuido("S-99");
            throw new AssertionError("No se admite un sensor desconocido");
        } catch (IllegalArgumentException esperado) {
            if (alarma.avisos() != 1) throw new AssertionError("El rechazo no debe notificar");
        }
        try {
            new SensorRuido(" ", centro);
            throw new AssertionError("Un sensor necesita un ID");
        } catch (IllegalArgumentException esperado) {
            // R-01.
        }
        try {
            new Luz("");
            throw new AssertionError("La luz necesita un ID");
        } catch (IllegalArgumentException esperado) {
            // R-01.
        }
    }
}
