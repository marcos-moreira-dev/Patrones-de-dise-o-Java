package liminal.creacionales.builder.variantes.director;

import java.util.Objects;

/** Director: decide la secuencia, sin conocer la clase concreta del Builder. */
final class DirectorAmbientes {
    void construirPasillo(String id, ConstructorEscenario builder) {
        Objects.requireNonNull(builder);
        builder.comenzar(id);
        builder.elegirAmbiente("Pasillo amarillo");
        builder.instalarLuces(4);
        builder.colocarPuerta(true);
    }

    void construirPiscina(String id, ConstructorEscenario builder) {
        Objects.requireNonNull(builder);
        builder.comenzar(id);
        builder.elegirAmbiente("Piscina vacía");
        builder.instalarLuces(2);
        builder.colocarPuerta(false);
    }
}
