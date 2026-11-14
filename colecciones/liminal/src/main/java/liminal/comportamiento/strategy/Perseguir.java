package liminal.comportamiento.strategy;

final class Perseguir implements EstrategiaMovimiento {
    public String elegirDestino(MapaLiminal mapa, String origen, String objetivo) {
        return mapa.conecta(origen, objetivo) ? objetivo : origen;
    }
}
