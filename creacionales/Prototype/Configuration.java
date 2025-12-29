

/**
 * PATRÓN: Prototype
 *
 * PROPÓSITO:
 *  Permitir la creación de nuevos objetos copiando (clonando) instancias existentes,
 *  en lugar de instanciarlas desde cero. 
 *  Esto facilita la duplicación de estructuras complejas o costosas de inicializar.
 *
 * MOTIVACIÓN:
 *  En ingeniería de software, hay escenarios donde construir un objeto nuevo es
 *  más costoso que copiar uno existente. 
 *  Por ejemplo, configuraciones de entorno, plantillas de documentos o estructuras
 *  de datos que requieren inicialización pesada.
 *
 * MECANISMO:
 *  - Las clases deben implementar la interfaz `Cloneable`.
 *  - El método `clone()` crea una nueva instancia copiando los valores del objeto original.
 *  - El cliente puede modificar la copia sin afectar al prototipo base.
 *
 * CRITERIOS DE USO (CUÁNDO APLICARLO):
 *  ✅ Cuando la creación de un objeto sea costosa (por carga de datos, inicialización, etc.).
 *  ✅ Cuando el sistema necesite múltiples instancias similares pero no idénticas.
 *  ✅ Cuando se requiera independencia entre copias pero coherencia de estructura.
 *  ✅ Cuando quieras evitar la complejidad de múltiples constructores o fábricas.
 *
 *  ⚠️ Evitarlo cuando:
 *    - Los objetos contienen referencias cíclicas o estructuras muy anidadas difíciles de copiar.
 *    - El proceso de clonado es más complejo que la creación directa.
 *
 * VENTAJAS:
 *  + Ahorra recursos cuando se reutilizan estructuras complejas.
 *  + Permite mantener configuraciones base o plantillas.
 *  + Facilita la extensión sin modificar clases existentes.
 *
 * DESVENTAJAS:
 *  - Requiere implementar clonación profunda si hay referencias internas.
 *  - Puede producir inconsistencias si el clon no es independiente del original.
 *
 * EJEMPLO EN BACKEND:
 *  - Clonar una configuración base de servidor o entorno (`Configuration` o `AppContext`).
 *  - Duplicar objetos de sesión, mensajes o respuestas preconfiguradas.
 *
 * EJEMPLO EN PIZZERÍA ONLINE:
 *  - Usar una receta base de pizza como prototipo, clonarla y modificar ingredientes
 *    según el pedido (por ejemplo, duplicar una “Pizza Margarita” y agregar pepperoni).
 *
 * USO:
 *  Configuration original = new Configuration("Producción", "192.168.1.100");
 *  Configuration copia = original.clone();
 *  System.out.println(original);
 *  System.out.println(copia);
 */
public class Configuration implements Cloneable {

    private String environment;
    private String databaseHost;

    public Configuration(String environment, String databaseHost) {
        this.environment = environment;
        this.databaseHost = databaseHost;
    }

    @Override
    public Configuration clone() {
        try {
            return (Configuration) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException("Error al clonar la configuración");
        }
    }

    @Override
    public String toString() {
        return "Configuration [env=" + environment + ", dbHost=" + databaseHost + "]";
    }
}
