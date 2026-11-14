package liminal;

import java.util.Locale;
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
            case "todos" -> {
                ejecutar("STATE — Puerta", () -> ClienteState.ejecutar(System.out));
                ejecutar("STRATEGY — Monstruo oscuro", () -> ClienteStrategy.ejecutar(System.out));
                ejecutar("OBSERVER — Interruptor", () -> ClienteObserver.ejecutar(System.out));
            }
            default -> throw new IllegalArgumentException("Elige state, strategy, observer o todos");
        }
    }

    private static void ejecutar(String nombre, Runnable accion) {
        System.out.println("\n=== " + nombre + " ===");
        accion.run();
    }
}
