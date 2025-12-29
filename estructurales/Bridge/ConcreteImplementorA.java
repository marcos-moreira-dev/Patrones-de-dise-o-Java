package estructurales.Bridge;

/**
 * CLASE CONCRETA: ConcreteImplementorA
 *
 * ROL EN EL PATRÓN:
 *  - Implementa la interfaz Implementor.
 *  - Representa una versión específica de la implementación.
 *
 * PROPÓSITO:
 *  Proveer una implementación concreta para la abstracción.
 *
 * EJEMPLO EN BACKEND:
 *  - Proveedor de base de datos MySQL.
 *
 * EJEMPLO EN PIZZERÍA ONLINE:
 *  - Cocina tradicional que procesa los pedidos manualmente.
 *
 * USO EN CLASE CLIENTE:
 *  Implementor cocina = new ConcreteImplementorA();
 *  cocina.operationImpl();
 *
 * SALIDA ESPERADA:
 *  [Cocina Tradicional] Preparando pedido manualmente.
 */
public class ConcreteImplementorA implements Implementor {
    @Override
    public void operationImpl() {
        System.out.println("[Cocina Tradicional] Preparando pedido manualmente.");
    }
}
