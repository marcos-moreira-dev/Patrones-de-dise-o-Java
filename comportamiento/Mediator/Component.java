

/**
 * CLASE ABSTRACTA: Component
 *
 * ROL EN EL PATRÓN:
 *  - Representa los objetos (colegas) que se comunican entre sí a través del Mediador.
 *  - Mantiene una referencia al Mediador para enviar mensajes.
 *
 * PROPÓSITO:
 *  Permitir que los componentes interactúen sin conocerse directamente.
 *
 * EJEMPLO EN BACKEND:
 *  - Módulos independientes (pago, inventario, facturación) comunicándose por eventos.
 *
 * EJEMPLO EN PIZZERÍA ONLINE:
 *  - “Cocina”, “Delivery” y “Caja” se comunican a través del mediador sin referencias directas.
 *
 * USO EN CLASE CLIENTE:
 *  Component c = new ConcreteComponent("Cocina", m);
 *  c.send("Pedido #101 listo");
 *
 * SALIDA ESPERADA:
 *  [Cocina] envió mensaje: Pedido #101 listo
 */
public abstract class Component {
    protected Mediator mediator;
    protected String name;

    public Component(String name, Mediator mediator) {
        this.name = name;
        this.mediator = mediator;
    }

    public abstract void receive(String message);

    public void send(String message) {
        System.out.println("[" + name + "] envió mensaje: " + message);
        mediator.send(message, this);
    }

    public String getName() {
        return name;
    }
}
