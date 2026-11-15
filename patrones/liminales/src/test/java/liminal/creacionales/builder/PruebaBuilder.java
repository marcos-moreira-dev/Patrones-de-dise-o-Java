package liminal.creacionales.builder;

/** Pruebas del Builder separadas de la demostración de aprendizaje. */
public final class PruebaBuilder {
    private PruebaBuilder() {}

    public static void ejecutar() {
        rechazarConfiguracionIncompleta();
        construirHabitacionValida();
        rechazarIdentidadInvalida();
        rechazarLucesNegativas();
    }

    private static void rechazarConfiguracionIncompleta() {
        esperarFallo(() -> new ConstructorHabitacion().construir());
    }

    private static void construirHabitacionValida() {
        Habitacion h = new ConstructorHabitacion()
                .id("H-01").ambiente("Piscina vacía")
                .luces(0).conPuerta(false).construir();
        if (!h.ambiente().equals("Piscina vacía") || h.luces() != 0 || h.conPuerta()) {
            throw new AssertionError("Builder no respetó la configuración");
        }
    }

    private static void rechazarIdentidadInvalida() {
        esperarFallo(() -> new ConstructorHabitacion()
                .id(" ").ambiente("Oficina").luces(1).construir());
    }

    private static void rechazarLucesNegativas() {
        esperarFallo(() -> new ConstructorHabitacion()
                .id("H-02").ambiente("Oficina").luces(-1));
    }

    private static void esperarFallo(Runnable accion) {
        try { accion.run(); }
        catch (IllegalArgumentException | IllegalStateException esperado) { return; }
        throw new AssertionError("Debía rechazar una configuración inválida");
    }
}
