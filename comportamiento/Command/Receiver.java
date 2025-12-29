
/**
 * CLASE: Receiver
 *
 * ROL EN EL PATRÓN:
 *  - Contiene la lógica real que ejecuta la acción solicitada.
 *  - Es el destinatario final del comando.
 *
 * PROPÓSITO:
 *  Encapsular la operación concreta (el trabajo que se debe realizar).
 *
 * EJEMPLO EN BACKEND:
 *  - Módulo que procesa operaciones específicas (por ejemplo, enviar email, guardar archivo).
 *
 * EJEMPLO EN PIZZERÍA ONLINE:
 *  - La “Cocina” que realmente prepara los pedidos.
 *
 * USO EN CLASE CLIENTE:
 *  Receiver cocina = new Receiver();
 *  cocina.action("Pizza Napolitana");
 *
 * SALIDA ESPERADA:
 *  Preparando pedido: Pizza Napolitana
 */
public class Receiver {
    public void action(String pedido) {
        System.out.println("Preparando pedido: " + pedido);
    }
}
