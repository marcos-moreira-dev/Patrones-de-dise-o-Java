package estructurales.Bridge;
/**
 * CLASE ABSTRACTA: Abstraction
 *
 * ROL EN EL PATRÓN:
 *  - Define la interfaz de alto nivel que usa el cliente.
 *  - Contiene una referencia a un Implementor para delegar la ejecución real.
 *
 * PROPÓSITO:
 *  Mantener una interfaz estable mientras las implementaciones pueden variar.
 *
 * EJEMPLO EN BACKEND:
 *  - Servicio de negocio que usa distintas capas de persistencia (SQL, NoSQL, etc.).
 *
 * EJEMPLO EN PIZZERÍA ONLINE:
 *  - Sistema de pedidos que puede funcionar con diferentes cocinas.
 *
 * USO EN CLASE CLIENTE:
 *  Abstraction sistema = new RefinedAbstraction(new ConcreteImplementorA());
 *  sistema.realizarOperacion();
 *
 * SALIDA ESPERADA:
 *  [Sistema de pedidos] Enviando orden al sistema de cocina...
 *  [Cocina Tradicional] Preparando pedido manualmente.
 */
public abstract class Abstraction {
    protected Implementor implementor;

    /**
     * Vincula la abstraccion con una implementacion intercambiable.
     *
     * @param implementor mecanismo concreto al que se delegaran las operaciones
     */
    public Abstraction(Implementor implementor) {
        this.implementor = implementor;
    }

    /** Coordina una operacion de alto nivel sin fijar la implementacion utilizada. */
    public abstract void realizarOperacion();
}
