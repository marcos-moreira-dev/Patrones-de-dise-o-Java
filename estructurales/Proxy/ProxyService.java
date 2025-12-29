package estructurales.Proxy;
/**
 * CLASE CONCRETA: ProxyService
 *
 * ROL EN EL PATRÓN:
 *  - Controla el acceso al RealService.
 *  - Puede crear el objeto real bajo demanda, verificar permisos,
 *    registrar logs o aplicar cache.
 *
 * PROPÓSITO:
 *  Añadir una capa intermedia de control sin modificar el código del servicio real.
 *
 * CRITERIOS DE USO:
 *  ✅ Cuando el acceso debe estar restringido.
 *  ✅ Cuando el servicio es costoso de crear (lazy initialization).
 *  ✅ Cuando se requiere logging o cacheo de resultados.
 *
 * EJEMPLO EN BACKEND:
 *  - Proxy que valida credenciales antes de conectar con un API.
 *
 * EJEMPLO EN PIZZERÍA ONLINE:
 *  - Proxy que verifica si el cliente está autenticado antes de registrar un pedido.
 *
 * USO EN CLASE CLIENTE:
 *  Service proxy = new ProxyService("cliente123", "1234");
 *  proxy.request();
 *
 * SALIDA ESPERADA:
 *  [Proxy] Verificando permisos...
 *  [RealService] Procesando pedido del cliente.
 */
public class ProxyService implements Service {

    private RealService realService;
    private final String user;
    private final String password;

    public ProxyService(String user, String password) {
        this.user = user;
        this.password = password;
    }

    @Override
    public void request() {
        if (checkAccess()) {
            if (realService == null) {
                realService = new RealService(); // Lazy loading
            }
            realService.request();
        } else {
            System.out.println("[Proxy] Acceso denegado para el usuario: " + user);
        }
    }

    private boolean checkAccess() {
        System.out.println("[Proxy] Verificando permisos...");
        return "cliente123".equals(user) && "1234".equals(password);
    }
}
