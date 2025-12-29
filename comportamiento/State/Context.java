package comportamiento.State;

/**
 * CLASE: Context
 *
 * ROL EN EL PATRÓN:
 *  - Mantiene una referencia al estado actual.
 *  - Permite cambiar dinámicamente el comportamiento delegando al objeto estado.
 *
 * PROPÓSITO:
 *  Representar el objeto que cambia su comportamiento según su estado interno.
 *
 * CRITERIOS DE USO:
 *  ✅ Cuando un objeto tiene múltiples modos de operación.
 *  ✅ Cuando cada modo debe comportarse de forma diferente.
 *
 * EJEMPLO EN BACKEND:
 *  - Pedido con estados “Pendiente”, “En Proceso”, “Completado”.
 *
 * EJEMPLO EN PIZZERÍA ONLINE:
 *  - Ciclo de vida de un pedido (preparación, envío, entrega).
 *
 * USO EN CLASE CLIENTE:
 *  Context pedido = new Context();
 *  pedido.request();
 *  pedido.request();
 *
 * SALIDA ESPERADA:
 *  Estado actual: A
 *  Cambiando a estado B
 *  Estado actual: B
 *  Cambiando a estado A
 */
public class Context {
    private State state;

    public Context() {
        this.state = new ConcreteStateA(); // Estado inicial
    }

    public void setState(State state) {
        this.state = state;
    }

    public void request() {
        state.handle(this);
    }
}
