package liminal.creacionales.abstractfactory;

import java.io.PrintStream;
import java.util.Objects;



/** Producto concreto de la familia amarilla; solo describe su estado inicial. */
record PuertaAmarilla() implements PuertaDelNivel {
    public Tema tema() { return Tema.AMARILLO; }
    public String aspecto() { return "puerta beige"; }
    public String estado() { return "CERRADA"; }
}
