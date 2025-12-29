package comportamiento.Mediator;

import java.util.ArrayList;
import java.util.List;

/**
 * CLASE CONCRETA: ConcreteMediator
 *
 * ROL EN EL PATRÓN:
 *  - Implementa la lógica central de comunicación entre componentes.
 *  - Mantiene una lista de componentes registrados y dirige los mensajes.
 *
 * PROPÓSITO:
 *  Coordinar la comunicación sin que los componentes se conozcan entre sí.
 *
 * CRITERIOS DE USO:
 *  ✅ Cuando los componentes deben comunicarse de forma controlada y centralizada.
 *  ✅ Cuando agregar más componentes no debe requerir modificar los existentes.
 *
 * EJEMPLO EN BACKEND:
 *  - Un sistema de mensajería interna que distribuye eventos a módulos suscritos.
 *
 * EJEMPLO EN PIZZERÍA ONLINE:
 *  - El “Mediador de pedidos” que comunica Cocina → Caja → Repartidor.
 *
 * USO EN CLASE CLIENTE:
 *  Mediator m = new ConcreteMediator();
 *  m.register(new ConcreteComponent("Cocina", m));
 *  m.register(new ConcreteComponent("Caja", m));
 *  m.register(new ConcreteComponent("Delivery", m));
 *  cocina.send("Pedido #101 listo");
 *
 * SALIDA ESPERADA:
 *  [Cocina] envió mensaje: Pedido #101 listo
 *  [Caja] recibió: Pedido #101 listo
 *  [Delivery] recibió: Pedido #101 listo
 */
public class ConcreteMediator implements Mediator {

    private final List<Component> components = new ArrayList<>();

    @Override
    public void register(Component component) {
        components.add(component);
    }

    @Override
    public void send(String message, Component sender) {
        for (Component c : components) {
            if (c != sender) {
                c.receive(message);
            }
        }
    }
}
