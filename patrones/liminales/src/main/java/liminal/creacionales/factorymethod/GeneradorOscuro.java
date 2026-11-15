package liminal.creacionales.factorymethod;

final class GeneradorOscuro extends GeneradorCriatura {
    protected Criatura crearCriatura(String id, String habitacion) {
        return new MonstruoOscuro(id, habitacion);
    }
}
