
/**
 * PATRÓN: Decorator (Decorador)
 *
 * INTERFAZ: Component
 *
 * ROL EN EL PATRÓN:
 *  - Define la interfaz común para los objetos que pueden tener
 *    responsabilidades añadidas dinámicamente.
 *
 * PROPÓSITO:
 *  Permitir agregar comportamiento adicional a un objeto sin modificar su clase.
 *
 * CRITERIOS DE USO:
 *  ✅ Cuando quieres extender funcionalidad sin heredar.
 *  ✅ Cuando distintas combinaciones de comportamiento deben coexistir.
 *  ✅ Cuando las decoraciones deben ser dinámicas en tiempo de ejecución.
 *
 * VENTAJAS:
 *  + Favorece la composición sobre la herencia.
 *  + Permite agregar o quitar comportamientos en tiempo de ejecución.
 *
 * DESVENTAJAS:
 *  - Puede generar demasiadas clases pequeñas y difíciles de rastrear.
 *
 * EJEMPLO EN BACKEND:
 *  - Middleware que añade logging, compresión o cacheo sin tocar el servicio base.
 *
 * EJEMPLO EN PIZZERÍA ONLINE:
 *  - Una pizza base que puede decorarse con extras: queso, pepperoni, etc.
 *
 * USO EN CLASE CLIENTE:
 *  Component pizza = new ConcreteComponent();
 *  pizza = new ConcreteDecorator(pizza);
 *  pizza.operation();
 *
 * SALIDA ESPERADA:
 *  Preparando pizza base.
 *  + Añadiendo extra: Queso extra.
 */
public interface Component {
    void operation();
}
