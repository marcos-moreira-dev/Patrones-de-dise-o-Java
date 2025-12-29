package creacionales.AbstractFactory;

/**
 * PATRÓN: Abstract Factory
 *
 * CLASE ABSTRACTA / INTERFAZ: PersistenceFactory
 *
 * ROL EN EL PATRÓN:
 *  - Define la interfaz para crear familias de objetos relacionados
 *    (Repository y Mapper) sin especificar sus clases concretas.
 *  - Es la "fábrica abstracta" del patrón.
 *
 * PROPÓSITO:
 *  Proveer una interfaz para crear conjuntos de objetos que pertenecen a una misma familia
 *  (por ejemplo, SQL o NoSQL) sin acoplar el código cliente a clases específicas.
 *
 * MECANISMO:
 *  - Las subclases concretas (SQLFactory y NoSQLFactory) implementan los métodos de creación.
 *  - El cliente utiliza solo esta interfaz para obtener los objetos necesarios.
 *
 * CRITERIOS DE USO (CUÁNDO APLICARLO):
 *  ✅ Cuando un sistema necesita trabajar con múltiples familias de productos relacionados.
 *  ✅ Cuando se quiere mantener la coherencia entre los objetos de una misma familia.
 *  ✅ Cuando el código cliente debe permanecer independiente de las clases concretas.
 *
 * ⚠️ Evitarlo cuando:
 *  - Solo hay una familia de productos y no se prevé extensión.
 *  - La complejidad de las jerarquías no se justifica.
 *
 * VENTAJAS:
 *  + Desacopla la creación del uso.
 *  + Facilita la incorporación de nuevas familias sin modificar el cliente.
 *  + Promueve la coherencia entre objetos compatibles.
 *
 * DESVENTAJAS:
 *  - Añade complejidad estructural (más clases).
 *
 * EJEMPLO EN BACKEND:
 *  - Fábricas que producen repositorios y mapeadores para distintos motores de persistencia (SQL/NoSQL).
 *
 * EJEMPLO EN PIZZERÍA ONLINE:
 *  - Fábricas que generan distintos manejadores de pedidos según el tipo de base de datos:
 *    `SQLFactory` (para pedidos tradicionales) o `NoSQLFactory` (para pedidos en tiempo real).
 *
 * USO EN CLASE CLIENTE:
 *  PersistenceFactory factory = new SQLFactory();
 *  Repository repo = factory.createRepository();
 *  Mapper mapper = factory.createMapper();
 *  repo.save();
 *  mapper.mapData();
 */
public interface PersistenceFactory {
    Repository createRepository();
    Mapper createMapper();
}
