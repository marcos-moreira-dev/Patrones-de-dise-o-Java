package estructurales.Facade;
/**
 * PATRÓN: Facade (Fachada)
 *
 * CLASE: Facade
 *
 * ROL EN EL PATRÓN:
 *  - Proporciona una interfaz unificada y simple a un conjunto de subsistemas complejos.
 *  - El cliente interactúa solo con la fachada, no con los subsistemas internos.
 *
 * PROPÓSITO:
 *  Simplificar la interacción con sistemas complejos, ocultando su implementación.
 *
 * CRITERIOS DE USO:
 *  ✅ Cuando se tiene un sistema con múltiples clases o módulos interdependientes.
 *  ✅ Cuando se desea reducir el acoplamiento entre cliente y subsistemas.
 *  ✅ Cuando se implementa una capa API o punto de entrada único.
 *
 * VENTAJAS:
 *  + Oculta la complejidad del sistema.
 *  + Promueve un bajo acoplamiento.
 *  + Facilita la evolución o sustitución interna del sistema.
 *
 * DESVENTAJAS:
 *  - Si se abusa, puede convertirse en un "monolito" que oculte demasiado.
 *
 * EJEMPLO EN BACKEND:
 *  - Un "Service Layer" que expone métodos simples para interactuar con múltiples repositorios.
 *
 * EJEMPLO EN PIZZERÍA ONLINE:
 *  - Una fachada que gestiona la creación de pedidos: comunicación con cocina y con el sistema de facturación.
 *
 * USO EN CLASE CLIENTE:
 *  Facade fachada = new Facade();
 *  fachada.realizarPedido("Pizza Napolitana");
 *
 * SALIDA ESPERADA:
 *  [Subsistema A] Validando pedido: Pizza Napolitana
 *  [Subsistema B] Preparando Pizza Napolitana
 *  [Facade] Pedido completado con éxito.
 */
public class Facade {

    private final SubsystemA subsistemaA;
    private final SubsystemB subsistemaB;

    public Facade() {
        this.subsistemaA = new SubsystemA();
        this.subsistemaB = new SubsystemB();
    }

    public void realizarPedido(String pedido) {
        subsistemaA.validarPedido(pedido);
        subsistemaB.prepararPedido(pedido);
        System.out.println("[Facade] Pedido completado con éxito.");
    }
}
