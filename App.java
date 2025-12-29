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

    public void ejecutarCreacionales() {
        System.out.println("\n=== PATRONES CREACIONALES ===");
        creacionales.probarSingleton();
        creacionales.probarAbstractFactory();
        creacionales.probarBuilder();
        creacionales.probarFactoryMethod();
        creacionales.probarPrototype();
    }

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

    public void mostrarMenu() {
        Scanner sc = new Scanner(System.in);
        int opcion;
        do {
            System.out.println("\n==============================");
            System.out.println("  MENÚ PRINCIPAL - PATRONES DE DISEÑO");
            System.out.println("==============================");
            System.out.println("1. Patrones Creacionales");
            System.out.println("2. Patrones Estructurales");
            System.out.println("3. Patrones de Comportamiento");
            System.out.println("0. Salir");
            System.out.print("Seleccione una opción: ");
            opcion = sc.nextInt();

            switch (opcion) {
                case 1 -> ejecutarCreacionales();
                case 2 -> ejecutarEstructurales();
                case 3 -> ejecutarComportamiento();
                case 0 -> System.out.println("Saliendo del programa...");
                default -> System.out.println("Opción inválida.");
            }
        } while (opcion != 0);
        sc.close();
    }
}
