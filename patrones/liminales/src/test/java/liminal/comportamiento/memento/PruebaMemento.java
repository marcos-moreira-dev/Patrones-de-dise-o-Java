package liminal.comportamiento.memento;

/** Prueba independiente del recorrido pedagógico. */
public final class PruebaMemento {
    private PruebaMemento() {}

    public static void ejecutar() {
        Linterna linterna = new Linterna(3, false);
        Historial historial = new Historial();
        historial.guardar(linterna);
        linterna.configurar(9, true);
        historial.deshacer(linterna);
        if (linterna.intensidad() != 3 || linterna.filtroAzul()) {
            throw new AssertionError("No se restauró el estado");
        }
        Linterna otra = new Linterna(10, true);
        historial.guardar(linterna);
        linterna.configurar(7, true);
        rechazar(() -> historial.deshacer(otra));
        rechazar(() -> historial.guardar(otra));
        if (otra.intensidad() != 10 || !otra.filtroAzul()
                || linterna.intensidad() != 7 || !linterna.filtroAzul()) {
            throw new AssertionError("Un historial no debe restaurar otra linterna");
        }
        historial.deshacer(linterna);
        if (linterna.intensidad() != 3 || linterna.filtroAzul()) {
            throw new AssertionError("Rechazar otra linterna no debe consumir el recuerdo");
        }
        try {
            linterna.configurar(-1, true);
            throw new AssertionError("No se permite intensidad negativa");
        } catch (IllegalArgumentException esperado) {
            if (linterna.intensidad() != 3) throw new AssertionError("Sin mutación parcial");
        }
        try {
            historial.deshacer(linterna);
            throw new AssertionError("No existe otra instantánea");
        } catch (IllegalStateException esperado) {
            if (linterna.intensidad() != 3) throw new AssertionError("No cambia el objeto");
        }
    }

    private static void rechazar(Runnable accion) {
        try { accion.run(); }
        catch (IllegalArgumentException esperado) { return; }
        throw new AssertionError("El historial aceptó una linterna distinta");
    }
}
