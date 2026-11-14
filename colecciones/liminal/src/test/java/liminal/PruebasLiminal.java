package liminal;

import liminal.comportamiento.observer.PruebaObserver;
import liminal.comportamiento.state.PruebaState;
import liminal.comportamiento.strategy.PruebaStrategy;

/** Pruebas básicas sin JUnit ni Maven. */
public final class PruebasLiminal {
    private PruebasLiminal() {}

    public static void main(String[] args) {
        PruebaState.ejecutar();
        System.out.println("OK State (R-05, R-12)");
        PruebaStrategy.ejecutar();
        System.out.println("OK Strategy (R-02, R-08, R-12)");
        PruebaObserver.ejecutar();
        System.out.println("OK Observer (R-07)");
        System.out.println("OK 3/3 pruebas del piloto.");
    }
}
