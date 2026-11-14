package liminal.comportamiento.strategy;

import java.util.List;

final class Patrullar implements EstrategiaMovimiento {
    public String elegirDestino(MapaLiminal mapa, String origen, String objetivo) {
        List<String> opciones = mapa.destinosDesde(origen);
        return opciones.isEmpty() ? origen : opciones.get(0);
    }
}
