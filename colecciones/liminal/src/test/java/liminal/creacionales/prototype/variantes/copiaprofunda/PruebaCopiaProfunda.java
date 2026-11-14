package liminal.creacionales.prototype.variantes.copiaprofunda;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/** Aislamiento de la copia profunda y aliasing de la superficial. */
public final class PruebaCopiaProfunda {
    private PruebaCopiaProfunda() {}

    public static void ejecutar() {
        ConfiguracionLuminaria luz = new ConfiguracionLuminaria("flúor", 35);
        List<ConfiguracionLuminaria> externa = new ArrayList<>();
        externa.add(luz);
        PlantillaHabitacion original = new PlantillaHabitacion("Pasillo", externa);
        externa.clear();
        asegurar(original.cantidadLuminarias() == 1, "Lista defensiva");

        PlantillaHabitacion shallow = original.copiarSuperficial();
        PlantillaHabitacion deep = original.copiarProfunda();
        asegurar(shallow != original && deep != original, "Copias del contenedor");
        asegurar(shallow.luminaria(0) == original.luminaria(0), "Referencia superficial compartida");
        asegurar(deep.luminaria(0) != original.luminaria(0), "Referencia profunda independiente");

        shallow.luminaria(0).cambiarIntensidad(70);
        asegurar(original.luminaria(0).intensidad() == 70, "Mutación compartida");
        deep.luminaria(0).cambiarIntensidad(5);
        asegurar(original.luminaria(0).intensidad() == 70, "Copia profunda aislada");
        asegurar(deep.luminaria(0).modelo().equals(original.luminaria(0).modelo()),
                "Configuración preservada");

        invalidar(() -> luz.cambiarIntensidad(120));
        asegurar(luz.intensidad() == 70, "Fallo sin mutación");
        invalidar(() -> new PlantillaHabitacion("", List.of(luz)));

        ByteArrayOutputStream buf = new ByteArrayOutputStream();
        ClienteCopiaProfunda.ejecutar(new PrintStream(buf, true, StandardCharsets.UTF_8));
        String salida = buf.toString(StandardCharsets.UTF_8);
        asegurar(salida.contains("SUPERFICIAL comparte configuración: true"), "Consola superficial");
        asegurar(salida.contains("PROFUNDA comparte configuración: false"), "Consola profunda");
        asegurar(salida.contains("ORIGEN tras modificar copia profunda: 80"), "Consola aislada");
    }

    private static void asegurar(boolean bien, String descripcion) {
        if (!bien) throw new AssertionError(descripcion);
    }

    private static void invalidar(Runnable accion) {
        try { accion.run(); }
        catch (IllegalArgumentException esperado) { return; }
        throw new AssertionError("Operación inválida aceptada");
    }
}
