package miniso.aplicacion;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import miniso.dominio.ArchivoVirtual;
import miniso.dominio.DirectorioVirtual;
import miniso.dominio.EntradaVirtual;

/**
 * Casos de uso de archivos simulados, independientes del disco del anfitrión.
 * Los caminos son absolutos, separados por /, sin . ni .. ni enlaces simbólicos.
 */
public final class SistemaArchivosVirtual {
    private final DirectorioVirtual raiz = DirectorioVirtual.raiz();

    /** CU-SO05: crea un directorio; el padre debe existir. */
    public ResultadoOperacion crearDirectorio(String camino) {
        List<String> segmentos = segmentos(camino);
        if (segmentos == null || segmentos.isEmpty()) {
            return ResultadoOperacion.rechazada("Ruta de directorio invalida");
        }
        DirectorioVirtual padre = buscarPadre(segmentos);
        if (padre == null) return ResultadoOperacion.rechazada("Directorio padre inexistente");
        String nombre = segmentos.get(segmentos.size() - 1);
        if (!padre.crearDirectorio(nombre)) {
            return ResultadoOperacion.rechazada("Ya existe una entrada con ese nombre");
        }
        return ResultadoOperacion.aceptada("Directorio creado: " + camino);
    }

    /** CU-SO06: crea o reemplaza texto en un archivo exclusivamente virtual. */
    public ResultadoOperacion escribir(String camino, String contenido) {
        List<String> segmentos = segmentos(camino);
        if (segmentos == null || segmentos.isEmpty() || contenido == null) {
            return ResultadoOperacion.rechazada("Ruta o contenido invalido");
        }
        DirectorioVirtual padre = buscarPadre(segmentos);
        if (padre == null) return ResultadoOperacion.rechazada("Directorio padre inexistente");
        if (!padre.escribirArchivo(segmentos.get(segmentos.size() - 1), contenido)) {
            return ResultadoOperacion.rechazada("La ruta corresponde a un directorio");
        }
        return ResultadoOperacion.aceptada("Archivo guardado en memoria: " + camino);
    }

    /** CU-SO07: lectura inmutable; no hay operaciones sobre archivos reales. */
    public Optional<String> leer(String camino) {
        List<String> segmentos = segmentos(camino);
        if (segmentos == null || segmentos.isEmpty()) return Optional.empty();
        DirectorioVirtual padre = buscarPadre(segmentos);
        if (padre != null && padre.buscar(segmentos.get(segmentos.size() - 1)) instanceof ArchivoVirtual archivo) {
            return Optional.of(archivo.contenido());
        }
        return Optional.empty();
    }

    /** CU-SO08: recorre el árbol de forma recursiva mediante Composite. */
    public String arbol() {
        StringBuilder salida = new StringBuilder();
        raiz.dibujar(salida, 0);
        return salida.toString();
    }

    public long bytesTotales() { return raiz.bytes(); }

    private DirectorioVirtual buscarPadre(List<String> segmentos) {
        DirectorioVirtual actual = raiz;
        for (int i = 0; i < segmentos.size() - 1; i++) {
            EntradaVirtual entrada = actual.buscar(segmentos.get(i));
            if (!(entrada instanceof DirectorioVirtual directorio)) return null;
            actual = directorio;
        }
        return actual;
    }

    private static List<String> segmentos(String camino) {
        if (camino == null || !camino.startsWith("/") || (camino.length() > 1 && camino.endsWith("/"))) {
            return null;
        }
        if (camino.equals("/")) return List.of();
        List<String> partes = Arrays.asList(camino.substring(1).split("/", -1));
        for (String parte : partes) {
            if (parte.equals(".") || parte.equals("..")
                    || !parte.matches("[\\p{L}\\p{N}_.-]+")) return null;
        }
        return partes;
    }
}
