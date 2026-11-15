package liminal.integrado.dominio.criaturas;

import java.util.List;
import liminal.integrado.dominio.mundo.Habitacion;

/** Política determinista: primera salida dirigida o permanecer quieto. */
public final class Patrullar implements EstrategiaMovimiento {
    @Override
    public Habitacion elegirDestino(Habitacion actual, Habitacion objetivo,
                                    List<Habitacion> destinosPermitidos) {
        return destinosPermitidos.isEmpty() ? actual : destinosPermitidos.get(0);
    }
}
