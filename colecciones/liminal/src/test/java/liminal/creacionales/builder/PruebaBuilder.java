package liminal.creacionales.builder;

/** Pruebas del Builder separadas de la demostración de aprendizaje. */
public final class PruebaBuilder {
    private PruebaBuilder() {}

    public static void ejecutar() {
        rechazarConfiguracionIncompleta();
        construirHabitacionValida();
        rechazarIdsDuplicados();
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

    private static void rechazarIdsDuplicados() {
        RegistroHabitaciones mapa = new RegistroHabitaciones();
        mapa.agregar(new ConstructorHabitacion()
                .id("H-01").ambiente("Piscina vacía")
                .luces(0).construir());
        esperarFallo(() -> mapa.agregar(new ConstructorHabitacion()
                .id("H-01").ambiente("Oficina")
                .luces(1).construir()));
        if (mapa.cantidad() != 1) throw new AssertionError("No se admiten IDs repetidos");
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
