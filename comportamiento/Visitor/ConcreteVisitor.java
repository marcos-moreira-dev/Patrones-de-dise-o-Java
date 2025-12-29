package comportamiento.Visitor;

/**
 * CLASE CONCRETA: ConcreteVisitor
 *
 * ROL EN EL PATRÓN:
 *  - Implementa la interfaz `Visitor`.
 *  - Define las acciones específicas que se ejecutarán sobre cada tipo de elemento.
 *
 * PROPÓSITO:
 *  Permitir operaciones especializadas sobre los distintos elementos,
 *  sin modificar su estructura ni romper el principio de abierto/cerrado.
 *
 * CRITERIOS DE USO:
 *  ✅ Cuando deseas realizar múltiples operaciones sobre una jerarquía de clases.
 *  ✅ Cuando necesitas centralizar la lógica de esas operaciones fuera de los modelos.
 *
 * EJEMPLO EN BACKEND:
 *  - Un visitante que genera reportes o realiza validaciones sobre objetos del dominio.
 *
 * EJEMPLO EN PIZZERÍA ONLINE:
 *  - Un visitante que recorre todos los pedidos:
 *      • Si es `ConcreteElementA` (Delivery) calcula costo de envío.
 *      • Si es `ConcreteElementB` (Presencial) aplica descuento.
 *
 * USO EN CLASE CLIENTE:
 *  Visitor visitor = new ConcreteVisitor();
 *  Element a = new ConcreteElementA();
 *  Element b = new ConcreteElementB();
 *  a.accept(visitor);
 *  b.accept(visitor);
 */
public class ConcreteVisitor implements Visitor {

    @Override
    public void visitConcreteElementA(ConcreteElementA elementA) {
        System.out.println("Visitando A → " + elementA.operationA());
    }

    @Override
    public void visitConcreteElementB(ConcreteElementB elementB) {
        System.out.println("Visitando B → " + elementB.operationB());
    }
}
