package creacionales.AbstractFactory;
/**
 * CLASE CONCRETA: NoSQLFactory
 *
 * ROL EN EL PATRÓN:
 *  - Implementa la interfaz `PersistenceFactory`.
 *  - Produce objetos concretos de la familia NoSQL (NoSQLRepository y NoSQLMapper).
 *
 * PROPÓSITO:
 *  Proveer implementaciones concretas para sistemas NoSQL.
 *
 * CRITERIOS DE USO:
 *  - Cuando el sistema trabaja con bases de datos de documentos (MongoDB, Firebase, etc.).
 *
 * EJEMPLO EN BACKEND:
 *  - Fábrica usada por servicios que requieren persistencia flexible o distribuida.
 *
 * EJEMPLO EN PIZZERÍA ONLINE:
 *  - Usada por el sistema de pedidos en tiempo real, que almacena los pedidos en MongoDB.
 *
 * USO EN CLASE CLIENTE:
 *  PersistenceFactory factory = new NoSQLFactory();
 *  Repository repo = factory.createRepository();
 *  Mapper mapper = factory.createMapper();
 *  repo.save();
 *  mapper.mapData();
 */
public class NoSQLFactory implements PersistenceFactory {

    @Override
    public Repository createRepository() {
        return new NoSQLRepository();
    }

    @Override
    public Mapper createMapper() {
        return new NoSQLMapper();
    }
}
