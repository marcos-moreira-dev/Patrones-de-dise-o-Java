

import java.util.ArrayList;
import java.util.List;

/**
 * CLASE CONCRETA: ConcreteAggregate
 *
 * ROL EN EL PATRÓN:
 *  - Implementa la interfaz Aggregate.
 *  - Contiene la colección real que será recorrida.
 *
 * PROPÓSITO:
 *  Representar una colección específica y permitir crear su iterador.
 *
 * EJEMPLO EN BACKEND:
 *  - Lista de usuarios en memoria, pedidos, productos, etc.
 *
 * EJEMPLO EN PIZZERÍA ONLINE:
 *  - Lista de pedidos en cola para entrega.
 *
 * USO EN CLASE CLIENTE:
 *  ConcreteAggregate pedidos = new ConcreteAggregate();
 *  pedidos.addItem("Pedido #1");
 *  pedidos.addItem("Pedido #2");
 *  Iterator it = pedidos.createIterator();
 *
 * SALIDA ESPERADA:
 *  Pedido #1
 *  Pedido #2
 */
public class ConcreteAggregate implements Aggregate {

    private final List<String> items = new ArrayList<>();

    public void addItem(String item) {
        items.add(item);
    }

    public List<String> getItems() {
        return items;
    }

    @Override
    public Iterator createIterator() {
        return new ConcreteIterator(this);
    }
}
