package comportamiento;

// Chain of Responsibility
import comportamiento.ChainOfResponsibility.ConcreteHandlerA;
import comportamiento.ChainOfResponsibility.ConcreteHandlerB;
import comportamiento.ChainOfResponsibility.Handler;

// Command
import comportamiento.Command.Command;
import comportamiento.Command.ConcreteCommand;
import comportamiento.Command.Invoker;
import comportamiento.Command.Receiver;

// Iterator
import comportamiento.Iterator.ConcreteAggregate;
import comportamiento.Iterator.Iterator;

// Mediator
import comportamiento.Mediator.ConcreteComponent;
import comportamiento.Mediator.ConcreteMediator;

// Memento
import comportamiento.Memento.Caretaker;
import comportamiento.Memento.Originator;

// Observer
import comportamiento.Observer.ConcreteObserver;
import comportamiento.Observer.ConcreteSubject;

// Strategy
import comportamiento.Strategy.ConcreteStrategyA;
import comportamiento.Strategy.ConcreteStrategyB;

// State
import comportamiento.State.Context;

// Template Method
import comportamiento.TemplateMethod.AbstractClass;
import comportamiento.TemplateMethod.ConcreteClassA;
import comportamiento.TemplateMethod.ConcreteClassB;

// Visitor
import comportamiento.Visitor.ConcreteElementA;
import comportamiento.Visitor.ConcreteElementB;
import comportamiento.Visitor.ConcreteVisitor;
import comportamiento.Visitor.Element;
import comportamiento.Visitor.Visitor;

public class ProbarComportamiento {

    public ProbarComportamiento() {
        System.out.println("=== ProbarComportamiento ===");
    }

    // Ejecuta todos los ejemplos de comportamiento
    public void ejecutarTodo() {
        probarStrategy();
        probarState();
        probarChainOfResponsibility();
        probarCommand();
        probarIterator();
        probarObserver();
        probarMediator();
        probarMemento();
        probarTemplateMethod();
        probarVisitor();
    }

    // ----------- STRATEGY -----------
    public void probarStrategy() {
        System.out.println("\n[Strategy]");
        comportamiento.Strategy.Context ctx =
                new comportamiento.Strategy.Context(new ConcreteStrategyA());
        ctx.executeStrategy();

        ctx.setStrategy(new ConcreteStrategyB());
        ctx.executeStrategy();
    }

    // ----------- STATE -----------
    public void probarState() {
        System.out.println("\n[State]");
        Context ctx = new Context();
        ctx.request();
        ctx.request();
        ctx.request();
    }

    // ----------- CHAIN OF RESPONSIBILITY -----------
    public void probarChainOfResponsibility() {
        System.out.println("\n[Chain of Responsibility]");
        Handler h1 = new ConcreteHandlerA();
        Handler h2 = new ConcreteHandlerB();
        h1.setNext(h2);

        h1.handleRequest("pedido tipo A");
        h1.handleRequest("pedido tipo B");
    }

    // ----------- COMMAND -----------
    public void probarCommand() {
        System.out.println("\n[Command]");
        Receiver receptor = new Receiver();
        Command cmd = new ConcreteCommand(receptor, "Pizza Napolitana");
        Invoker inv = new Invoker();
        inv.setCommand(cmd);
        inv.executeCommand();
    }

    // ----------- ITERATOR -----------
    public void probarIterator() {
        System.out.println("\n[Iterator]");
        ConcreteAggregate pedidos = new ConcreteAggregate();
        pedidos.addItem("Pedido #1");
        pedidos.addItem("Pedido #2");
        pedidos.addItem("Pedido #3");

        Iterator it = pedidos.createIterator();
        while (it.hasNext()) {
            System.out.println(it.next());
        }
    }

    // ----------- OBSERVER -----------
    public void probarObserver() {
        System.out.println("\n[Observer]");
        ConcreteSubject pedidos = new ConcreteSubject();

        pedidos.attach(new ConcreteObserver("Cocina"));
        pedidos.attach(new ConcreteObserver("Delivery"));
        pedidos.attach(new ConcreteObserver("Caja"));

        pedidos.setState("Pedido #101 LISTO");
        pedidos.setState("Pedido #101 ENVIADO");
    }

    // ----------- MEDIATOR -----------
    public void probarMediator() {
        System.out.println("\n[Mediator]");
        ConcreteMediator mediator = new ConcreteMediator();

        ConcreteComponent cocina = new ConcreteComponent("Cocina", mediator);
        ConcreteComponent caja = new ConcreteComponent("Caja", mediator);

        mediator.register(cocina);
        mediator.register(caja);

        cocina.send("Pedido listo para entrega");
        caja.send("Confirmación recibida");
    }

    // ----------- MEMENTO -----------
    public void probarMemento() {
        System.out.println("\n[Memento]");
        Originator originator = new Originator();
        Caretaker history = new Caretaker();

        originator.setState("Versión 1 - Pedido creado");
        history.save(originator.createMemento());

        originator.setState("Versión 2 - Pedido modificado");
        history.save(originator.createMemento());

        originator.restore(history.undo());
        originator.restore(history.undo());
    }

    // ----------- TEMPLATE METHOD -----------
    public void probarTemplateMethod() {
        System.out.println("\n[Template Method]");
        AbstractClass procesoA = new ConcreteClassA();
        procesoA.templateMethod();

        AbstractClass procesoB = new ConcreteClassB();
        procesoB.templateMethod();
    }

    // ----------- VISITOR -----------
    public void probarVisitor() {
        System.out.println("\n[Visitor]");
        Element a = new ConcreteElementA();
        Element b = new ConcreteElementB();
        Visitor v = new ConcreteVisitor();

        a.accept(v);
        b.accept(v);
    }
}
