

/**
 * PATRÓN: Mediator
 *
 * INTERFAZ: Mediator
 *
 * ROL EN EL PATRÓN:
 *  - Define la interfaz para la comunicación entre componentes (colegas).
 *  - Evita que los componentes se comuniquen directamente entre sí.
 *
 * PROPÓSITO:
 *  Centralizar la comunicación entre varios objetos para reducir acoplamiento.
 *
 * CRITERIOS DE USO:
 *  ✅ Cuando muchos objetos deben interactuar, pero las dependencias directas generan complejidad.
 *  ✅ Cuando se busca desacoplar clases y mover la lógica de interacción a un objeto central.
 *
 * ⚠️ Evitarlo cuando:
 *  - Las relaciones son simples y no justifican un mediador adicional.
 *
 * VENTAJAS:
 *  + Desacopla los componentes.
 *  + Centraliza la lógica de coordinación.
 *
 * DESVENTAJAS:
 *  - El Mediador puede crecer demasiado (convertirse en “Dios” del sistema).
 *
 * EJEMPLO EN BACKEND:
 *  - Un sistema de microservicios donde un “EventBus” coordina eventos entre módulos.
 *
 * EJEMPLO EN PIZZERÍA ONLINE:
 *  - Un mediador que coordina la comunicación entre “Cocina”, “Delivery” y “Caja”.
 *
 * USO EN CLASE CLIENTE:
 *  Mediator m = new ConcreteMediator();
 *  Component c1 = new ConcreteComponent("Cocina", m);
 *  Component c2 = new ConcreteComponent("Caja", m);
 *  m.register(c1);
 *  m.register(c2);
 *  c1.send("Pedido #101 listo");
 *
 * SALIDA ESPERADA:
 *  [Cocina] envió mensaje: Pedido #101 listo
 *  [Caja] recibió: Pedido #101 listo
 */
public interface Mediator {
    void send(String message, Component sender);
    void register(Component component);
}
