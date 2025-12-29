package estructurales.Composite;

/**
 * PATRÓN: Composite (Compuesto)
 *
 * INTERFAZ: Component
 *
 * ROL EN EL PATRÓN:
 *  - Define la interfaz común para los objetos individuales (Leaf) y los compuestos (Composite).
 *  - Permite tratar objetos individuales y grupos de objetos de la misma manera.
 *
 * PROPÓSITO:
 *  Representar jerarquías de objetos (estructuras tipo árbol) donde los clientes
 *  pueden interactuar con elementos simples y compuestos de forma uniforme.
 *
 * CRITERIOS DE USO:
 *  ✅ Cuando necesitas manejar jerarquías de objetos (como menús, archivos, hilos).
 *  ✅ Cuando deseas tratar elementos individuales y colecciones de la misma manera.
 *
 * VENTAJAS:
 *  + Simplifica el código cliente al unificar el trato de objetos simples y complejos.
 *  + Facilita la construcción de estructuras recursivas.
 *
 * DESVENTAJAS:
 *  - Puede complicar la implementación si las clases hijas tienen comportamientos muy distintos.
 *
 * EJEMPLO EN BACKEND:
 *  - Árbol de permisos, estructura de carpetas o jerarquía de categorías.
 *
 * EJEMPLO EN PIZZERÍA ONLINE:
 *  - Un “Combo Familiar” que contiene pizzas, bebidas y postres como componentes.
 *
 * USO EN CLASE CLIENTE:
 *  Component combo = new Composite("Combo Familiar");
 *  combo.add(new Leaf("Pizza Napolitana"));
 *  combo.add(new Leaf("Refresco"));
 *  combo.showDetails();
 *
 * SALIDA ESPERADA:
 *  Combo: Combo Familiar
 *   - Pizza Napolitana
 *   - Refresco
 */
public interface Component {
    void showDetails();
}
