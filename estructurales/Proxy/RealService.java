
/**
 * CLASE CONCRETA: RealService
 *
 * ROL EN EL PATRÓN:
 *  - Implementa la lógica real del servicio.
 *  - El Proxy delega a este objeto cuando las condiciones se cumplen.
 *
 * PROPÓSITO:
 *  Representar el objeto costoso o protegido al que se accede mediante el proxy.
 *
 * EJEMPLO EN BACKEND:
 *  - Servicio de base de datos o microservicio remoto.
 *
 * EJEMPLO EN PIZZERÍA ONLINE:
 *  - Servicio que genera el pedido y lo guarda en la base de datos.
 *
 * USO EN CLASE CLIENTE:
 *  Service servicio = new RealService();
 *  servicio.request();
 *
 * SALIDA ESPERADA:
 *  [RealService] Procesando pedido del cliente.
 */
public class RealService implements Service {

    @Override
    public void request() {
        System.out.println("[RealService] Procesando pedido del cliente.");
    }
}
