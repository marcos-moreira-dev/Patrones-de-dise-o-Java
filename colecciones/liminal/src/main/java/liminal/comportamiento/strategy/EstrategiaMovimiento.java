package liminal.comportamiento.strategy;

interface EstrategiaMovimiento {
    String elegirDestino(MapaLiminal mapa, String origen, String objetivo);
}
