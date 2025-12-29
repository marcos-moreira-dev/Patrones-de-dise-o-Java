

/**
 * CLASE CONCRETA: ConcreteStateB
 *
 * ROL EN EL PATRÓN:
 *  - Implementa un comportamiento específico asociado al estado B.
 *  - Decide la transición hacia otro estado.
 *
 * PROPÓSITO:
 *  Representar otro estado del Contexto con su propio comportamiento.
 *
 * EJEMPLO EN BACKEND:
 *  - Pedido “En reparto”.
 *
 * EJEMPLO EN PIZZERÍA ONLINE:
 *  - Pedido “Entregado”.
 *
 * USO EN CLASE CLIENTE:
 *  Context pedido = new Context();
 *  pedido.setState(new ConcreteStateB());
 *  pedido.request();
 *
 * SALIDA ESPERADA:
 *  Estado actual: B
 *  Cambiando a estado A
 */
public class ConcreteStateB implements State {

    @Override
    public void handle(Context context) {
        System.out.println("Estado actual: B");
        context.setState(new ConcreteStateA());
        System.out.println("Cambiando a estado A");
    }
}
