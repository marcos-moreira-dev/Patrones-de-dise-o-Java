package estructurales;

// --- Adapter
import estructurales.Adapter.Adaptee;
import estructurales.Adapter.Adapter;
import estructurales.Adapter.Target;

// --- Bridge
import estructurales.Bridge.Abstraction;
import estructurales.Bridge.ConcreteImplementorA;
import estructurales.Bridge.ConcreteImplementorB;
import estructurales.Bridge.Implementor;
import estructurales.Bridge.RefinedAbstraction;

// --- Composite
import estructurales.Composite.Composite;
import estructurales.Composite.Leaf;

// --- Decorator (ojo con Component: import explícito para este paquete)
import estructurales.Decorator.Component;
import estructurales.Decorator.ConcreteComponent;
import estructurales.Decorator.ConcreteDecorator;

// --- Facade
import estructurales.Facade.Facade;

// --- Flyweight
import estructurales.Flyweight.Flyweight;
import estructurales.Flyweight.FlyweightFactory;

// --- Proxy
import estructurales.Proxy.ProxyService;
import estructurales.Proxy.Service;

// --- (Demo cruzada) Iterator / Observer desde comportamiento
import comportamiento.Iterator.ConcreteAggregate;
import comportamiento.Iterator.Iterator;
import comportamiento.Observer.ConcreteObserver;
import comportamiento.Observer.ConcreteSubject;
import comportamiento.Observer.Observer;

public class ProbarEstructurales {

    public ProbarEstructurales() {
        System.out.println("=== ProbarEstructurales ===");
    }

    public void ejecutarTodo() {
        probarAdapter();
        probarBridge();
        probarComposite();
        probarDecorator();
        probarFacade();
        probarFlyweight();
        probarProxy();

        // demos cruzadas
        probarIterator();
        probarObserver();
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
        Component pizza = new ConcreteComponent();
        Component pizzaConExtra = new ConcreteDecorator(pizza);
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
        autorizado.request(); //autorizado.execute(); // <- si tu interfaz se llama request(), cambia a autorizado.request()

        Service denegado = new ProxyService("usuarioX", "0000");
        denegado.request(); //denegado.execute();   // <- si tu interfaz se llama request(), cambia a denegado.request()
    }

    // ----------- ITERATOR (demo cruzada) -----------
    public void probarIterator() {
        System.out.println("\n[Iterator] (demo cruzada)");
        ConcreteAggregate listaPedidos = new ConcreteAggregate();
        listaPedidos.addItem("Pedido 1");
        listaPedidos.addItem("Pedido 2");
        listaPedidos.addItem("Pedido 3");

        Iterator iter = listaPedidos.createIterator();
        while (iter.hasNext()) {
            System.out.println("Iterando: " + iter.next());
        }
    }

    // ----------- OBSERVER (demo cruzada) -----------
    public void probarObserver() {
        System.out.println("\n[Observer] (demo cruzada)");
        ConcreteSubject subject = new ConcreteSubject();
        Observer obs1 = new ConcreteObserver("Cocina");
        Observer obs2 = new ConcreteObserver("Caja");

        subject.attach(obs1);
        subject.attach(obs2);

        subject.setState("Nuevo pedido recibido");
        subject.setState("Pedido listo para entrega");
    }
}
