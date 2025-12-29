
/**
 * INTERFAZ: Service
 *
 * ROL EN EL PATRÓN:
 *  Define la interfaz común que todas las clases de servicio deben implementar.
 *  Es el "producto" abstracto del patrón Factory Method.
 *
 * PROPÓSITO:
 *  Permitir que las fábricas creen diferentes servicios concretos
 *  (como UserService o PaymentService) que comparten el mismo contrato.
 *
 * CRITERIOS DE USO:
 *  - Define operaciones genéricas que los productos deben cumplir.
 *  - El cliente trabajará con esta interfaz, sin conocer las clases concretas.
 *
 * EJEMPLO EN BACKEND:
 *  Un controlador puede depender de `Service` en lugar de `UserService` o `PaymentService`.
 *  Así el código es extensible y desacoplado.
 *
 * EJEMPLO EN PIZZERÍA ONLINE:
 *  Podría representar un "Servicio de preparación" genérico, donde cada tipo de
 *  pizza implementa su propia versión del método `execute()`.
 */
public interface Service {
    void execute();
}
