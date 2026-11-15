package liminal;

import java.util.Locale;

import liminal.creacionales.singleton.ClienteSingleton;
import liminal.creacionales.factorymethod.ClienteFactoryMethod;
import liminal.creacionales.abstractfactory.ClienteAbstractFactory;
import liminal.creacionales.builder.ClienteBuilder;
import liminal.creacionales.prototype.ClientePrototype;
import liminal.estructurales.adapter.ClienteAdapter;
import liminal.estructurales.bridge.ClienteBridge;
import liminal.estructurales.composite.ClienteComposite;
import liminal.estructurales.decorator.ClienteDecorator;
import liminal.estructurales.facade.ClienteFacade;
import liminal.estructurales.flyweight.ClienteFlyweight;
import liminal.estructurales.proxy.ClienteProxy;
import liminal.comportamiento.chainofresponsibility.ClienteChainOfResponsibility;
import liminal.comportamiento.command.ClienteCommand;
import liminal.comportamiento.interpreter.ClienteInterpreter;
import liminal.comportamiento.iterator.ClienteIterator;
import liminal.comportamiento.mediator.ClienteMediator;
import liminal.comportamiento.memento.ClienteMemento;
import liminal.comportamiento.templatemethod.ClienteTemplateMethod;
import liminal.comportamiento.visitor.ClienteVisitor;
import liminal.comportamiento.state.ClienteState;
import liminal.comportamiento.strategy.ClienteStrategy;
import liminal.comportamiento.observer.ClienteObserver;

/**
 * Índice manual de los 23 ejemplos: primero la categoría y luego el patrón.
 * Cada ClienteX también tiene su propio main para ejecutarse por separado.
 */
public final class DemoLiminal {
    private DemoLiminal() {}

    public static void main(String[] args) {
        String seleccion = args.length == 0 ? "todos" : args[0].toLowerCase(Locale.ROOT);
        switch (seleccion) {
            case "todos" -> ejecutarTodos();
            case "creacionales" -> ejecutarCreacionales();
            case "estructurales" -> ejecutarEstructurales();
            case "comportamiento" -> ejecutarComportamiento();
            case "singleton" -> probarSingleton();
            case "factorymethod" -> probarFactoryMethod();
            case "abstractfactory" -> probarAbstractFactory();
            case "builder" -> probarBuilder();
            case "prototype" -> probarPrototype();
            case "adapter" -> probarAdapter();
            case "bridge" -> probarBridge();
            case "composite" -> probarComposite();
            case "decorator" -> probarDecorator();
            case "facade" -> probarFacade();
            case "flyweight" -> probarFlyweight();
            case "proxy" -> probarProxy();
            case "chainofresponsibility" -> probarChainOfResponsibility();
            case "command" -> probarCommand();
            case "interpreter" -> probarInterpreter();
            case "iterator" -> probarIterator();
            case "mediator" -> probarMediator();
            case "memento" -> probarMemento();
            case "templatemethod" -> probarTemplateMethod();
            case "visitor" -> probarVisitor();
            case "state" -> probarState();
            case "strategy" -> probarStrategy();
            case "observer" -> probarObserver();
            default -> throw new IllegalArgumentException(
                    "Elige todos, una categoría (creacionales, estructurales, comportamiento) o un patrón");
        }
    }

    /** Orden de lectura equivalente al orquestador de la colección técnica. */
    public static void ejecutarTodos() {
        ejecutarCreacionales();
        ejecutarEstructurales();
        ejecutarComportamiento();
    }

    public static void ejecutarCreacionales() {
        probarSingleton();
        probarFactoryMethod();
        probarAbstractFactory();
        probarBuilder();
        probarPrototype();
    }

    public static void ejecutarEstructurales() {
        probarAdapter();
        probarBridge();
        probarComposite();
        probarDecorator();
        probarFacade();
        probarFlyweight();
        probarProxy();
    }

    public static void ejecutarComportamiento() {
        probarChainOfResponsibility();
        probarCommand();
        probarInterpreter();
        probarIterator();
        probarMediator();
        probarMemento();
        probarTemplateMethod();
        probarVisitor();
        probarState();
        probarStrategy();
        probarObserver();
    }

    // ---- CREACIONALES ----
    public static void probarSingleton() {
        ejecutar("SINGLETON — Panel de avisos", () -> ClienteSingleton.ejecutar(System.out));
    }

    public static void probarFactoryMethod() {
        ejecutar("FACTORY METHOD — Apariciones", () -> ClienteFactoryMethod.ejecutar(System.out));
    }

    public static void probarAbstractFactory() {
        ejecutar("ABSTRACT FACTORY — Ambientes", () -> ClienteAbstractFactory.ejecutar(System.out));
    }

    public static void probarBuilder() {
        ejecutar("BUILDER — Habitación", () -> ClienteBuilder.ejecutar(System.out));
    }

    public static void probarPrototype() {
        ejecutar("PROTOTYPE — Habitación", () -> ClientePrototype.ejecutar(System.out));
    }

    // ---- ESTRUCTURALES ----
    public static void probarAdapter() {
        ejecutar("ADAPTER — Radio antigua", () -> ClienteAdapter.ejecutar(System.out));
    }

    public static void probarBridge() {
        ejecutar("BRIDGE — Alertas", () -> ClienteBridge.ejecutar(System.out));
    }

    public static void probarComposite() {
        ejecutar("COMPOSITE — Grupos de luces", () -> ClienteComposite.ejecutar(System.out));
    }

    public static void probarDecorator() {
        ejecutar("DECORATOR — Linterna", () -> ClienteDecorator.ejecutar(System.out));
    }

    public static void probarFacade() {
        ejecutar("FACADE — Cruce seguro", () -> ClienteFacade.ejecutar(System.out));
    }

    public static void probarFlyweight() {
        ejecutar("FLYWEIGHT — Texturas", () -> ClienteFlyweight.ejecutar(System.out));
    }

    public static void probarProxy() {
        ejecutar("PROXY — Cámara", () -> ClienteProxy.ejecutar(System.out));
    }

    // ---- COMPORTAMIENTO ----
    public static void probarChainOfResponsibility() {
        ejecutar("CHAIN OF RESPONSIBILITY — Cruce", () -> ClienteChainOfResponsibility.ejecutar(System.out));
    }

    public static void probarCommand() {
        ejecutar("COMMAND — Panel", () -> ClienteCommand.ejecutar(System.out));
    }

    public static void probarInterpreter() {
        ejecutar("INTERPRETER — Lenguaje", () -> ClienteInterpreter.ejecutar(System.out));
    }

    public static void probarIterator() {
        ejecutar("ITERATOR — Habitaciones", () -> ClienteIterator.ejecutar(System.out));
    }

    public static void probarMediator() {
        ejecutar("MEDIATOR — Sensores", () -> ClienteMediator.ejecutar(System.out));
    }

    public static void probarMemento() {
        ejecutar("MEMENTO — Linterna", () -> ClienteMemento.ejecutar(System.out));
    }

    public static void probarTemplateMethod() {
        ejecutar("TEMPLATE METHOD — Rutas", () -> ClienteTemplateMethod.ejecutar(System.out));
    }

    public static void probarVisitor() {
        ejecutar("VISITOR — Inspección", () -> ClienteVisitor.ejecutar(System.out));
    }

    public static void probarState() {
        ejecutar("STATE — Puerta", () -> ClienteState.ejecutar(System.out));
    }

    public static void probarStrategy() {
        ejecutar("STRATEGY — Monstruo oscuro", () -> ClienteStrategy.ejecutar(System.out));
    }

    public static void probarObserver() {
        ejecutar("OBSERVER — Interruptor", () -> ClienteObserver.ejecutar(System.out));
    }

    private static void ejecutar(String nombre, Runnable accion) {
        System.out.println("\n=== " + nombre + " ===");
        accion.run();
    }
}
