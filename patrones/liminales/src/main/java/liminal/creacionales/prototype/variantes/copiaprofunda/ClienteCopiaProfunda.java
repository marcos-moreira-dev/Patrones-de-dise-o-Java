package liminal.creacionales.prototype.variantes.copiaprofunda;

import java.io.PrintStream;
import java.util.List;

/** Copiar contenedores no equivale a copiar sus elementos mutables. */
public final class ClienteCopiaProfunda {
    private ClienteCopiaProfunda() {}

    public static void main(String[] args) { ejecutar(System.out); }

    public static void ejecutar(PrintStream out) {
        PlantillaHabitacion original = crearPlantilla();
        PlantillaHabitacion superficial = original.copiarSuperficial();
        PlantillaHabitacion profunda = original.copiarProfunda();
        compararReferencias(original, superficial, profunda, out);
        compararCambios(original, superficial, profunda, out);
    }

    private static PlantillaHabitacion crearPlantilla() {
        return new PlantillaHabitacion("Pasillo amarillo",
                List.of(new ConfiguracionLuminaria("fluorescente", 50)));
    }

    private static void compararReferencias(PlantillaHabitacion a, PlantillaHabitacion b,
            PlantillaHabitacion c, PrintStream out) {
        out.println("SUPERFICIAL comparte configuración: " + (a.luminaria(0) == b.luminaria(0)));
        out.println("PROFUNDA comparte configuración: " + (a.luminaria(0) == c.luminaria(0)));
    }

    private static void compararCambios(PlantillaHabitacion origen,
            PlantillaHabitacion superficial, PlantillaHabitacion profunda, PrintStream out) {
        superficial.luminaria(0).cambiarIntensidad(80);
        out.println("ORIGEN tras modificar copia superficial: " + origen.luminaria(0).intensidad());
        profunda.luminaria(0).cambiarIntensidad(20);
        out.println("ORIGEN tras modificar copia profunda: " + origen.luminaria(0).intensidad());
        out.println("PROFUNDA modificada: " + profunda.luminaria(0).intensidad());
    }
}
