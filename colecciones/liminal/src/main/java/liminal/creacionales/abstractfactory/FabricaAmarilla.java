package liminal.creacionales.abstractfactory;

final class FabricaAmarilla implements FabricaAmbiente {
    public LuzDelNivel crearLuz() { return new LuzAmarilla(); }
    public PuertaDelNivel crearPuerta() { return new PuertaAmarilla(); }
}
