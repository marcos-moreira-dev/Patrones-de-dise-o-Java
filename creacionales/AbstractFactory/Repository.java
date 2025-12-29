
/**
 * INTERFAZ: Repository
 *
 * ROL EN EL PATRÓN:
 *  - Representa el "producto abstracto" dentro de la familia de persistencia.
 *  - Define las operaciones generales que todos los repositorios deben implementar.
 *
 * PROPÓSITO:
 *  Permitir que las fábricas produzcan repositorios sin acoplarse a implementaciones concretas.
 *
 * EJEMPLO EN BACKEND:
 *  - Un repositorio puede ser SQLRepository o NoSQLRepository.
 *
 * EJEMPLO EN PIZZERÍA ONLINE:
 *  - Un repositorio que almacena pedidos en base de datos relacional o documentos JSON.
 *
 * USO EN CLASE CLIENTE:
 *  Repository repo = factory.createRepository();
 *  repo.save();
 */
public interface Repository {
    void save();
}
