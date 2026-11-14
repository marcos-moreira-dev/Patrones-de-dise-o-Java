package liminal;

import liminal.comportamiento.observer.PruebaObserver;
import liminal.laboratorios.relaciones.PruebaRelacionesUml;
import liminal.comportamiento.chainofresponsibility.ClienteChainOfResponsibility;
import liminal.comportamiento.command.ClienteCommand;
import liminal.comportamiento.interpreter.ClienteInterpreter;
import liminal.comportamiento.iterator.ClienteIterator;
import liminal.comportamiento.mediator.ClienteMediator;
import liminal.comportamiento.memento.ClienteMemento;
import liminal.comportamiento.templatemethod.ClienteTemplateMethod;
import liminal.comportamiento.visitor.ClienteVisitor;
import liminal.estructurales.adapter.ClienteAdapter;
import liminal.estructurales.bridge.ClienteBridge;
import liminal.estructurales.composite.ClienteComposite;
import liminal.estructurales.decorator.ClienteDecorator;
import liminal.estructurales.facade.ClienteFacade;
import liminal.estructurales.flyweight.ClienteFlyweight;
import liminal.estructurales.proxy.ClienteProxy;
import liminal.creacionales.abstractfactory.ClienteAbstractFactory;
import liminal.creacionales.builder.ClienteBuilder;
import liminal.creacionales.factorymethod.ClienteFactoryMethod;
import liminal.creacionales.prototype.ClientePrototype;
import liminal.creacionales.singleton.ClienteSingleton;
import liminal.comportamiento.state.PruebaState;
import liminal.comportamiento.strategy.PruebaStrategy;

/** Pruebas básicas sin JUnit ni Maven. */
public final class PruebasLiminal {
    private PruebasLiminal() {}

    public static void main(String[] args) {
        ClienteSingleton.probar();
        System.out.println("OK Singleton");
        ClienteFactoryMethod.probar();
        System.out.println("OK Factory Method");
        ClienteAbstractFactory.probar();
        System.out.println("OK Abstract Factory");
        ClienteBuilder.probar();
        System.out.println("OK Builder");
        ClientePrototype.probar();
        System.out.println("OK Prototype");
        ClienteAdapter.probar();
        System.out.println("OK Adapter");
        ClienteBridge.probar();
        System.out.println("OK Bridge");
        ClienteComposite.probar();
        System.out.println("OK Composite");
        ClienteDecorator.probar();
        System.out.println("OK Decorator");
        ClienteFacade.probar();
        System.out.println("OK Facade");
        ClienteFlyweight.probar();
        System.out.println("OK Flyweight");
        ClienteProxy.probar();
        System.out.println("OK Proxy");
        ClienteChainOfResponsibility.probar();
        System.out.println("OK ChainOfResponsibility");
        ClienteCommand.probar();
        System.out.println("OK Command");
        ClienteInterpreter.probar();
        System.out.println("OK Interpreter");
        ClienteIterator.probar();
        System.out.println("OK Iterator");
        ClienteMediator.probar();
        System.out.println("OK Mediator");
        ClienteMemento.probar();
        System.out.println("OK Memento");
        ClienteTemplateMethod.probar();
        System.out.println("OK TemplateMethod");
        ClienteVisitor.probar();
        System.out.println("OK Visitor");
        PruebaState.ejecutar();
        System.out.println("OK State (R-05, R-12)");
        PruebaStrategy.ejecutar();
        System.out.println("OK Strategy (R-02, R-08, R-12)");
        PruebaObserver.ejecutar();
        System.out.println("OK Observer (R-07)");
        System.out.println("OK 23/23 pruebas del catálogo liminal.");
        PruebaRelacionesUml.ejecutar();
        System.out.println("OK laboratorio UML (R-01, R-03, R-09, R-10, R-12)");
        VerificarDocumentacion.ejecutar();
    }
}
