
/**
 * CLASE CONCRETA: RefinedAbstraction
 *
 * ROL EN EL PATRÓN:
 *  - Extiende la interfaz de la abstracción y puede añadir operaciones específicas.
 *
 * PROPÓSITO:
 *  Agregar comportamiento adicional de alto nivel usando la implementación subyacente.
 *
 * EJEMPLO EN BACKEND:
 *  - Controlador que usa diferentes repositorios o adaptadores según el contexto.
 *
 * EJEMPLO EN PIZZERÍA ONLINE:
 *  - Plataforma de pedidos que decide qué tipo de cocina usar.
 *
 * USO EN CLASE CLIENTE:
 *  Implementor cocina = new ConcreteImplementorB();
 *  Abstraction sistema = new RefinedAbstraction(cocina);
 *  sistema.realizarOperacion();
 *
 * SALIDA ESPERADA:
 *  [Sistema de pedidos] Enviando orden al sistema de cocina...
 *  [Cocina Automatizada] Robot preparando pizza con precisión perfecta.
 */
public class RefinedAbstraction extends Abstraction {

    public RefinedAbstraction(Implementor implementor) {
        super(implementor);
    }

    @Override
    public void realizarOperacion() {
        System.out.println("[Sistema de pedidos] Enviando orden al sistema de cocina...");
        implementor.operationImpl();
    }
}
