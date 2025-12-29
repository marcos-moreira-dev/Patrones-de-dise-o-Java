package creacionales.AbstractFactory;

/**
 * CLASE CONCRETA: SQLMapper
 *
 * ROL EN EL PATRÓN:
 *  - Implementa el producto concreto de tipo `Mapper` para SQL.
 *
 * PROPÓSITO:
 *  Definir cómo se mapean los datos entre objetos Java y tablas SQL.
 *
 * EJEMPLO EN BACKEND:
 *  - Convierte entidades Java a filas SQL y viceversa.
 *
 * EJEMPLO EN PIZZERÍA ONLINE:
 *  - Convierte el pedido en una fila SQL con campos: id, cliente, pizza, estado.
 *
 * USO EN CLASE CLIENTE:
 *  Mapper mapper = new SQLMapper();
 *  mapper.mapData();
 */
public class SQLMapper implements Mapper {
    @Override
    public void mapData() {
        System.out.println("Mapeando datos en formato SQL...");
    }
}
