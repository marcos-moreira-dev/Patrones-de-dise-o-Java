

/**
 * CLASE CONCRETA: ConcreteElementB
 *
 * ROL EN EL PATRÓN:
 *  - Implementa la interfaz `Element`.
 *  - Representa otro tipo concreto que puede ser visitado por el Visitor.
 *
 * PROPÓSITO:
 *  Proveer una variación de elemento que pueda ser tratada de forma distinta
 *  por los visitantes, sin cambiar la jerarquía original.
 *
 * CRITERIOS DE USO:
 *  - Cuando distintos tipos de elementos deben comportarse diferente ante un Visitor.
 *
 * EJEMPLO EN BACKEND:
 *  - Un registro de auditoría o un módulo de reportes.
 *
 * EJEMPLO EN PIZZERÍA ONLINE:
 *  - Un pedido “Presencial” con lógica diferente al ser procesado
 *    (sin costos de envío o con promociones especiales).
 *
 * USO EN CLASE CLIENTE:
 *  Visitor visitor = new ConcreteVisitor();
 *  Element e = new ConcreteElementB();
 *  e.accept(visitor);
 */
public class ConcreteElementB implements Element {

    public String operationB() {
        return "Elemento B procesado.";
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visitConcreteElementB(this);
    }
}
