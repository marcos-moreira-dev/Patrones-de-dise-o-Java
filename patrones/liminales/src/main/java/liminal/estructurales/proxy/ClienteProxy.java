package liminal.estructurales.proxy;

import java.io.PrintStream;

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

    public static void main(String[] args) { ejecutar(System.out); }
}
