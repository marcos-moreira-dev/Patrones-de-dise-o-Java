package comportamiento.Mediator;

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

    /**
     * Vincula este participante al coordinador de mensajes.
     *
     * @param name nombre visible del participante
     * @param mediator coordinador al que se delegaran los envios
     */
    public Component(String name, Mediator mediator) {
        this.name = name;
        this.mediator = mediator;
    }

    /**
     * Recibe un mensaje remitido por el mediador.
     *
     * @param message contenido entregado al participante
     */
    public abstract void receive(String message);

    /**
     * Entrega el mensaje al mediador sin conocer a los demas receptores.
     *
     * @param message informacion que se desea comunicar
     */
    public void send(String message) {
        System.out.println("[" + name + "] envió mensaje: " + message);
        mediator.send(message, this);
    }

    public String getName() {
        return name;
    }
}
