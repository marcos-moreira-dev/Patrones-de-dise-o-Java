package liminal.comportamiento.strategy;

final class Esperar implements EstrategiaMovimiento {
    public String elegirDestino(MapaLiminal mapa, String origen, String objetivo) { return origen; }
}
