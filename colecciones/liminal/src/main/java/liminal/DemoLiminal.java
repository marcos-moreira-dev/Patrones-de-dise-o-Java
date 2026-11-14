package liminal;

import java.util.Locale;
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
import liminal.comportamiento.observer.ClienteObserver;
import liminal.comportamiento.state.ClienteState;
import liminal.comportamiento.strategy.ClienteStrategy;

/** Ejecuta tres escenas independientes por consola. */
public final class DemoLiminal {
    private DemoLiminal() {}

    public static void main(String[] args) {
        String seleccion = args.length == 0 ? "todos" : args[0].toLowerCase(Locale.ROOT);
        switch (seleccion) {
            case "state" -> ejecutar("STATE — Puerta", () -> ClienteState.ejecutar(System.out));
            case "strategy" -> ejecutar("STRATEGY — Monstruo oscuro", () -> ClienteStrategy.ejecutar(System.out));
            case "observer" -> ejecutar("OBSERVER — Interruptor", () -> ClienteObserver.ejecutar(System.out));
            case "singleton" -> ejecutar("SINGLETON — Panel de avisos", () -> ClienteSingleton.ejecutar(System.out));
            case "factorymethod" -> ejecutar("FACTORY METHOD — Apariciones", () -> ClienteFactoryMethod.ejecutar(System.out));
            case "abstractfactory" -> ejecutar("ABSTRACT FACTORY — Ambientes", () -> ClienteAbstractFactory.ejecutar(System.out));
            case "builder" -> ejecutar("BUILDER — Habitación", () -> ClienteBuilder.ejecutar(System.out));
            case "prototype" -> ejecutar("PROTOTYPE — Habitación", () -> ClientePrototype.ejecutar(System.out));
            case "adapter" -> ejecutar("ADAPTER — Radio antigua", () -> ClienteAdapter.ejecutar(System.out));
            case "bridge" -> ejecutar("BRIDGE — Alertas", () -> ClienteBridge.ejecutar(System.out));
            case "composite" -> ejecutar("COMPOSITE — Grupos de luces", () -> ClienteComposite.ejecutar(System.out));
            case "decorator" -> ejecutar("DECORATOR — Linterna", () -> ClienteDecorator.ejecutar(System.out));
            case "facade" -> ejecutar("FACADE — Cruce seguro", () -> ClienteFacade.ejecutar(System.out));
            case "flyweight" -> ejecutar("FLYWEIGHT — Texturas", () -> ClienteFlyweight.ejecutar(System.out));
            case "proxy" -> ejecutar("PROXY — Cámara", () -> ClienteProxy.ejecutar(System.out));
            case "chainofresponsibility" -> ejecutar("CHAIN OF RESPONSIBILITY — Cruce", () -> ClienteChainOfResponsibility.ejecutar(System.out));
            case "command" -> ejecutar("COMMAND — Panel", () -> ClienteCommand.ejecutar(System.out));
            case "interpreter" -> ejecutar("INTERPRETER — Lenguaje", () -> ClienteInterpreter.ejecutar(System.out));
            case "iterator" -> ejecutar("ITERATOR — Habitaciones", () -> ClienteIterator.ejecutar(System.out));
            case "mediator" -> ejecutar("MEDIATOR — Sensores", () -> ClienteMediator.ejecutar(System.out));
            case "memento" -> ejecutar("MEMENTO — Linterna", () -> ClienteMemento.ejecutar(System.out));
            case "templatemethod" -> ejecutar("TEMPLATE METHOD — Rutas", () -> ClienteTemplateMethod.ejecutar(System.out));
            case "visitor" -> ejecutar("VISITOR — Inspección", () -> ClienteVisitor.ejecutar(System.out));
            case "todos" -> {
                ejecutar("SINGLETON — Panel de avisos", () -> ClienteSingleton.ejecutar(System.out));
                ejecutar("FACTORY METHOD — Apariciones", () -> ClienteFactoryMethod.ejecutar(System.out));
                ejecutar("ABSTRACT FACTORY — Ambientes", () -> ClienteAbstractFactory.ejecutar(System.out));
                ejecutar("BUILDER — Habitación", () -> ClienteBuilder.ejecutar(System.out));
                ejecutar("PROTOTYPE — Habitación", () -> ClientePrototype.ejecutar(System.out));
                ejecutar("ADAPTER — Radio antigua", () -> ClienteAdapter.ejecutar(System.out));
                ejecutar("BRIDGE — Alertas", () -> ClienteBridge.ejecutar(System.out));
                ejecutar("COMPOSITE — Grupos de luces", () -> ClienteComposite.ejecutar(System.out));
                ejecutar("DECORATOR — Linterna", () -> ClienteDecorator.ejecutar(System.out));
                ejecutar("FACADE — Cruce seguro", () -> ClienteFacade.ejecutar(System.out));
                ejecutar("FLYWEIGHT — Texturas", () -> ClienteFlyweight.ejecutar(System.out));
                ejecutar("PROXY — Cámara", () -> ClienteProxy.ejecutar(System.out));
                ejecutar("CHAIN OF RESPONSIBILITY — Cruce", () -> ClienteChainOfResponsibility.ejecutar(System.out));
                ejecutar("COMMAND — Panel", () -> ClienteCommand.ejecutar(System.out));
                ejecutar("INTERPRETER — Lenguaje", () -> ClienteInterpreter.ejecutar(System.out));
                ejecutar("ITERATOR — Habitaciones", () -> ClienteIterator.ejecutar(System.out));
                ejecutar("MEDIATOR — Sensores", () -> ClienteMediator.ejecutar(System.out));
                ejecutar("MEMENTO — Linterna", () -> ClienteMemento.ejecutar(System.out));
                ejecutar("TEMPLATE METHOD — Rutas", () -> ClienteTemplateMethod.ejecutar(System.out));
                ejecutar("VISITOR — Inspección", () -> ClienteVisitor.ejecutar(System.out));
                ejecutar("STATE — Puerta", () -> ClienteState.ejecutar(System.out));
                ejecutar("STRATEGY — Monstruo oscuro", () -> ClienteStrategy.ejecutar(System.out));
                ejecutar("OBSERVER — Interruptor", () -> ClienteObserver.ejecutar(System.out));
            }
            default -> throw new IllegalArgumentException("Elige un patrón por su nombre en minúsculas o todos");
        }
    }

    private static void ejecutar(String nombre, Runnable accion) {
        System.out.println("\n=== " + nombre + " ===");
        accion.run();
    }
}
