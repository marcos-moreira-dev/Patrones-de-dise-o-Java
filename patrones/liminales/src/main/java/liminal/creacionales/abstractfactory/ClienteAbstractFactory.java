package liminal.creacionales.abstractfactory;

import java.io.PrintStream;

/** Abstract Factory: cada fábrica construye una familia de productos concretos. */
public final class ClienteAbstractFactory {
    private ClienteAbstractFactory() {}

    public static void ejecutar(PrintStream out) {
        Escena amarillo = new Escena(new FabricaAmarilla());
        Escena piscinas = new Escena(new FabricaPiscinas());
        out.println(amarillo.descripcion());
        out.println(piscinas.descripcion());
    }

    public static void main(String[] args) { ejecutar(System.out); }
}
