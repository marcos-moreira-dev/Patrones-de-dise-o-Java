package liminal.creacionales.abstractfactory;

import java.io.PrintStream;
import java.util.Objects;



final class FabricaPiscinas implements FabricaAmbiente {
    public LuzDelNivel crearLuz() { return new LuzPiscinas(); }
    public PuertaDelNivel crearPuerta() { return new PuertaPiscinas(); }
}
