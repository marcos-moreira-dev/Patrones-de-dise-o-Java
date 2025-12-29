

/**
 * CLASE ADAPTADORA: Adapter
 *
 * ROL EN EL PATRÓN:
 *  - Implementa la interfaz Target.
 *  - Traduce las llamadas de la interfaz esperada (Target)
 *    a las llamadas reales del Adaptee.
 *
 * PROPÓSITO:
 *  Permitir la colaboración entre sistemas incompatibles.
 *
 * CRITERIOS DE USO:
 *  ✅ Cuando necesitas conectar clases sin modificar su código original.
 *  ✅ Cuando hay dependencia con un módulo de terceros o legado.
 *
 * EJEMPLO EN BACKEND:
 *  - Un servicio que adapta la respuesta de una API externa al formato de tu aplicación.
 *
 * EJEMPLO EN PIZZERÍA ONLINE:
 *  - Un adaptador que traduce los pedidos del sistema interno al formato del proveedor de delivery.
 *
 * USO EN CLASE CLIENTE:
 *  Target sistema = new Adapter(new Adaptee());
 *  sistema.request();
 *
 * SALIDA ESPERADA:
 *  Solicitando pedido desde el sistema principal...
 *  [Proveedor Externo] Recibiendo solicitud en formato incompatible.
 */
public class Adapter implements Target {
    private Adaptee adaptee;

    // Constructor que recibe un objeto Adaptee
    public Adapter(Adaptee adaptee) {
        this.adaptee = adaptee;
    }

    @Override
    public void request() {
        System.out.println("Adaptando la solicitud...");
        adaptee.specificRequest();
    }
}