
import java.util.Scanner;
import creacionales.ProbarCreacionales;
import estructurales.ProbarEstructurales;
import comportamiento.ProbarComportamiento;

/**
 * Clase App con menú principal para probar todos los patrones de diseño.
 */
public class App {
    private ProbarCreacionales creacionales;
    private ProbarEstructurales estructurales;
    private ProbarComportamiento comportamiento;

    public App() {
        creacionales = new ProbarCreacionales();
        estructurales = new ProbarEstructurales();
        comportamiento = new ProbarComportamiento();
    }

    /** Presenta los ejemplos creacionales en la consola principal. */
    public void ejecutarCreacionales() {
        System.out.println("\n=== PATRONES CREACIONALES ===");
        creacionales.probarSingleton();
        creacionales.probarAbstractFactory();
        creacionales.probarBuilder();
        creacionales.probarFactoryMethod();
        creacionales.probarPrototype();
    }

    /** Presenta los ejemplos estructurales en la consola principal. */
    public void ejecutarEstructurales() {
        System.out.println("\n=== PATRONES ESTRUCTURALES ===");
        estructurales.probarAdapter();
        estructurales.probarBridge();
        estructurales.probarComposite();
        estructurales.probarDecorator();
        estructurales.probarFacade();
        estructurales.probarFlyweight();
        estructurales.probarProxy();
        estructurales.probarIterator();
        estructurales.probarObserver();
        
    }

    /** Presenta los ejemplos de comportamiento en la consola principal. */
    public void ejecutarComportamiento() {
        System.out.println("\n=== PATRONES DE COMPORTAMIENTO ===");
        comportamiento.probarStrategy();
        comportamiento.probarState();
        comportamiento.probarChainOfResponsibility();
        comportamiento.probarCommand();
        comportamiento.probarMediator();
        comportamiento.probarMemento();
        comportamiento.probarTemplateMethod();
        comportamiento.probarVisitor();
    }

    /**
     * Gestiona el menú textual sin abortar ante letras, números inválidos o EOF.
     * Una entrada rechazada no ejecuta patrones; vuelve a pedir una opción.
     */
    public void mostrarMenu() {
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.println("\n==============================");
            System.out.println("  MENÚ PRINCIPAL - PATRONES DE DISEÑO");
            System.out.println("==============================");
            System.out.println("1. Patrones Creacionales");
            System.out.println("2. Patrones Estructurales");
            System.out.println("3. Patrones de Comportamiento");
            System.out.println("0. Salir");
            System.out.print("Seleccione una opción: ");
            if (!sc.hasNextLine()) {
                System.out.println("Entrada finalizada. Saliendo del programa...");
                return;
            }
            int opcion;
            try {
                opcion = Integer.parseInt(sc.nextLine().strip());
            } catch (NumberFormatException e) {
                System.out.println("Opción inválida. Escribe 0, 1, 2 o 3.");
                continue;
            }

            switch (opcion) {
                case 1 -> ejecutarCreacionales();
                case 2 -> ejecutarEstructurales();
                case 3 -> ejecutarComportamiento();
                case 0 -> {
                    System.out.println("Saliendo del programa...");
                    return;
                }
                default -> System.out.println("Opción inválida.");
            }
        }
    }
}
