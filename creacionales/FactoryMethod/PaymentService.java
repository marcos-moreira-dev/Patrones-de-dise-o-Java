
/**
 * CLASE CONCRETA: PaymentService
 *
 * ROL EN EL PATRÓN:
 *  Implementa la interfaz `Service` para representar un producto concreto,
 *  diferente de `UserService`.
 *
 * PROPÓSITO:
 *  Definir la lógica de procesamiento de pagos dentro del sistema.
 *
 * CRITERIOS DE USO:
 *  - Útil cuando el sistema necesita operaciones específicas de pago,
 *    pero desea mantener el acoplamiento bajo gracias a la interfaz `Service`.
 *
 * EJEMPLO EN BACKEND:
 *  `PaymentService` podría encargarse de validar transacciones o comunicarse
 *  con pasarelas de pago externas.
 *
 * EJEMPLO EN PIZZERÍA ONLINE:
 *  Un servicio que valida el pago del pedido antes de enviarlo al horno o al delivery.
 */
public class PaymentService implements Service {
    @Override
    public void execute() {
        System.out.println("Ejecutando servicio de pago...");
    }
}
