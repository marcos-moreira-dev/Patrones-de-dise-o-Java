

/**
 * CLASE CONCRETA: ConcreteStrategyA
 *
 * ROL EN EL PATRÓN:
 *  - Implementa la interfaz `Strategy`.
 *  - Define una variación específica del algoritmo.
 *
 * PROPÓSITO:
 *  Encapsular un comportamiento particular que el Contexto puede usar.
 *
 * EJEMPLO EN BACKEND:
 *  - Estrategia para manejar descuentos en pedidos grandes.
 *
 * EJEMPLO EN PIZZERÍA ONLINE:
 *  - Aplicar 10% de descuento para clientes frecuentes.
 *
 * USO EN CLASE CLIENTE:
 *  Context context = new Context(new ConcreteStrategyA());
 *  context.executeStrategy();
 *
 * SALIDA ESPERADA:
 *  Ejecutando estrategia A: aplicando descuento del 10%.
 */
public class ConcreteStrategyA implements Strategy {
    @Override
    public void execute() {
        System.out.println("Ejecutando estrategia A: aplicando descuento del 10%.");
    }
}
