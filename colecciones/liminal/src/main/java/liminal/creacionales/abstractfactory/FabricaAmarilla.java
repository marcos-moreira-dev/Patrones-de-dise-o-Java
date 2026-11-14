package liminal.creacionales.abstractfactory;

import java.io.PrintStream;
import java.util.Objects;



final class FabricaAmarilla implements FabricaAmbiente {
    public LuzDelNivel crearLuz() { return new LuzAmarilla(); }
    public PuertaDelNivel crearPuerta() { return new PuertaAmarilla(); }
}
