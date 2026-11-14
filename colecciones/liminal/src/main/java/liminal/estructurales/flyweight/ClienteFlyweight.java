package liminal.estructurales.flyweight;

import java.io.PrintStream;

/** Flyweight: muchas paredes comparten una textura inmutable. */
public final class ClienteFlyweight {
    private ClienteFlyweight() {}

    public static void ejecutar(PrintStream out) {
        FabricaTexturas fabrica = new FabricaTexturas();
        Pared a = new Pared("M-01", fabrica.obtener("papel", "amarillo"));
        Pared b = new Pared("M-02", fabrica.obtener("papel", "amarillo"));
        out.println("Paredes distintas: " + (a != b));
        out.println("Textura compartida: " + (a.textura() == b.textura()));
        out.println("Texturas almacenadas: " + fabrica.cantidad());
    }

    public static void main(String[] args) { ejecutar(System.out); }
}
