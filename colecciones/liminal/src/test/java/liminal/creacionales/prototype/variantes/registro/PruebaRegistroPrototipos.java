package liminal.creacionales.prototype.variantes.registro;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

/** Registros de plantillas separados de IDs de entidades materializadas. */
public final class PruebaRegistroPrototipos {
    private PruebaRegistroPrototipos() {}

    public static void ejecutar() {
        CatalogoPrototipos catalogo = new CatalogoPrototipos();
        PlantillaRegistrada amarilla = new PlantillaRegistrada("Pasillo", 4, true);
        catalogo.registrar("amarillo", amarilla);
        catalogo.registrar("piscinas", new PlantillaRegistrada("Piscina", 0, false));
        asegurar(catalogo.cantidadPlantillas() == 2, "Alta de plantillas");

        HabitacionGenerada h1 = catalogo.crear("amarillo", "H-01");
        HabitacionGenerada h2 = catalogo.crear("amarillo", "H-02");
        asegurar(h1 != h2 && !h1.id().equals(h2.id()), "Nueva identidad de habitación");
        asegurar(h1.ambiente().equals(h2.ambiente()) && h1.luces() == h2.luces()
                && h1.conPuerta() && h2.conPuerta(), "Configuración preservada");
        asegurar(catalogo.cantidadCreadas() == 2, "Conteo de habitaciones creadas");
        rechazar(() -> catalogo.crear("piscinas", "H-01"));
        rechazar(() -> catalogo.crear("desconocida", "H-03"));
        rechazar(() -> catalogo.crear("piscinas", ""));
        rechazar(() -> catalogo.registrar("amarillo", amarilla));
        asegurar(catalogo.cantidadCreadas() == 2 && catalogo.cantidadPlantillas() == 2,
                "Rechazos sin mutación");

        HabitacionGenerada h3 = catalogo.crear("piscinas", "H-03");
        asegurar(h3.luces() == 0 && !h3.conPuerta(), "Configuración de piscina");
        asegurar(catalogo.cantidadCreadas() == 3, "Alta después de rechazos");
        rechazar(() -> new PlantillaRegistrada(" ", 1, true));
        rechazar(() -> new PlantillaRegistrada("Oficina", -1, false));

        ByteArrayOutputStream buf = new ByteArrayOutputStream();
        ClienteRegistroPrototipos.ejecutar(new PrintStream(buf, true, StandardCharsets.UTF_8));
        String salida = buf.toString(StandardCharsets.UTF_8);
        asegurar(salida.contains("H-01: Pasillo amarillo / luces: 4"), "Cliente amarillo");
        asegurar(salida.contains("H-02: Piscina vacía / luces: 2"), "Cliente piscinas");
    }

    private static void asegurar(boolean condicion, String motivo) {
        if (!condicion) throw new AssertionError(motivo);
    }

    private static void rechazar(Runnable accion) {
        try { accion.run(); }
        catch (IllegalArgumentException esperado) { return; }
        throw new AssertionError("Se esperaba rechazo");
    }
}
