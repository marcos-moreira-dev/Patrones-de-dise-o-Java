package creacionales.FactoryMethod;
/**
 * CLASE CONCRETA: PaymentServiceFactory
 *
 * ROL EN EL PATRÓN:
 *  Implementa el método fábrica para crear instancias de `PaymentService`.
 *
 * PROPÓSITO:
 *  Encapsular la creación de servicios relacionados con pagos,
 *  siguiendo el principio de sustitución por la interfaz `ServiceFactory`.
 *
 * CRITERIOS DE USO:
 *  - Cuando se requiera una fábrica capaz de producir servicios de pago
 *    sin acoplar al código cliente con `PaymentService` directamente.
 *
 * EJEMPLO EN BACKEND:
 *  Controlador que crea un servicio de validación de pago
 *  sin importar si el método de pago cambia (tarjeta, PayPal, etc.).
 *
 * EJEMPLO EN PIZZERÍA ONLINE:
 *  Una fábrica encargada de generar el servicio que valida el pago del pedido
 *  antes de enviarlo al sistema de entrega o cocina.
 */
public class PaymentServiceFactory extends ServiceFactory {
    @Override
    public Service createService() {
        return new PaymentService();
    }
}
