
/**
 * PATRÓN: Adapter (Adaptador)
 *
 * INTERFAZ: Target
 *
 * ROL EN EL PATRÓN:
 *  - Define la interfaz esperada por el cliente.
 *  - El adaptador implementará esta interfaz para que el cliente
 *    pueda usar la funcionalidad del Adaptee sin saber que es diferente.
 *
 * PROPÓSITO:
 *  Permitir que clases con interfaces incompatibles trabajen juntas.
 *
 * CRITERIOS DE USO:
 *  ✅ Cuando tienes una clase existente que quieres reutilizar, pero su interfaz no coincide.
 *  ✅ Cuando integras sistemas legados o APIs externas con tu código actual.
 *
 * VENTAJAS:
 *  + Reutilización de código existente sin modificarlo.
 *  + Desacopla el cliente de implementaciones específicas.
 *
 * DESVENTAJAS:
 *  - Aumenta la cantidad de clases intermediarias.
 *
 * EJEMPLO EN BACKEND:
 *  - Adaptar una API REST para que funcione como si fuera un servicio local.
 *
 * EJEMPLO EN PIZZERÍA ONLINE:
 *  - El sistema de pedidos necesita comunicarse con un proveedor externo de delivery
 *    que usa otra interfaz.
 *
 * USO EN CLASE CLIENTE:
 *  Target sistema = new Adapter(new Adaptee());
 *  sistema.request();
 *
 * SALIDA ESPERADA:
 *  Solicitando pedido desde el sistema principal...
 *  [Proveedor Externo] Recibiendo solicitud en formato incompatible.
 */
public interface Target {
    void request();
}