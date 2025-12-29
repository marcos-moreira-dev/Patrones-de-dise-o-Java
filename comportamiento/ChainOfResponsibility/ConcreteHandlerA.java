package comportamiento.ChainOfResponsibility;

/**
 * CLASE CONCRETA: ConcreteHandlerA
 *
 * ROL EN EL PATRÓN:
 *  - Procesa la solicitud si puede hacerlo, o la pasa al siguiente manejador.
 *
 * PROPÓSITO:
 *  Simular el primer eslabón de la cadena.
 *
 * EJEMPLO EN BACKEND:
 *  - Middleware que valida si el pedido existe en la base de datos.
 *
 * EJEMPLO EN PIZZERÍA ONLINE:
 *  - La recepción verifica si el pedido está correctamente registrado.
 *
 * USO EN CLASE CLIENTE:
 *  Handler recepcion = new ConcreteHandlerA();
 *  recepcion.handleRequest("Preparar pizza");
 *
 * SALIDA ESPERADA:
 *  Recepción: verificando pedido...
 *  Cocina: preparando pizza.
 */
public class ConcreteHandlerA extends Handler {

    @Override
    public void handleRequest(String request) {
        if (request.contains("pedido")) {
            System.out.println("Recepción: verificando pedido...");
            if (next != null) next.handleRequest(request);
        } else {
            System.out.println("Recepción: solicitud no reconocida, pasando...");
            if (next != null) next.handleRequest(request);
        }
    }
}
