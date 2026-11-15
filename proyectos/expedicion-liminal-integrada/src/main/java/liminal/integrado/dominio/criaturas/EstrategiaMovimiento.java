package liminal.integrado.dominio.criaturas;

import java.util.List;
import liminal.integrado.dominio.mundo.Habitacion;

/** Strategy: propone un destino, sin mutar a la criatura ni al mapa. */
@FunctionalInterface
public interface EstrategiaMovimiento {
    Habitacion elegirDestino(Habitacion actual, Habitacion objetivo, List<Habitacion> destinosPermitidos);
}
