package liminal.creacionales.builder.variantes.director;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

public final class PruebaDirector {
    private PruebaDirector() {}

    public static void ejecutar() {
        ConstructorEscenario builder = new ConstructorHabitacion();
        rechazar(builder::construir);
        DirectorAmbientes director = new DirectorAmbientes();
        director.construirPasillo("H-01", builder);
        Habitacion pasillo = builder.construir();
        asegurar(pasillo.id().equals("H-01") && pasillo.luces() == 4
                && pasillo.conPuerta(), "Receta del pasillo");

        director.construirPiscina("H-02", builder);
        Habitacion piscina = builder.construir();
        asegurar(piscina.id().equals("H-02") && piscina.luces() == 2
                && !piscina.conPuerta(), "Segunda receta independiente");
        asegurar(pasillo.id().equals("H-01") && pasillo.conPuerta(),
                "Reutilizar builder no modifica producto entregado");

        rechazar(() -> director.construirPasillo(" ", builder));
        Habitacion despuesDelRechazo = builder.construir();
        asegurar(despuesDelRechazo.id().equals("H-02")
                && despuesDelRechazo.ambiente().equals("Piscina vacía"),
                "El rechazo de ID no altera los campos del Builder");
        rechazar(() -> builder.instalarLuces(-1));
        asegurar(builder.construir().luces() == 2, "El rechazo no altera las luces");

        ByteArrayOutputStream buf = new ByteArrayOutputStream();
        ClienteDirector.ejecutar(new PrintStream(buf, true, StandardCharsets.UTF_8));
        asegurar(buf.toString(StandardCharsets.UTF_8).contains("H-02: Piscina vacía / luces 2"),
                "Salida pedagógica");
    }

    private static void asegurar(boolean condicion, String fallo) {
        if (!condicion) throw new AssertionError(fallo);
    }

    private static void rechazar(Runnable accion) {
        try { accion.run(); }
        catch (IllegalArgumentException | IllegalStateException esperado) { return; }
        throw new AssertionError("Acción inválida aceptada");
    }
}
