

/**
 * CLASE: Context
 *
 * ROL EN EL PATRÓN:
 *  - Mantiene una referencia a una instancia de `Strategy`.
 *  - Permite cambiar la estrategia durante la ejecución sin alterar la lógica del cliente.
 *
 * PROPÓSITO:
 *  Delegar la ejecución de un comportamiento específico a la estrategia seleccionada.
 *
 * CRITERIOS DE USO:
 *  ✅ Cuando el comportamiento puede variar según condiciones externas.
 *  ✅ Cuando el cliente no debe conocer los detalles del algoritmo.
 *
 * EJEMPLO EN BACKEND:
 *  - Seleccionar dinámicamente una estrategia de pago o persistencia.
 *
 * EJEMPLO EN PIZZERÍA ONLINE:
 *  - Cambiar la estrategia de descuento según el tipo de cliente.
 *
 * USO EN CLASE CLIENTE:
 *  Context context = new Context(new ConcreteStrategyA());
 *  context.executeStrategy();
 *
 *  context.setStrategy(new ConcreteStrategyB());
 *  context.executeStrategy();
 *
 * SALIDA ESPERADA:
 *  Ejecutando estrategia A: aplicando descuento del 10%.
 *  Ejecutando estrategia B: aplicando descuento del 5%.
 */
public class Context {
    private Strategy strategy;

    public Context(Strategy strategy) {
        this.strategy = strategy;
    }

    public void setStrategy(Strategy strategy) {
        this.strategy = strategy;
    }

    public void executeStrategy() {
        strategy.execute();
    }
}
