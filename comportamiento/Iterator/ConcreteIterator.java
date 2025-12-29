

/**
 * CLASE CONCRETA: ConcreteIterator
 *
 * ROL EN EL PATRÓN:
 *  - Implementa la interfaz Iterator.
 *  - Controla la posición actual en la colección.
 *
 * PROPÓSITO:
 *  Permitir recorrer una colección secuencialmente sin exponer su estructura interna.
 *
 * CRITERIOS DE USO:
 *  ✅ Cuando se quiere ocultar cómo se almacenan los elementos.
 *  ✅ Cuando diferentes recorridos (por índice, por tipo, por prioridad) son necesarios.
 *
 * EJEMPLO EN BACKEND:
 *  - Iterar sobre resultados de consultas o logs.
 *
 * EJEMPLO EN PIZZERÍA ONLINE:
 *  - Iterar sobre pedidos del día para generar reportes.
 *
 * USO EN CLASE CLIENTE:
 *  Iterator it = pedidos.createIterator();
 *  while (it.hasNext()) System.out.println(it.next());
 *
 * SALIDA ESPERADA:
 *  Pedido #1
 *  Pedido #2
 *  Pedido #3
 */
public class ConcreteIterator implements Iterator {

    private final ConcreteAggregate aggregate;
    private int index = 0;

    public ConcreteIterator(ConcreteAggregate aggregate) {
        this.aggregate = aggregate;
    }

    @Override
    public boolean hasNext() {
        return index < aggregate.getItems().size();
    }

    @Override
    public Object next() {
        return aggregate.getItems().get(index++);
    }
}
