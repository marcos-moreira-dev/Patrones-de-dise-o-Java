package liminal.estructurales.proxy.variantes.cache;

import java.util.Objects;

/** Generación simulada costosa: cuenta cuántas veces se prepara una vista. */
final class CamaraReal implements Camara {
    private final EscenaGrabada escena;
    private int generaciones;

    CamaraReal(EscenaGrabada escena) { this.escena = Objects.requireNonNull(escena); }

    @Override
    public String ver() {
        generaciones++;
        return "Vista: " + escena.nombre() + " (revisión " + escena.revision() + ")";
    }

    int generaciones() { return generaciones; }
}
