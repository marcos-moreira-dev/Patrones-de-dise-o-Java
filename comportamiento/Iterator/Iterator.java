

/**
 * PATRÓN: Iterator
 *
 * INTERFAZ: Iterator
 *
 * ROL EN EL PATRÓN:
 *  - Define las operaciones necesarias para recorrer una colección
 *    sin exponer su representación interna.
 *
 * PROPÓSITO:
 *  Permitir recorrer una colección de manera uniforme, independientemente
 *  del tipo de estructura (lista, conjunto, árbol, etc.).
 *
 * CRITERIOS DE USO:
 *  ✅ Cuando se necesita recorrer estructuras sin conocer su implementación.
 *  ✅ Cuando se requiere más de un recorrido simultáneo (diferentes iteradores).
 *
 * VENTAJAS:
 *  + Desacopla la lógica de recorrido del tipo de colección.
 *  + Permite agregar nuevos tipos de iteradores sin modificar las colecciones.
 *
 * DESVENTAJAS:
 *  - Aumenta el número de clases.
 *
 * EJEMPLO EN BACKEND:
 *  - Recorrer registros de una base de datos o una lista de pedidos.
 *
 * EJEMPLO EN PIZZERÍA ONLINE:
 *  - Recorrer los pedidos pendientes para procesarlos o imprimirlos.
 *
 * USO EN CLASE CLIENTE:
 *  ConcreteAggregate pedidos = new ConcreteAggregate();
 *  Iterator it = pedidos.createIterator();
 *  while (it.hasNext()) System.out.println(it.next());
 *
 * SALIDA ESPERADA:
 *  Pedido #1
 *  Pedido #2
 *  Pedido #3
 */

 /*
 * NOTA CONCEPTUAL (única vez):
 *
 * Existen otros patrones que se relacionan con estructuras de datos
 * más allá de las listas lineales que recorre Iterator:
 *
 *  - Árboles (estructuras jerárquicas):
 *      → Patrón COMPOSITE (trata nodos y hojas como un mismo objeto)
 *      → Patrón VISITOR (permite recorrer y operar sobre nodos)
 *
 *  - Diccionarios o mapas (clave-valor):
 *      → Patrón FLYWEIGHT (optimiza almacenamiento de datos repetidos)
 *      → Patrón PROXY (controla acceso a datos grandes o remotos)
 *
 *  - Grafos (estructuras conectadas):
 *      → Patrón STRATEGY (permite intercambiar el algoritmo de recorrido)
 *      → Patrón MEDIATOR (coordina comunicación entre nodos)
 *
 *  - Estructuras versionadas o reversibles:
 *      → Patrón MEMENTO (guarda snapshots o versiones de estados)
 *
 * En general, Iterator abstrae el recorrido secuencial,
 * mientras que los otros patrones abstraen la relación, jerarquía
 * o comportamiento dentro de estructuras más complejas.
 */

public interface Iterator {
    boolean hasNext();
    Object next();
}
