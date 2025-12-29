

/**
 * INTERFAZ: Element
 *
 * ROL EN EL PATRÓN:
 *  - Define la interfaz común para los objetos que pueden ser visitados.
 *  - Cada elemento implementa el método `accept(Visitor v)` que
 *    delega la ejecución al Visitor correspondiente.
 *
 * PROPÓSITO:
 *  Permitir que un objeto "Visitor" pueda recorrer distintas clases concretas
 *  sin acoplar su lógica interna.
 *
 * EJEMPLO EN BACKEND:
 *  - Representar diferentes tipos de datos (nodos de un árbol JSON, endpoints, etc.)
 *    que pueden ser inspeccionados por un Visitor (analizador, reportador, validador).
 *
 * EJEMPLO EN PIZZERÍA ONLINE:
 *  - Pedidos o productos visitables para aplicar descuentos o auditorías.
 *
 * USO EN CLASE CLIENTE:
 *  Element e = new ConcreteElementA();
 *  Visitor v = new ConcreteVisitor();
 *  e.accept(v);
 */
public interface Element {
    void accept(Visitor visitor);
}
