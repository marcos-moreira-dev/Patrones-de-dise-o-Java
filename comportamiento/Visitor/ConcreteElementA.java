package comportamiento.Visitor;

/**
 * CLASE CONCRETA: ConcreteElementA
 *
 * ROL EN EL PATRÓN:
 *  - Implementa la interfaz `Element`.
 *  - Define un tipo concreto de elemento que puede ser visitado.
 *
 * PROPÓSITO:
 *  Proveer una estructura de datos u objeto específico sobre el cual el Visitor
 *  puede realizar una operación particular.
 *
 * CRITERIOS DE USO:
 *  - Cuando se necesita diferenciar el comportamiento al ser visitado.
 *
 * EJEMPLO EN BACKEND:
 *  - Un endpoint o nodo de configuración.
 *
 * EJEMPLO EN PIZZERÍA ONLINE:
 *  - Un pedido “Delivery” con un comportamiento particular al ser analizado
 *    (por ejemplo, aplicar una tarifa de envío distinta).
 *
 * USO EN CLASE CLIENTE:
 *  Visitor visitor = new ConcreteVisitor();
 *  Element e = new ConcreteElementA();
 *  e.accept(visitor);
 */
public class ConcreteElementA implements Element {

    public String operationA() {
        return "Elemento A procesado.";
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visitConcreteElementA(this);
    }
}
