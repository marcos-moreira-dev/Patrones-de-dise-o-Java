package liminal.creacionales.abstractfactory;

final class FabricaPiscinas implements FabricaAmbiente {
    public LuzDelNivel crearLuz() { return new LuzPiscinas(); }
    public PuertaDelNivel crearPuerta() { return new PuertaPiscinas(); }
}
