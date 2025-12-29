package comportamiento.Strategy;

/**
 * CLASE CONCRETA: ConcreteStrategyB
 *
 * ROL EN EL PATRÓN:
 *  - Implementa la interfaz `Strategy`.
 *  - Define otra variación del algoritmo.
 *
 * PROPÓSITO:
 *  Proporcionar un comportamiento alternativo intercambiable en tiempo de ejecución.
 *
 * EJEMPLO EN BACKEND:
 *  - Estrategia para aplicar impuestos según la región del cliente.
 *
 * EJEMPLO EN PIZZERÍA ONLINE:
 *  - Aplicar 5% de descuento para nuevos clientes.
 *
 * USO EN CLASE CLIENTE:
 *  Context context = new Context(new ConcreteStrategyB());
 *  context.executeStrategy();
 *
 * SALIDA ESPERADA:
 *  Ejecutando estrategia B: aplicando descuento del 5%.
 */
public class ConcreteStrategyB implements Strategy {
    @Override
    public void execute() {
        System.out.println("Ejecutando estrategia B: aplicando descuento del 5%.");
    }
}
