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

/** Producto concreto de la familia amarilla. */
record LuzAmarilla() implements LuzDelNivel {
    public Tema tema() { return Tema.AMARILLO; }
    public String aspecto() { return "fluorescente amarilla"; }
}

/** Producto concreto de la familia amarilla; solo describe su estado inicial. */
record PuertaAmarilla() implements PuertaDelNivel {
    public Tema tema() { return Tema.AMARILLO; }
    public String aspecto() { return "puerta beige"; }
    public String estado() { return "CERRADA"; }
}

/** Producto concreto de la familia de piscinas. */
record LuzPiscinas() implements LuzDelNivel {
    public Tema tema() { return Tema.PISCINAS; }
    public String aspecto() { return "reflejo azul"; }
}

/** Producto concreto de la familia de piscinas; solo describe su estado inicial. */
record PuertaPiscinas() implements PuertaDelNivel {
    public Tema tema() { return Tema.PISCINAS; }
    public String aspecto() { return "puerta blanca"; }
    public String estado() { return "CERRADA"; }
}

interface FabricaAmbiente {
    LuzDelNivel crearLuz();
    PuertaDelNivel crearPuerta();
}

final class FabricaAmarilla implements FabricaAmbiente {
    public LuzDelNivel crearLuz() { return new LuzAmarilla(); }
    public PuertaDelNivel crearPuerta() { return new PuertaAmarilla(); }
}

final class FabricaPiscinas implements FabricaAmbiente {
    public LuzDelNivel crearLuz() { return new LuzPiscinas(); }
    public PuertaDelNivel crearPuerta() { return new PuertaPiscinas(); }
}

/** Cliente del contrato: no conoce las clases concretas de los productos. */
final class Escena {
    private final LuzDelNivel luz;
    private final PuertaDelNivel puerta;

    Escena(FabricaAmbiente fabrica) {
        this(Objects.requireNonNull(fabrica).crearLuz(), fabrica.crearPuerta());
    }

    Escena(LuzDelNivel luz, PuertaDelNivel puerta) {
        this.luz = Objects.requireNonNull(luz);
        this.puerta = Objects.requireNonNull(puerta);
        if (luz.tema() != puerta.tema()) {
            throw new IllegalArgumentException("Familias incompatibles");
        }
        if (!puerta.estado().equals("CERRADA")) {
            throw new IllegalArgumentException("Estado inicial incorrecto");
        }
    }

    Tema tema() { return luz.tema(); }
    LuzDelNivel luz() { return luz; }
    PuertaDelNivel puerta() { return puerta; }

    String descripcion() {
        return tema() + ": " + luz.aspecto() + " y " + puerta.aspecto();
    }
}
