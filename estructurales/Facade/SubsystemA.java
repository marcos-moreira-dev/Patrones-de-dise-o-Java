package estructurales.Facade;

/**
 * CLASE: SubsystemA
 *
 * ROL EN EL PATRÓN:
 *  - Parte del sistema interno al que la fachada delega trabajo.
 *  - No conoce la existencia de la fachada.
 *
 * PROPÓSITO:
 *  Realizar parte de la funcionalidad compleja del sistema.
 *
 * EJEMPLO EN BACKEND:
 *  - Validación de datos o autenticación de usuarios.
 *
 * EJEMPLO EN PIZZERÍA ONLINE:
 *  - Valida que el pedido sea correcto (tipo de pizza, ingredientes, etc.).
 *
 * USO EN CLASE CLIENTE:
 *  SubsystemA validador = new SubsystemA();
 *  validador.validarPedido("Pizza Napolitana");
 *
 * SALIDA ESPERADA:
 *  [Subsistema A] Validando pedido: Pizza Napolitana
 */
public class SubsystemA {
    public void validarPedido(String pedido) {
        System.out.println("[Subsistema A] Validando pedido: " + pedido);
    }
}
