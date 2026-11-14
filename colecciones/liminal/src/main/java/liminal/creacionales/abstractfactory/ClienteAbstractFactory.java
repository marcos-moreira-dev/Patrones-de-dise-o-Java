package liminal.creacionales.abstractfactory;

import java.io.PrintStream;
import java.util.Objects;

/** Abstract Factory: cada fábrica construye una familia de productos concretos. */
public final class ClienteAbstractFactory {
    private ClienteAbstractFactory() {}

    public static void ejecutar(PrintStream out) {
        Escena amarillo = new Escena(new FabricaAmarilla());
        Escena piscinas = new Escena(new FabricaPiscinas());
        out.println(amarillo.descripcion());
        out.println(piscinas.descripcion());
    }

    public static void probar() {
        Escena amarillo = new Escena(new FabricaAmarilla());
        Escena piscinas = new Escena(new FabricaPiscinas());

        if (!(amarillo.luz() instanceof LuzAmarilla)
                || !(amarillo.puerta() instanceof PuertaAmarilla)) {
            throw new AssertionError("La fábrica amarilla debe crear su familia concreta");
        }
        if (!(piscinas.luz() instanceof LuzPiscinas)
                || !(piscinas.puerta() instanceof PuertaPiscinas)) {
            throw new AssertionError("La fábrica de piscinas debe crear su familia concreta");
        }
        if (amarillo.tema() != Tema.AMARILLO || piscinas.tema() != Tema.PISCINAS) {
            throw new AssertionError("Temas de fábrica intercambiados");
        }
        if (!amarillo.puerta().estado().equals("CERRADA")
                || !piscinas.puerta().estado().equals("CERRADA")) {
            throw new AssertionError("Las representaciones de puerta comienzan CERRADAS");
        }
        try {
            new Escena(new LuzAmarilla(), new PuertaPiscinas());
            throw new AssertionError("Una familia mezclada debe rechazarse");
        } catch (IllegalArgumentException esperado) {
            // No se construye una escena incoherente.
        }
        try {
            new Escena(new FabricaAmbiente() {
                public LuzDelNivel crearLuz() { return new LuzPiscinas(); }
                public PuertaDelNivel crearPuerta() { return new PuertaAmarilla(); }
            });
            throw new AssertionError("Una fábrica mal implementada no debe crear una escena válida");
        } catch (IllegalArgumentException esperado) {
            // Escena protege la coherencia incluso ante una fábrica defectuosa.
        }
    }

    public static void main(String[] args) { ejecutar(System.out); }
}
