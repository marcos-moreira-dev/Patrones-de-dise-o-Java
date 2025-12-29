

/**
 * PATRÓN: Observer
 *
 * CLASE CONCRETA: ConcreteSubject
 *
 * ROL EN EL PATRÓN:
 *  - Contiene el estado que, al cambiar, dispara notificaciones a los observadores.
 *
 * PROPÓSITO:
 *  - Encapsular el origen del evento/cambio y difundirlo sin acoplarse a receptores concretos.
 *
 * EJEMPLO BACKEND:
 *  - Servicio de Pedidos publica eventos: "PedidoCreado", "PedidoListo", "PedidoEnviado".
 *
 * EJEMPLO PIZZERÍA:
 *  - Al marcar un pedido como “LISTO”, cocina notifica a “Delivery” y a “FrontDesk”.
 *
 * USO EN CLASE CLIENTE:
 *  ConcreteSubject pedidos = new ConcreteSubject();
 *  pedidos.attach(new ConcreteObserver("Cocina"));
 *  pedidos.setState("Pedido #101 LISTO");
 *
 * SALIDA ESPERADA:
 *  [Cocina] recibió: Pedido #101 LISTO
 *  [Delivery] recibió: Pedido #101 LISTO   (si también está suscrito)
 */
public class ConcreteSubject extends Subject {
    private String state;

    public void setState(String newState) {
        this.state = newState;
        // Notificar inmediatamente a los observadores
        notifyObservers("Estado cambiado: " + state);
    }

    public String getState() {
        return state;
    }
}
