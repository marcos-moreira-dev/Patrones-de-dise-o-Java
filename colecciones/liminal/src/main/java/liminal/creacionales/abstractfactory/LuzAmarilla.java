package liminal.creacionales.abstractfactory;

import java.io.PrintStream;
import java.util.Objects;



/** Producto concreto de la familia amarilla. */
record LuzAmarilla() implements LuzDelNivel {
    public Tema tema() { return Tema.AMARILLO; }
    public String aspecto() { return "fluorescente amarilla"; }
}
