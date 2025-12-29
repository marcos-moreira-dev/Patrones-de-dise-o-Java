
/**
 * CLASE CONCRETA: UserServiceFactory
 *
 * ROL EN EL PATRÓN:
 *  Implementa el método fábrica para crear instancias de `UserService`.
 *  Es una "fábrica concreta".
 *
 * PROPÓSITO:
 *  Encapsular la creación de servicios relacionados con usuarios.
 *
 * CRITERIOS DE USO:
 *  - Cuando se desea producir un tipo específico de servicio (`UserService`)
 *    sin que el cliente conozca los detalles de su implementación.
 *
 * EJEMPLO EN BACKEND:
 *  Un router podría usar esta fábrica para crear un servicio de autenticación.
 *
 * EJEMPLO EN PIZZERÍA ONLINE:
 *  Un sistema que crea el servicio de "gestión de clientes" cada vez que
 *  un nuevo usuario se registra o inicia sesión.
 */
public class UserServiceFactory extends ServiceFactory {
    @Override
    public Service createService() {
        return new UserService();
    }
}
