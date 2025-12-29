package comportamiento.ChainOfResponsibility;

/**
 * CLASE CONCRETA: ConcreteHandlerB
 *
 * ROL EN EL PATRÓN:
 *  - Procesa la solicitud que proviene del manejador anterior en la cadena.
 *
 * PROPÓSITO:
 *  Ejecutar la acción final de la cadena.
 *
 * EJEMPLO EN BACKEND:
 *  - Servicio que procesa la lógica principal (guardar pedido, enviar email, etc.).
 *
 * EJEMPLO EN PIZZERÍA ONLINE:
 *  - La cocina recibe el pedido validado y prepara la pizza.
 *
 * USO EN CLASE CLIENTE:
 *  Handler cocina = new ConcreteHandlerB();
 *  cocina.handleRequest("Preparar pedido de pizza");
 *
 * SALIDA ESPERADA:
 *  Cocina: preparando pizza.
 */
public class ConcreteHandlerB extends Handler {

    @Override
    public void handleRequest(String request) {
        if (request.contains("pedido")) {
            System.out.println("Cocina: preparando pizza.");
        } else {
            System.out.println("Cocina: solicitud no válida, descartando.");
        }
    }
}
