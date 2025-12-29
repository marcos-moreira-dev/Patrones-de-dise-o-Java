package estructurales.Composite;

/**
 * CLASE CONCRETA: Leaf
 *
 * ROL EN EL PATRÓN:
 *  - Representa los objetos individuales (hojas) de la jerarquía.
 *  - No tiene hijos ni otros componentes.
 *
 * PROPÓSITO:
 *  Ejecutar el comportamiento real en los elementos simples de la estructura.
 *
 * EJEMPLO EN BACKEND:
 *  - Archivo individual en un sistema de archivos.
 *
 * EJEMPLO EN PIZZERÍA ONLINE:
 *  - Un producto individual (Pizza, Refresco, Postre).
 *
 * USO EN CLASE CLIENTE:
 *  Component pizza = new Leaf("Pizza Napolitana");
 *  pizza.showDetails();
 *
 * SALIDA ESPERADA:
 *   - Pizza Napolitana
 */
public class Leaf implements Component {

    private final String nombre;

    public Leaf(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public void showDetails() {
        System.out.println("  - " + nombre);
    }
}
