
/**
 * INTERFAZ: Aggregate
 *
 * ROL EN EL PATRÓN:
 *  - Define la interfaz para crear un objeto Iterator asociado.
 *
 * PROPÓSITO:
 *  Abstraer la creación de iteradores para diferentes colecciones.
 *
 * EJEMPLO EN BACKEND:
 *  - Colección de pedidos, usuarios, facturas, etc.
 *
 * EJEMPLO EN PIZZERÍA ONLINE:
 *  - Lista de pedidos activos o de clientes frecuentes.
 *
 * USO EN CLASE CLIENTE:
 *  Aggregate pedidos = new ConcreteAggregate();
 *  Iterator it = pedidos.createIterator();
 */
public interface Aggregate {
    Iterator createIterator();
}
