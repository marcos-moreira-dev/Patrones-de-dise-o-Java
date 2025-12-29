
package comportamiento.Strategy;
/**
 * PATRÓN: Strategy
 *
 * INTERFAZ: Strategy
 *
 * ROL EN EL PATRÓN:
 *  - Define una interfaz común para todas las estrategias (algoritmos).
 *  - Permite que el objeto Contexto ejecute una estrategia sin conocer su implementación concreta.
 *
 * PROPÓSITO:
 *  Separar la lógica de selección de algoritmos de su ejecución,
 *  permitiendo intercambiar dinámicamente el comportamiento en tiempo de ejecución.
 *
 * CRITERIOS DE USO:
 *  ✅ Cuando hay varias formas de realizar una operación (diferentes algoritmos o políticas).
 *  ✅ Cuando se necesita cambiar el comportamiento del sistema sin modificar su estructura.
 *  ✅ Cuando se quiere evitar condicionales largos (`if/else` o `switch`) para seleccionar un algoritmo.
 *
 * ⚠️ Evitarlo cuando:
 *  - Solo se necesita una implementación fija (crea sobreingeniería).
 *
 * VENTAJAS:
 *  + Facilita la extensión con nuevos algoritmos sin modificar código existente.
 *  + Fomenta el principio de abierto/cerrado.
 *
 * DESVENTAJAS:
 *  - Aumenta el número de clases en el sistema.
 *
 * EJEMPLO EN BACKEND:
 *  - Distintas estrategias de serialización (JSON, XML, YAML).
 *
 * EJEMPLO EN PIZZERÍA ONLINE:
 *  - Diferentes estrategias de cálculo de descuento según tipo de cliente:
 *    Cliente frecuente, cliente nuevo, cliente corporativo.
 *
 * USO EN CLASE CLIENTE:
 *  Context context = new Context(new ConcreteStrategyA());
 *  context.executeStrategy();
 */
public interface Strategy {
    void execute();
}
