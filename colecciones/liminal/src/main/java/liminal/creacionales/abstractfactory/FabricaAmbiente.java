package liminal.creacionales.abstractfactory;

import java.io.PrintStream;
import java.util.Objects;



interface FabricaAmbiente {
    LuzDelNivel crearLuz();
    PuertaDelNivel crearPuerta();
}
