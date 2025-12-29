

import comportamiento.ChainOfResponsibility.*;
import comportamiento.Command.*;
import comportamiento.Iterator.*;
import comportamiento.Mediator.*;
import comportamiento.Memento.*;
import comportamiento.Observer.*;
import comportamiento.State.*;
import comportamiento.Strategy.*;
import comportamiento.TemplateMethod.*;
import comportamiento.Visitor.*;

public class ProbarComportamiento {

    public ProbarComportamiento() {
        System.out.println("=== ProbarComportamiento ===");
    }

    // ----------- STRATEGY -----------
    public void probarStrategy() {
        System.out.println("\n[Strategy]");
        comportamiento.Strategy.Context ctx = new comportamiento.Strategy.Context(new ConcreteStrategyA());
        ctx.executeStrategy();
        ctx.setStrategy(new ConcreteStrategyB());
        ctx.executeStrategy();
    }

    // ----------- STATE -----------
    public void probarState() {
        System.out.println("\n[State]");
        comportamiento.State.Context ctx = new comportamiento.State.Context();
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
