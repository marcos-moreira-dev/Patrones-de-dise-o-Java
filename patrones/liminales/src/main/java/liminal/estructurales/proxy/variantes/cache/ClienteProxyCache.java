package liminal.estructurales.proxy.variantes.cache;

import java.io.PrintStream;

/** Mostrar acierto de caché e invalidación sin medir tiempo real. */
public final class ClienteProxyCache {
    private ClienteProxyCache() {}

    public static void main(String[] args) { ejecutar(System.out); }

    public static void ejecutar(PrintStream out) {
        EscenaGrabada escena = new EscenaGrabada("Pasillo amarillo");
        ProxyCamaraCache camara = new ProxyCamaraCache(escena);
        out.println("GENERACIONES antes: " + camara.generacionesReales());
        primeraConsulta(camara, out);
        repetirConsulta(camara, out);
        actualizarEscena(escena, camara, out);
    }

    private static void primeraConsulta(ProxyCamaraCache camara, PrintStream out) {
        out.println(camara.ver());
        out.println("GENERACIONES primera consulta: " + camara.generacionesReales());
    }

    private static void repetirConsulta(ProxyCamaraCache camara, PrintStream out) {
        camara.ver();
        out.println("GENERACIONES segunda consulta: " + camara.generacionesReales());
    }

    private static void actualizarEscena(
            EscenaGrabada escena, ProxyCamaraCache camara, PrintStream out) {
        escena.actualizar();
        out.println(camara.ver());
        out.println("GENERACIONES tras cambio: " + camara.generacionesReales());
    }
}
