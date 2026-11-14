package liminal.creacionales.abstractfactory;

import java.io.PrintStream;
import java.util.Objects;



/** Producto concreto de la familia de piscinas. */
record LuzPiscinas() implements LuzDelNivel {
    public Tema tema() { return Tema.PISCINAS; }
    public String aspecto() { return "reflejo azul"; }
}
