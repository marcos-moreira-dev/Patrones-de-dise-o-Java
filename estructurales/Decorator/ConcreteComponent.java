
/**
 * CLASE CONCRETA: ConcreteComponent
 *
 * ROL EN EL PATRÓN:
 *  - Implementa la interfaz Component.
 *  - Es el objeto base al que se agregan responsabilidades adicionales.
 *
 * PROPÓSITO:
 *  Representar el componente "núcleo" que puede decorarse.
 *
 * EJEMPLO EN BACKEND:
 *  - Servicio original sin logging ni cache (por ejemplo, ServicioUsuario).
 *
 * EJEMPLO EN PIZZERÍA ONLINE:
 *  - Pizza base sin ingredientes adicionales.
 *
 * USO EN CLASE CLIENTE:
 *  Component pizza = new ConcreteComponent();
 *  pizza.operation();
 *
 * SALIDA ESPERADA:
 *  Preparando pizza base.
 */
public class ConcreteComponent implements Component {
    @Override
    public void operation() {
        System.out.println("Operación base del componente (ej. pizza simple).");
    }
}