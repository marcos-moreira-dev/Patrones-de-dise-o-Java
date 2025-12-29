

/**
 * CLASE: Invoker
 *
 * ROL EN EL PATRÓN:
 *  - Solicita la ejecución del comando.
 *  - No sabe cómo se ejecuta ni qué hace internamente.
 *
 * PROPÓSITO:
 *  Desacoplar al cliente del comando real y su receptor.
 *
 * CRITERIOS DE USO:
 *  ✅ Cuando el cliente no debe saber quién ejecuta el comando.
 *  ✅ Cuando se quiere programar, almacenar o ejecutar comandos dinámicamente.
 *
 * EJEMPLO EN BACKEND:
 *  - Una cola de tareas que ejecuta comandos recibidos en orden.
 *
 * EJEMPLO EN PIZZERÍA ONLINE:
 *  - El “Cajero” que toma el pedido y lo envía a la cocina.
 *
 * USO EN CLASE CLIENTE:
 *  Invoker invoker = new Invoker();
 *  invoker.setCommand(preparar);
 *  invoker.executeCommand();
 *
 * SALIDA ESPERADA:
 *  Preparando pedido: Pizza Napolitana
 */
public class Invoker {
    private Command command;

    public void setCommand(Command command) {
        this.command = command;
    }

    public void executeCommand() {
        if (command != null) {
            command.execute();
        }
    }
}
