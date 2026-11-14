package liminal.creacionales.singleton;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/** Pruebas del patrón, fuera del cliente pedagógico. */
public final class PruebaSingleton {
    private PruebaSingleton() {}

    public static void ejecutar() {
        PanelAvisos a = PanelAvisos.instancia();
        PanelAvisos b = PanelAvisos.instancia();
        if (a != b) throw new AssertionError("Singleton debe devolver la misma instancia");
        a.limpiarParaDemo();
        a.publicar("Luz encendida");
        if (!b.mensajes().equals(List.of("Luz encendida"))) {
            throw new AssertionError("Ambas referencias deben compartir estado");
        }
        try {
            a.publicar(" ");
            throw new AssertionError("Debe rechazar mensajes vacíos");
        } catch (IllegalArgumentException esperado) {
            if (a.mensajes().size() != 1) throw new AssertionError("No debe mutar");
        }
        a.limpiarParaDemo();
    }
}
