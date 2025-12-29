

/**
 * PATRÓN: Observer
 *
 * CLASE CONCRETA: ConcreteObserver
 *
 * ROL EN EL PATRÓN:
 *  - Recibe actualizaciones del sujeto y reacciona (log, enviar email, actualizar UI, etc.).
 *
 * PROPÓSITO:
 *  - Implementar la acción concreta ante cada notificación del Subject.
 *
 * EJEMPLO BACKEND:
 *  - Un observador que registra auditoría o dispara un workflow.
 *
 * EJEMPLO PIZZERÍA:
 *  - "Cocina", "Delivery", "Caja" actúan al recibir el evento.
 *
 * USO EN CLASE CLIENTE:
 *  subject.attach(new ConcreteObserver("Delivery"));
 *
 * SALIDA ESPERADA:
 *  [Delivery] recibió: Estado cambiado: Pedido #101 LISTO
 */
public class ConcreteObserver implements Observer {
    private final String name;

    public ConcreteObserver(String name) {
        this.name = name;
    }

    @Override
    public void update(String message) {
        System.out.println("[" + name + "] recibió: " + message);
    }
}
