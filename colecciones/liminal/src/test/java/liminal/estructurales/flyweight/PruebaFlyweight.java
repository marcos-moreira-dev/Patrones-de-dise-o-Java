package liminal.estructurales.flyweight;

import java.io.PrintStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/** Prueba independiente del recorrido pedagógico. */
public final class PruebaFlyweight {
    private PruebaFlyweight() {}

    public static void ejecutar() {
        FabricaTexturas fabrica = new FabricaTexturas();
        Pared uno = new Pared("M-01", fabrica.obtener("papel", "amarillo"));
        Pared dos = new Pared("M-02", fabrica.obtener("papel", "amarillo"));
        Pared tres = new Pared("M-03", fabrica.obtener("azulejo", "azul"));
        if (uno == dos || uno.textura() != dos.textura()) {
            throw new AssertionError("Instancias de pared distintas, textura idéntica");
        }
        if (uno.textura() == tres.textura() || fabrica.cantidad() != 2) {
            throw new AssertionError("Textura nueva para configuración diferente");
        }
        try {
            fabrica.obtener("", "verde");
            throw new AssertionError("Debe rechazar descripción vacía");
        } catch (IllegalArgumentException esperado) {
            if (fabrica.cantidad() != 2) throw new AssertionError("No debe mutar ante error");
        }
    }
}
