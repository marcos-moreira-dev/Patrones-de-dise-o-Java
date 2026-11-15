package creacionales.FactoryMethod;
/**
 * CLASE CONCRETA: UserService
 *
 * ROL EN EL PATRÓN:
 *  Implementa la interfaz `Service` y define un producto específico.
 *  Representa una de las variantes concretas que la fábrica puede producir.
 *
 * PROPÓSITO:
 *  Definir la lógica asociada a la gestión de usuarios.
 *
 * CRITERIOS DE USO:
 *  - Se usa cuando el cliente requiere operaciones de usuario,
 *    pero el código cliente sigue dependiendo solo de `Service`.
 *
 * EJEMPLO EN BACKEND:
 *  `UserService` podría encargarse de registrar o autenticar usuarios.
 *
 * EJEMPLO EN PIZZERÍA ONLINE:
 *  Un servicio responsable de registrar nuevos clientes o manejar su historial de pedidos.
 */
public class UserService implements Service {
    @Override
    public void execute() {
        System.out.println("Ejecutando servicio de usuario...");
    }
}
