
/**
 * CLASE CONCRETA: ConcreteImplementorB
 *
 * ROL EN EL PATRÓN:
 *  - Otra implementación diferente del mismo tipo de operación.
 *
 * PROPÓSITO:
 *  Mostrar cómo pueden coexistir múltiples implementaciones bajo una misma abstracción.
 *
 * EJEMPLO EN BACKEND:
 *  - Proveedor de base de datos MongoDB o API externa.
 *
 * EJEMPLO EN PIZZERÍA ONLINE:
 *  - Cocina automatizada que usa robots para preparar las pizzas.
 *
 * USO EN CLASE CLIENTE:
 *  Implementor cocina = new ConcreteImplementorB();
 *  cocina.operationImpl();
 *
 * SALIDA ESPERADA:
 *  [Cocina Automatizada] Robot preparando pizza con precisión perfecta.
 */
public class ConcreteImplementorB implements Implementor {
    @Override
    public void operationImpl() {
        System.out.println("[Cocina Automatizada] Robot preparando pizza con precisión perfecta.");
    }
}
