package liminal.estructurales.proxy.variantes.cache;

import java.util.Objects;

/** Proxy virtual con caché invalidada por la revisión de la escena. */
final class ProxyCamaraCache implements Camara {
    private final EscenaGrabada escena;
    private CamaraReal real;
    private int revisionEnCache = -1;
    private String vistaEnCache;

    ProxyCamaraCache(EscenaGrabada escena) { this.escena = Objects.requireNonNull(escena); }

    @Override
    public String ver() {
        if (vistaEnCache == null || revisionEnCache != escena.revision()) {
            if (real == null) real = new CamaraReal(escena);
            String nuevaVista = real.ver();
            vistaEnCache = nuevaVista;
            revisionEnCache = escena.revision();
        }
        return vistaEnCache;
    }

    int generacionesReales() { return real == null ? 0 : real.generaciones(); }
}
