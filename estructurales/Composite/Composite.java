package estructurales.Composite;
import java.util.ArrayList;
import java.util.List;

/**
 * CLASE CONCRETA: Composite
 *
 * ROL EN EL PATRÓN:
 *  - Representa los nodos compuestos (con hijos) en la jerarquía.
 *  - Implementa operaciones para agregar o eliminar componentes.
 *
 * PROPÓSITO:
 *  Permitir que los objetos compuestos (como combos o menús) contengan otros componentes.
 *
 * CRITERIOS DE USO:
 *  ✅ Cuando necesitas manejar estructuras jerárquicas complejas.
 *  ✅ Cuando quieres que el cliente trate igual los nodos y las hojas.
 *
 * EJEMPLO EN BACKEND:
 *  - Menú de aplicación, árbol de categorías o estructuras JSON anidadas.
 *
 * EJEMPLO EN PIZZERÍA ONLINE:
 *  - Un combo de productos que contiene pizzas y bebidas.
 *
 * USO EN CLASE CLIENTE:
 *  Composite combo = new Composite("Combo Familiar");
 *  combo.add(new Leaf("Pizza Napolitana"));
 *  combo.add(new Leaf("Refresco"));
 *  combo.showDetails();
 *
 * SALIDA ESPERADA:
 *  Combo: Combo Familiar
 *   - Pizza Napolitana
 *   - Refresco
 */
public class Composite implements Component {

    private final String nombre;
    private final List<Component> hijos = new ArrayList<>();

    public Composite(String nombre) {
        this.nombre = nombre;
    }

    public void add(Component component) {
        hijos.add(component);
    }

    public void remove(Component component) {
        hijos.remove(component);
    }

    @Override
    public void showDetails() {
        System.out.println("Combo: " + nombre);
        for (Component hijo : hijos) {
            hijo.showDetails();
        }
    }
}
