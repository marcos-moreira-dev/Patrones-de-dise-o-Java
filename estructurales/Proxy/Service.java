package estructurales.Proxy;

/**
 * PATRÓN: Proxy (Sustituto o Representante)
 *
 * INTERFAZ: Service
 *
 * ROL EN EL PATRÓN:
 *  - Define la interfaz común que tanto el objeto real (RealService)
 *    como el proxy (ProxyService) deben implementar.
 *
 * PROPÓSITO:
 *  Controlar el acceso a un objeto real (costoso, remoto o sensible)
 *  mediante un intermediario que añade lógica adicional antes o después
 *  de la llamada.
 *
 * CRITERIOS DE USO:
 *  ✅ Cuando el objeto real consume muchos recursos (memoria, red, CPU).
 *  ✅ Cuando se quiere agregar control de acceso, logging o cache.
 *  ✅ Cuando se necesita posponer la creación del objeto real (lazy loading).
 *
 * VENTAJAS:
 *  + Control de acceso, seguridad y rendimiento.
 *  + Transparencia para el cliente (mismo tipo de interfaz).
 *
 * DESVENTAJAS:
 *  - Aumenta la complejidad y número de clases.
 *
 * EJEMPLO EN BACKEND:
 *  - Proxy que valida tokens antes de acceder a un microservicio.
 *
 * EJEMPLO EN PIZZERÍA ONLINE:
 *  - Un proxy controla si el usuario está autenticado antes de ver pedidos.
 *
 * USO EN CLASE CLIENTE:
 *  Service servicio = new ProxyService();
 *  servicio.request();
 *
 * SALIDA ESPERADA:
 *  [Proxy] Verificando permisos...
 *  [RealService] Procesando pedido del cliente.
 */
public interface Service {
    void request();
}
