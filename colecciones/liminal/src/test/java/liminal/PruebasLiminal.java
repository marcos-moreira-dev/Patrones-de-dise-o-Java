package liminal;

import liminal.comportamiento.observer.PruebaObserver;
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
        PruebaState.ejecutar();
        System.out.println("OK State (R-05, R-12)");
        PruebaStrategy.ejecutar();
        System.out.println("OK Strategy (R-02, R-08, R-12)");
        PruebaObserver.ejecutar();
        System.out.println("OK Observer (R-07)");
        System.out.println("OK 8/8 pruebas del catálogo liminal.");
    }
}
