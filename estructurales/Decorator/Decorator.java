package estructurales.Decorator;

/**
 * CLASE ABSTRACTA: Decorator
 *
 * ROL EN EL PATRÓN:
 *  - Implementa la interfaz Component.
 *  - Contiene una referencia a un objeto Component (puede ser el original u otro decorador).
 *
 * PROPÓSITO:
 *  Definir la estructura común para todos los decoradores.
 *
 * EJEMPLO EN BACKEND:
 *  - Clase base de middlewares (log, seguridad, auditoría).
 *
 * EJEMPLO EN PIZZERÍA ONLINE:
 *  - Base común para todos los agregados de ingredientes extra.
 *
 * USO EN CLASE CLIENTE:
 *  Component pizza = new ConcreteDecorator(new ConcreteComponent());
 *  pizza.operation();
 *
 * SALIDA ESPERADA:
 *  Preparando pizza base.
 *  + Añadiendo extra: Queso extra.
 */
public abstract class Decorator implements Component {
    protected Component component;

    public Decorator(Component component) {
        this.component = component;
    }

    @Override
    public void operation() {
        component.operation();
    }
}