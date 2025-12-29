

/**
 * CLASE CONCRETA: ConcreteCommand
 *
 * ROL EN EL PATRÓN:
 *  - Implementa la interfaz Command.
 *  - Llama a los métodos apropiados del Receiver.
 *
 * PROPÓSITO:
 *  Enlazar un comando específico con su receptor.
 *
 * CRITERIOS DE USO:
 *  ✅ Cuando cada operación debe encapsular su ejecución.
 *  ✅ Cuando se necesita una estructura de comandos reutilizables.
 *
 * EJEMPLO EN BACKEND:
 *  - Comando “GuardarReporte” que llama a ReportService.save().
 *
 * EJEMPLO EN PIZZERÍA ONLINE:
 *  - Comando “PrepararPizza” que indica a la cocina qué pedido hacer.
 *
 * USO EN CLASE CLIENTE:
 *  Command cmd = new ConcreteCommand(cocina, "Pizza Napolitana");
 *  cmd.execute();
 *
 * SALIDA ESPERADA:
 *  Preparando pedido: Pizza Napolitana
 */
public class ConcreteCommand implements Command {

    private final Receiver receiver;
    private final String pedido;

    public ConcreteCommand(Receiver receiver, String pedido) {
        this.receiver = receiver;
        this.pedido = pedido;
    }

    @Override
    public void execute() {
        receiver.action(pedido);
    }
}
