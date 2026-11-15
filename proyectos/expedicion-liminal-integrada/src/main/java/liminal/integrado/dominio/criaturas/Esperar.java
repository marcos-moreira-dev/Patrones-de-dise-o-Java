package liminal.integrado.dominio.criaturas;

import java.util.List;
import liminal.integrado.dominio.mundo.Habitacion;

/** No desplazarse también es una política explícita. */
public final class Esperar implements EstrategiaMovimiento {
    @Override
    public Habitacion elegirDestino(Habitacion actual, Habitacion objetivo,
                                    List<Habitacion> destinosPermitidos) {
        return actual;
    }
}
