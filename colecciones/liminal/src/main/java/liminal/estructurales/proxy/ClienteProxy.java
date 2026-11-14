package liminal.estructurales.proxy;

import java.io.PrintStream;
import java.util.Objects;

/** Proxy: protege una cámara y crea el objeto real solo cuando se necesita. */
public final class ClienteProxy {
    private ClienteProxy() {}

    public static void ejecutar(PrintStream out) {
        ProxyCamara camara = new ProxyCamara("Pasillo amarillo", "PASE-01");
        out.println("Cámara real creada al inicio: " + camara.creaciones());
        try {
            camara.observar("INCORRECTO");
        } catch (IllegalArgumentException e) {
            out.println("Acceso sin pase: denegado");
        }
        out.println(camara.observar("PASE-01"));
        out.println("Cámara real creada al final: " + camara.creaciones());
    }

    public static void probar() {
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
    public static void main(String[] args) { ejecutar(System.out); }
}
