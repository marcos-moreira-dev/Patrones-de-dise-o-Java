package liminal.creacionales.abstractfactory;

import java.util.Objects;

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
