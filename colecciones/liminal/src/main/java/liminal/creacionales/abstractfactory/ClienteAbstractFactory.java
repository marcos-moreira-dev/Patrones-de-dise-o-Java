package liminal.creacionales.abstractfactory;

import java.io.PrintStream;
import java.util.Objects;

/** Abstract Factory: familias coherentes de objetos ambientales. */
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
        if (amarillo.tema() != Tema.AMARILLO || piscinas.tema() != Tema.PISCINAS) {
            throw new AssertionError("Familias intercambiadas");
        }
        if (!amarillo.puerta().estado().equals("CERRADA")) {
            throw new AssertionError("Puerta inicial debe estar cerrada");
        }
        try {
            new Escena(new LuzAmbiental(Tema.AMARILLO, "amarilla"),
                    new PuertaAmbiental(Tema.PISCINAS, "azul", "CERRADA"));
            throw new AssertionError("Una familia mezclada debe rechazarse");
        } catch (IllegalArgumentException esperado) {
            // Ninguna escena incompatible se construye.
        }
    }

    public static void main(String[] args) { ejecutar(System.out); }
}

enum Tema { AMARILLO, PISCINAS }
interface LuzDelNivel {
    Tema tema();
    String aspecto();
}
interface PuertaDelNivel {
    Tema tema();
    String aspecto();
    String estado();
}

record LuzAmbiental(Tema tema, String aspecto) implements LuzDelNivel {}
record PuertaAmbiental(Tema tema, String aspecto, String estado) implements PuertaDelNivel {}

interface FabricaAmbiente {
    LuzDelNivel crearLuz();
    PuertaDelNivel crearPuerta();
}
final class FabricaAmarilla implements FabricaAmbiente {
    public LuzDelNivel crearLuz() { return new LuzAmbiental(Tema.AMARILLO, "fluorescente amarilla"); }
    public PuertaDelNivel crearPuerta() {
        return new PuertaAmbiental(Tema.AMARILLO, "puerta beige", "CERRADA");
    }
}
final class FabricaPiscinas implements FabricaAmbiente {
    public LuzDelNivel crearLuz() { return new LuzAmbiental(Tema.PISCINAS, "reflejo azul"); }
    public PuertaDelNivel crearPuerta() {
        return new PuertaAmbiental(Tema.PISCINAS, "puerta blanca", "CERRADA");
    }
}

final class Escena {
    private final LuzDelNivel luz;
    private final PuertaDelNivel puerta;

    Escena(FabricaAmbiente fabrica) {
        this(Objects.requireNonNull(fabrica).crearLuz(), fabrica.crearPuerta());
    }
    Escena(LuzDelNivel luz, PuertaDelNivel puerta) {
        this.luz = Objects.requireNonNull(luz);
        this.puerta = Objects.requireNonNull(puerta);
        if (luz.tema() != puerta.tema()) throw new IllegalArgumentException("Familias incompatibles");
        if (!puerta.estado().equals("CERRADA")) throw new IllegalArgumentException("Estado inicial incorrecto");
    }
    Tema tema() { return luz.tema(); }
    PuertaDelNivel puerta() { return puerta; }
    String descripcion() {
        return tema() + ": " + luz.aspecto() + " y " + puerta.aspecto();
    }
}
