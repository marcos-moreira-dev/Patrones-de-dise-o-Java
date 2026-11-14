package liminal.creacionales.factorymethod;

final class GeneradorSilencioso extends GeneradorCriatura {
    protected Criatura crearCriatura(String id, String habitacion) {
        return new MonstruoSilencioso(id, habitacion);
    }
}
