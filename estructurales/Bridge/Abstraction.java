
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

    public Abstraction(Implementor implementor) {
        this.implementor = implementor;
    }

    public abstract void realizarOperacion();
}
