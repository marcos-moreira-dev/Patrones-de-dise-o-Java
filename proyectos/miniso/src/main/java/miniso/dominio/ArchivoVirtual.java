package miniso.dominio;

import java.nio.charset.StandardCharsets;
import java.util.Objects;

/** Hoja inmutable del árbol: el contenido reside solo en memoria del simulador. */
public record ArchivoVirtual(String nombre, String contenido) implements EntradaVirtual {
    public ArchivoVirtual {
        if (!DirectorioVirtual.nombreValido(nombre)) {
            throw new IllegalArgumentException("Nombre de archivo virtual invalido");
        }
        Objects.requireNonNull(contenido, "contenido");
    }

    @Override public long bytes() { return contenido.getBytes(StandardCharsets.UTF_8).length; }

    @Override public void dibujar(StringBuilder salida, int nivel) {
        salida.append("  ".repeat(nivel)).append(nombre)
                .append(" (").append(bytes()).append(" B)").append('\n');
    }
}
