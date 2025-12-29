
/**
 * CLASE CONCRETA: NoSQLMapper
 *
 * ROL EN EL PATRÓN:
 *  - Implementa el producto concreto de tipo `Mapper` para NoSQL.
 *
 * PROPÓSITO:
 *  Definir cómo se transforman los objetos Java en documentos JSON.
 *
 * EJEMPLO EN BACKEND:
 *  - Convierte entidades Java a documentos BSON/JSON y viceversa.
 *
 * EJEMPLO EN PIZZERÍA ONLINE:
 *  - Convierte pedidos en documentos JSON con campos dinámicos.
 *
 * USO EN CLASE CLIENTE:
 *  Mapper mapper = new NoSQLMapper();
 *  mapper.mapData();
 */
public class NoSQLMapper implements Mapper {
    @Override
    public void mapData() {
        System.out.println("Mapeando datos en formato NoSQL...");
    }
}
