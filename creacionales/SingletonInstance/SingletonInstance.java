

/**
 * PATRÓN: Singleton
 * 
 * PROPÓSITO:
 *  Garantiza que exista una única instancia de una clase durante
 *  toda la ejecución del programa y proporciona un punto de acceso global a ella.
 *
 * MOTIVACIÓN:
 *  En ingeniería de software, hay recursos o servicios que deben mantenerse únicos
 *  para evitar inconsistencia de estado, uso excesivo de memoria o conflictos de acceso.
 *  Ejemplo clásico: conexión global a una base de datos, gestor de logs o configurador de entorno.
 *
 * MECANISMO:
 *  - Constructor privado → impide crear instancias externas.
 *  - Atributo estático de la propia clase → almacena la instancia única.
 *  - Método estático público → permite el acceso controlado a esa instancia.
 *
 * CRITERIOS DE USO (CUÁNDO APLICARLO):
 *  ✅ Cuando necesitas exactamente un único punto de control sobre un recurso compartido.
 *  ✅ Cuando la creación repetida de objetos sería costosa o incoherente.
 *  ✅ Cuando varios módulos o clases deben acceder a la misma instancia con el mismo estado.
 *  ✅ Cuando quieres mantener consistencia global en la configuración del sistema.
 *
 *  ⚠️ Evitarlo cuando:
 *    - La clase necesita ser fácilmente testeable (por ejemplo, en pruebas unitarias).
 *    - Existen múltiples contextos o entornos donde se requiere independencia de estado.
 *
 * VENTAJAS:
 *  + Ahorra recursos (una sola instancia).
 *  + Proporciona acceso global coherente.
 *  + Facilita la sincronización de configuraciones o servicios.
 *
 * DESVENTAJAS:
 *  - Introduce acoplamiento global.
 *  - Dificulta pruebas unitarias y paralelismo si no se maneja con cuidado.
 *
 * EJEMPLO EN BACKEND:
 *  - Gestor de conexión a base de datos (DatabaseConnection.getInstance()).
 *  - Servicio de logs global (Logger.getInstance()).
 *
 * EJEMPLO EN PIZZERÍA ONLINE:
 *  - HornoVirtual.getInstance(): un único horno que procesa todos los pedidos.
 *    Si hubiera múltiples hornos desincronizados, las pizzas podrían duplicarse o perderse.
 *
 * USO:
 *  SingletonInstance s1 = SingletonInstance.getInstance();
 *  SingletonInstance s2 = SingletonInstance.getInstance();
 *  s1.doSomethingUnique();  // Ambos apuntan a la misma instancia.
 */
public class SingletonInstance {
    private static SingletonInstance instance;
    private SingletonInstance() {
        // Constructor privado para evitar instanciación externa
    }
    public static SingletonInstance getInstance() {
        if (instance == null) {
            instance = new SingletonInstance();
        }
        return instance;
    }

    public void doSomethingUnique() {
        System.out.println("Singleton Instance: Hello World!");
    }
}
