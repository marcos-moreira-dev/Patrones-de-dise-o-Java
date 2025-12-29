

/**
 * INTERFAZ: Mapper
 *
 * ROL EN EL PATRÓN:
 *  - Otro "producto abstracto" de la familia de persistencia.
 *  - Define la interfaz para convertir datos entre formatos o modelos.
 *
 * PROPÓSITO:
 *  Permitir que cada fábrica proporcione su propia versión de Mapper coherente con el repositorio.
 *
 * EJEMPLO EN BACKEND:
 *  - SQLMapper: transforma datos entre tablas y objetos.
 *  - NoSQLMapper: transforma entre documentos JSON y objetos.
 *
 * EJEMPLO EN PIZZERÍA ONLINE:
 *  - Un Mapper que convierte los datos de un pedido entre la capa API y la base de datos.
 *
 * USO EN CLASE CLIENTE:
 *  Mapper mapper = factory.createMapper();
 *  mapper.mapData();
 */
public interface Mapper {
    void mapData();
}
