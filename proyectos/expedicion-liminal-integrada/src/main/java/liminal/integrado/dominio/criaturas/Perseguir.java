package liminal.integrado.dominio.criaturas;

import java.util.List;
import liminal.integrado.dominio.mundo.Habitacion;

/** Persecución local: no inventa rutas entre habitaciones lejanas. */
public final class Perseguir implements EstrategiaMovimiento {
    @Override
    public Habitacion elegirDestino(Habitacion actual, Habitacion objetivo,
                                    List<Habitacion> destinosPermitidos) {
        return destinosPermitidos.contains(objetivo) ? objetivo : actual;
    }
}
