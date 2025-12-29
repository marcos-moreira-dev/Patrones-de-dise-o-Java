package creacionales.AbstractFactory;

/**
 * CLASE CONCRETA: NoSQLRepository
 *
 * ROL EN EL PATRÓN:
 *  - Implementa el producto concreto de tipo `Repository` para NoSQL.
 *
 * PROPÓSITO:
 *  Representar la lógica específica de persistencia en bases de datos NoSQL.
 *
 * EJEMPLO EN BACKEND:
 *  - Guarda documentos JSON en una colección de MongoDB.
 *
 * EJEMPLO EN PIZZERÍA ONLINE:
 *  - Almacena pedidos como documentos JSON en una base NoSQL.
 *
 * USO EN CLASE CLIENTE:
 *  Repository repo = new NoSQLRepository();
 *  repo.save();
 */
public class NoSQLRepository implements Repository {
    @Override
    public void save() {
        System.out.println("Guardando datos en base de datos NoSQL...");
    }
}
