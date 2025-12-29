package estructurales.Decorator;

/**
 * CLASE CONCRETA: ConcreteDecorator
 *
 * ROL EN EL PATRÓN:
 *  - Extiende la funcionalidad del objeto Component sin modificar su clase.
 *
 * PROPÓSITO:
 *  Agregar comportamiento adicional antes o después de invocar al componente original.
 *
 * CRITERIOS DE USO:
 *  ✅ Cuando diferentes funcionalidades pueden aplicarse de forma combinable.
 *  ✅ Cuando se quiere modificar comportamiento sin heredar.
 *
 * EJEMPLO EN BACKEND:
 *  - Decorador que añade logging a un servicio.
 *
 * EJEMPLO EN PIZZERÍA ONLINE:
 *  - Decorador que añade ingredientes extra a una pizza base.
 *
 * USO EN CLASE CLIENTE:
 *  Component pizza = new ConcreteDecorator(new ConcreteComponent());
 *  pizza.operation();
 *
 * SALIDA ESPERADA:
 *  Preparando pizza base.
 *  + Añadiendo extra: Queso extra.
 */
public class ConcreteDecorator extends Decorator {
    public ConcreteDecorator(Component component) {
        super(component);
    }

    @Override
    public void operation() {
        super.operation();
        agregarExtra();
    }

    private void agregarExtra() {
        System.out.println("➕ Se añadió extra de queso (decoración adicional).");
    }
}