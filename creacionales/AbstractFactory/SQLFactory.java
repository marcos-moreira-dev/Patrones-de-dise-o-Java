package creacionales.AbstractFactory;

/**
 * CLASE CONCRETA: SQLFactory
 *
 * ROL EN EL PATRÓN:
 *  - Implementa la interfaz `PersistenceFactory`.
 *  - Crea objetos concretos que pertenecen a la familia SQL (SQLRepository y SQLMapper).
 *
 * PROPÓSITO:
 *  Proveer implementaciones concretas para sistemas SQL.
 *
 * CRITERIOS DE USO:
 *  - Cuando el sistema trabaja con una base de datos relacional (MySQL, PostgreSQL, etc.).
 *
 * EJEMPLO EN BACKEND:
 *  - Fábrica usada por el servicio de persistencia de datos SQL.
 *
 * EJEMPLO EN PIZZERÍA ONLINE:
 *  - La pizzería usa SQLFactory para registrar pedidos y clientes en una base relacional.
 *
 * USO EN CLASE CLIENTE:
 *  PersistenceFactory factory = new SQLFactory();
 *  Repository repo = factory.createRepository();
 *  Mapper mapper = factory.createMapper();
 *  repo.save();
 *  mapper.mapData();
 */
public class SQLFactory implements PersistenceFactory {

    @Override
    public Repository createRepository() {
        return new SQLRepository();
    }

    @Override
    public Mapper createMapper() {
        return new SQLMapper();
    }
}
