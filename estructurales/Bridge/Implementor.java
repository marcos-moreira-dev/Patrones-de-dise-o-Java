

/**
 * PATRÓN: Bridge (Puente)
 *
 * INTERFAZ: Implementor
 *
 * ROL EN EL PATRÓN:
 *  - Define la interfaz común para todas las implementaciones concretas.
 *  - Se usa por la clase Abstraction para delegar el trabajo real.
 *
 * PROPÓSITO:
 *  Desacoplar la abstracción (interfaz de alto nivel) de su implementación
 *  para que ambas puedan variar de forma independiente.
 *
 * CRITERIOS DE USO:
 *  ✅ Cuando deseas evitar el acoplamiento fuerte entre una abstracción y su implementación.
 *  ✅ Cuando tienes múltiples dimensiones de variación (por ejemplo, tipo y plataforma).
 *  ✅ Cuando una clase podría tener múltiples combinaciones de comportamiento.
 *
 * VENTAJAS:
 *  + Permite extender implementaciones sin modificar las abstracciones.
 *  + Evita herencias múltiples y combinaciones explosivas.
 *
 * DESVENTAJAS:
 *  - Incrementa la complejidad inicial del diseño.
 *
 * EJEMPLO EN BACKEND:
 *  - Una API que puede trabajar con diferentes proveedores de servicios (AWS, Azure, GCP).
 *
 * EJEMPLO EN PIZZERÍA ONLINE:
 *  - La “Plataforma de Pedidos” (abstracción) puede usar diferentes
 *    sistemas de cocina (implementaciones): tradicional o automatizada.
 *
 * USO EN CLASE CLIENTE:
 *  Implementor cocina = new ConcreteImplementorA();
 *  Abstraction sistema = new RefinedAbstraction(cocina);
 *  sistema.realizarOperacion();
 *
 * SALIDA ESPERADA:
 *  [Sistema de pedidos] Enviando orden al sistema de cocina...
 *  [Cocina Tradicional] Preparando pedido manualmente.
 */
public interface Implementor {
    void operationImpl();
}
