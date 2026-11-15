package comportamiento.State;

/**
 * CLASE CONCRETA: ConcreteStateA
 *
 * ROL EN EL PATRÓN:
 *  - Implementa un comportamiento específico asociado al estado A.
 *  - Decide la transición hacia otro estado.
 *
 * PROPÓSITO:
 *  Representar un estado particular del Contexto con su propia lógica.
 *
 * EJEMPLO EN BACKEND:
 *  - Pedido “Pendiente”.
 *
 * EJEMPLO EN PIZZERÍA ONLINE:
 *  - Pedido “Preparando”.
 *
 * USO EN CLASE CLIENTE:
 *  Context pedido = new Context();
 *  pedido.setState(new ConcreteStateA());
 *  pedido.request();
 *
 * SALIDA ESPERADA:
 *  Estado actual: A
 *  Cambiando a estado B
 */
public class ConcreteStateA implements State {

    @Override
    public void handle(Context context) {
        System.out.println("Estado actual: A");
        context.setState(new ConcreteStateB());
        System.out.println("Cambiando a estado B");
    }
}
