

/**
 * PATRÓN: Command
 *
 * INTERFAZ: Command
 *
 * ROL EN EL PATRÓN:
 *  - Define la interfaz común para todos los comandos.
 *  - Declara el método `execute()`, que encapsula una acción específica.
 *
 * PROPÓSITO:
 *  Encapsular una petición como un objeto, permitiendo parametrizar
 *  y manejar operaciones como si fueran datos.
 *
 * CRITERIOS DE USO:
 *  ✅ Cuando se necesita almacenar o ejecutar operaciones bajo demanda.
 *  ✅ Cuando se quiere implementar "Deshacer/Rehacer".
 *  ✅ Cuando se busca desacoplar el emisor (Invoker) del receptor (Receiver).
 *
 * VENTAJAS:
 *  + Desacopla quién solicita la acción de quién la ejecuta.
 *  + Permite colas, logs y deshacer fácilmente.
 *
 * DESVENTAJAS:
 *  - Puede generar muchas clases si hay muchos comandos distintos.
 *
 * EJEMPLO EN BACKEND:
 *  - Un sistema de tareas programadas (Scheduler) donde cada tarea es un comando.
 *
 * EJEMPLO EN PIZZERÍA ONLINE:
 *  - Cada pedido (comando) encapsula la acción “preparar pizza”, ejecutada por Cocina.
 *
 * USO EN CLASE CLIENTE:
 *  Receiver cocina = new Receiver();
 *  Command preparar = new ConcreteCommand(cocina, "Pizza Napolitana");
 *  Invoker invoker = new Invoker();
 *  invoker.setCommand(preparar);
 *  invoker.executeCommand();
 *
 * SALIDA ESPERADA:
 *  Preparando pedido: Pizza Napolitana
 */
public interface Command {
    void execute();
}
