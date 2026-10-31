package miniso.dominio;

/** Composite: un archivo y un directorio comparten nombre, tamaño y representación. */
public sealed interface EntradaVirtual permits ArchivoVirtual, DirectorioVirtual {
    String nombre();
    long bytes();
    void dibujar(StringBuilder salida, int nivel);
}
