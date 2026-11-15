package creacionales.AbstractFactory;
/**
 * CLASE CONCRETA: SQLRepository
 *
 * ROL EN EL PATRÓN:
 *  - Implementa el producto concreto de tipo `Repository` para SQL.
 *
 * PROPÓSITO:
 *  Representar la lógica específica de guardado para sistemas SQL.
 *
 * EJEMPLO EN BACKEND:
 *  - Guarda registros en una base de datos relacional.
 *
 * EJEMPLO EN PIZZERÍA ONLINE:
 *  - Guarda los pedidos en la tabla "orders".
 *
 * USO EN CLASE CLIENTE:
 *  Repository repo = new SQLRepository();
 *  repo.save();
 */
public class SQLRepository implements Repository {
    @Override
    public void save() {
        System.out.println("Guardando datos en base de datos SQL...");
    }
}
