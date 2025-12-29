package estructurales;

import comportamiento.Iterator.ConcreteAggregate;
import comportamiento.Iterator.Iterator;
import comportamiento.Observer.ConcreteObserver;
import comportamiento.Observer.ConcreteSubject;
import comportamiento.Observer.Observer;
import estructurales.Adapter.*;
import estructurales.Bridge.*;
import estructurales.Composite.*;
//import estructurales.Decorator.*;
import estructurales.Facade.*;
import estructurales.Flyweight.*;
import estructurales.Proxy.*;

public class ProbarEstructurales {

    public ProbarEstructurales() {
        System.out.println("=== ProbarEstructurales ===");
    }

    // ----------- ADAPTER -----------
    public void probarAdapter() {
        System.out.println("\n[Adapter]");
        Adaptee proveedor = new Adaptee();
        Target sistemaPedidos = new Adapter(proveedor);
        sistemaPedidos.request();
    }

    // ----------- BRIDGE -----------
    public void probarBridge() {
        System.out.println("\n[Bridge]");
        Implementor cocinaTradicional = new ConcreteImplementorA();
        Abstraction sistema1 = new RefinedAbstraction(cocinaTradicional);
        sistema1.realizarOperacion();

        Implementor cocinaRobotica = new ConcreteImplementorB();
        Abstraction sistema2 = new RefinedAbstraction(cocinaRobotica);
        sistema2.realizarOperacion();
    }

    // ----------- COMPOSITE -----------
    public void probarComposite() {
        System.out.println("\n[Composite]");
        Composite menu = new Composite("Menú Principal");
        Leaf pizza = new Leaf("Pizza");
        Leaf pasta = new Leaf("Pasta");
        menu.add(pizza);
        menu.add(pasta);
        menu.showDetails();
    }

    // ----------- DECORATOR -----------
    public void probarDecorator() {
    System.out.println("\n[Decorator]");
    estructurales.Decorator.Component pizza = new estructurales.Decorator.ConcreteComponent();
    estructurales.Decorator.Component pizzaConExtra = new estructurales.Decorator.ConcreteDecorator(pizza);
    pizzaConExtra.operation();
}

    // ----------- FACADE -----------
    public void probarFacade() {
        System.out.println("\n[Facade]");
        Facade fachada = new Facade();
        fachada.realizarPedido("Pizza Napolitana");
    }

    // ----------- FLYWEIGHT -----------
    public void probarFlyweight() {
        System.out.println("\n[Flyweight]");
        FlyweightFactory factory = new FlyweightFactory();

        Flyweight pizzaMargherita1 = factory.getFlyweight("Pizza Margherita");
        Flyweight pizzaMargherita2 = factory.getFlyweight("Pizza Margherita");
        Flyweight pizzaPepperoni = factory.getFlyweight("Pizza Pepperoni");

        pizzaMargherita1.servir("Mesa 1");
        pizzaMargherita2.servir("Mesa 2");
        pizzaPepperoni.servir("Mesa 3");

        System.out.println("¿Son iguales las instancias de Margherita? " + (pizzaMargherita1 == pizzaMargherita2));
    }

    // ----------- PROXY -----------
    public void probarProxy() {
        System.out.println("\n[Proxy]");
        Service autorizado = new ProxyService("cliente123", "1234");
        autorizado.request();

        Service denegado = new ProxyService("usuarioX", "0000");
        denegado.request();
    }

     // ----------- ITERATOR -----------
    public void probarIterator() {
        System.out.println("\n[Iterator]");
        ConcreteAggregate listaPedidos = new ConcreteAggregate();
        listaPedidos.addItem("Pedido 1");
        listaPedidos.addItem("Pedido 2");
        listaPedidos.addItem("Pedido 3");

        Iterator iter = listaPedidos.createIterator();
        while (iter.hasNext()) {
            System.out.println("Iterando: " + iter.next());
        }
        // 🔹 Salida esperada:
        // Iterando: Pedido 1
        // Iterando: Pedido 2
        // Iterando: Pedido 3
    }

    // ----------- OBSERVER -----------
    public void probarObserver() {
        System.out.println("\n[Observer]");
        ConcreteSubject subject = new ConcreteSubject();
        Observer obs1 = new ConcreteObserver("Cocina");
        Observer obs2 = new ConcreteObserver("Caja");

        subject.attach(obs1);
        subject.attach(obs2);

        subject.setState("Nuevo pedido recibido");
        subject.setState("Pedido listo para entrega");
        // 🔹 Salida esperada:
        // Notificación a Cocina: Nuevo pedido recibido
        // Notificación a Caja: Nuevo pedido recibido
        // Notificación a Cocina: Pedido listo para entrega
        // Notificación a Caja: Pedido listo para entrega
    }
}
