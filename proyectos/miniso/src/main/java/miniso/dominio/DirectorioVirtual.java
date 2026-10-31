package miniso.dominio;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Nodo Composite: contiene subdirectorios y archivos, sin acceso al disco real.
 * El árbol no puede tener ciclos: se crean hijos nuevos, nunca se enlazan nodos ajenos.
 */
public final class DirectorioVirtual implements EntradaVirtual {
    private final String nombre;
    private final Map<String, EntradaVirtual> hijos = new LinkedHashMap<>();

    public DirectorioVirtual(String nombre) {
        if (!nombreValido(nombre)) {
            throw new IllegalArgumentException("Nombre de directorio virtual invalido");
        }
        this.nombre = nombre;
    }

    private DirectorioVirtual() { this.nombre = ""; } // Solo la raíz carece de nombre.

    public static DirectorioVirtual raiz() { return new DirectorioVirtual(); }

    /** Contrato compartido por archivos y directorios; no acepta rutas como nombres. */
    static boolean nombreValido(String nombre) {
        return nombre != null && !nombre.equals(".") && !nombre.equals("..")
                && nombre.matches("[\\p{L}\\p{N}_.-]+");
    }

    @Override public String nombre() { return nombre; }

    /** Búsqueda O(1) promedio por componente del camino. */
    public EntradaVirtual buscar(String nombre) { return hijos.get(nombre); }

    /** Añade un directorio vacío; no altera un hijo ya existente. */
    public boolean crearDirectorio(String nombre) {
        if (!nombreValido(nombre)) throw new IllegalArgumentException("Nombre de directorio invalido");
        if (hijos.containsKey(nombre)) return false;
        hijos.put(nombre, new DirectorioVirtual(nombre));
        return true;
    }

    /** Crea o reemplaza un archivo, pero nunca reemplaza un directorio. */
    public boolean escribirArchivo(String nombre, String contenido) {
        if (!nombreValido(nombre) || contenido == null) {
            throw new IllegalArgumentException("Archivo virtual invalido");
        }
        if (hijos.get(nombre) instanceof DirectorioVirtual) return false;
        hijos.put(nombre, new ArchivoVirtual(nombre, contenido));
        return true;
    }

    /** Recorre recursivamente todo el subárbol: O(n) entradas. */
    @Override public long bytes() {
        long total = 0;
        for (EntradaVirtual hijo : hijos.values()) total += hijo.bytes();
        return total;
    }

    /** Recorre recursivamente todo el subárbol: O(n) entradas. */
    @Override public void dibujar(StringBuilder salida, int nivel) {
        salida.append("  ".repeat(nivel)).append(nombre).append('/').append('\n');
        for (EntradaVirtual hijo : hijos.values()) hijo.dibujar(salida, nivel + 1);
    }
}
