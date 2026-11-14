package liminal.estructurales.proxy.variantes.cache;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

public final class PruebaProxyCache {
    private PruebaProxyCache() {}

    public static void ejecutar() {
        EscenaGrabada escena = new EscenaGrabada("Oficina");
        ProxyCamaraCache proxy = new ProxyCamaraCache(escena);
        Camara porContrato = proxy;
        asegurar(proxy.generacionesReales() == 0, "Sin cámara real al crear proxy");
        String v0 = porContrato.ver();
        asegurar(v0.contains("revisión 0") && proxy.generacionesReales() == 1,
                "Primera carga");
        asegurar(porContrato.ver().equals(v0) && proxy.generacionesReales() == 1,
                "Cache hit sin trabajo adicional");
        escena.actualizar();
        String v1 = porContrato.ver();
        asegurar(v1.contains("revisión 1") && !v1.equals(v0)
                && proxy.generacionesReales() == 2, "Invalidación por versión");
        asegurar(porContrato.ver().equals(v1) && proxy.generacionesReales() == 2,
                "Nueva versión también se cachea");
        rechazar(() -> new EscenaGrabada(" "));
        asegurar(proxy.generacionesReales() == 2, "Rechazo sin crear nuevas vistas");

        ByteArrayOutputStream buf = new ByteArrayOutputStream();
        ClienteProxyCache.ejecutar(new PrintStream(buf, true, StandardCharsets.UTF_8));
        String salida = buf.toString(StandardCharsets.UTF_8);
        asegurar(salida.contains("GENERACIONES antes: 0"), "Sin carga anticipada");
        asegurar(salida.contains("GENERACIONES segunda consulta: 1"), "Cache hit");
        asegurar(salida.contains("GENERACIONES tras cambio: 2"), "Cache miss");
    }

    private static void asegurar(boolean bien, String motivo) {
        if (!bien) throw new AssertionError(motivo);
    }

    private static void rechazar(Runnable accion) {
        try { accion.run(); }
        catch (IllegalArgumentException esperado) { return; }
        throw new AssertionError("Operación inválida permitida");
    }
}
