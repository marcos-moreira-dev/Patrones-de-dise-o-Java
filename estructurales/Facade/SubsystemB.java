
/**
 * CLASE: SubsystemB
 *
 * ROL EN EL PATRÓN:
 *  - Otro componente interno del sistema al que la fachada delega operaciones.
 *  - Trabaja junto con otros subsistemas pero sin conocerlos directamente.
 *
 * PROPÓSITO:
 *  Ejecutar la parte operativa del proceso.
 *
 * EJEMPLO EN BACKEND:
 *  - Servicio de negocio que ejecuta operaciones con datos validados.
 *
 * EJEMPLO EN PIZZERÍA ONLINE:
 *  - Cocina que prepara la pizza validada por el subsistema anterior.
 *
 * USO EN CLASE CLIENTE:
 *  SubsystemB cocina = new SubsystemB();
 *  cocina.prepararPedido("Pizza Napolitana");
 *
 * SALIDA ESPERADA:
 *  [Subsistema B] Preparando Pizza Napolitana
 */
public class SubsystemB {
    public void prepararPedido(String pedido) {
        System.out.println("[Subsistema B] Preparando " + pedido);
    }
}
