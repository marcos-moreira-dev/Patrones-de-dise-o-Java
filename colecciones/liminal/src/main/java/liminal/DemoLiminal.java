package liminal;

import java.util.Locale;
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
            case "todos" -> {
                ejecutar("SINGLETON — Panel de avisos", () -> ClienteSingleton.ejecutar(System.out));
                ejecutar("FACTORY METHOD — Apariciones", () -> ClienteFactoryMethod.ejecutar(System.out));
                ejecutar("ABSTRACT FACTORY — Ambientes", () -> ClienteAbstractFactory.ejecutar(System.out));
                ejecutar("BUILDER — Habitación", () -> ClienteBuilder.ejecutar(System.out));
                ejecutar("PROTOTYPE — Habitación", () -> ClientePrototype.ejecutar(System.out));
                ejecutar("STATE — Puerta", () -> ClienteState.ejecutar(System.out));
                ejecutar("STRATEGY — Monstruo oscuro", () -> ClienteStrategy.ejecutar(System.out));
                ejecutar("OBSERVER — Interruptor", () -> ClienteObserver.ejecutar(System.out));
            }
            default -> throw new IllegalArgumentException("Elige singleton, factorymethod, abstractfactory, builder, prototype, state, strategy, observer o todos");
        }
    }

    private static void ejecutar(String nombre, Runnable accion) {
        System.out.println("\n=== " + nombre + " ===");
        accion.run();
    }
}
