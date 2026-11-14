package liminal.estructurales.proxy;

import java.io.PrintStream;
import java.util.Objects;

/** Prueba independiente del recorrido pedagógico. */
public final class PruebaProxy {
    private PruebaProxy() {}

    public static void ejecutar() {
        ProxyCamara proxy = new ProxyCamara("Piscinas", "PASE-02");
        if (proxy.creaciones() != 0) throw new AssertionError("Carga anticipada");
        try {
            proxy.observar("OTRO");
            throw new AssertionError("No debe permitir acceso");
        } catch (IllegalArgumentException esperado) {
            if (proxy.creaciones() != 0) throw new AssertionError("Acceso denegado no debe cargar");
        }
        if (!proxy.observar("PASE-02").equals("Vista: Piscinas")) {
            throw new AssertionError("El proxy debe delegar correctamente");
        }
        proxy.observar("PASE-02");
        if (proxy.creaciones() != 1) throw new AssertionError("Cámara real no debe duplicarse");
    }
}
