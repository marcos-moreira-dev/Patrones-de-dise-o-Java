

/**
 * CLASE CONCRETA: ConcreteComponent
 *
 * ROL EN EL PATRÓN:
 *  - Implementa la lógica específica de los componentes que participan en la mediación.
 *
 * PROPÓSITO:
 *  Mostrar cómo los objetos concretos se comunican exclusivamente a través del Mediador.
 *
 * EJEMPLO EN BACKEND:
 *  - Servicio “Notificaciones” que recibe mensajes del Mediador cuando se crea un pedido.
 *
 * EJEMPLO EN PIZZERÍA ONLINE:
 *  - Componente “Cocina” que notifica a “Caja” cuando un pedido está listo.
 *
 * USO EN CLASE CLIENTE:
 *  Component cocina = new ConcreteComponent("Cocina", m);
 *  cocina.send("Pedido #101 listo");
 *
 * SALIDA ESPERADA:
 *  [Cocina] envió mensaje: Pedido #101 listo
 *  [Caja] recibió: Pedido #101 listo
 */
public class ConcreteComponent extends Component {

    public ConcreteComponent(String name, Mediator mediator) {
        super(name, mediator);
    }

    @Override
    public void receive(String message) {
        System.out.println("[" + name + "] recibió: " + message);
    }
}
