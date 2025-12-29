package estructurales.Flyweight;
/**
 * PATRÓN: Flyweight (Peso Ligero)
 *
 * INTERFAZ: Flyweight
 *
 * ROL EN EL PATRÓN:
 *  - Define la interfaz común que comparten los objetos "livianos" reutilizables.
 *  - Permite separar el estado intrínseco (compartido) del extrínseco (único por cliente).
 *
 * PROPÓSITO:
 *  Minimizar el uso de memoria compartiendo datos entre múltiples objetos similares.
 *
 * CRITERIOS DE USO:
 *  ✅ Cuando hay un número enorme de objetos que comparten la mayoría de sus datos.
 *  ✅ Cuando se quiere optimizar la memoria o el rendimiento.
 *  ✅ Cuando el estado puede dividirse entre intrínseco (compartido) y extrínseco (único).
 *
 * VENTAJAS:
 *  + Reducción significativa de uso de memoria.
 *  + Separación clara entre datos comunes y específicos.
 *
 * DESVENTAJAS:
 *  - Mayor complejidad en gestión de objetos.
 *  - Menor encapsulamiento (parte del estado pasa a parámetros externos).
 *
 * EJEMPLO EN BACKEND:
 *  - Cache de objetos de configuración o conexiones (como pool de conexiones).
 *
 * EJEMPLO EN PIZZERÍA ONLINE:
 *  - Todos los pedidos que comparten el mismo tipo de pizza reutilizan la misma receta.
 *
 * USO EN CLASE CLIENTE:
 *  Flyweight pizza = factory.getFlyweight("Margarita");
 *  pizza.servir("Mesa 5");
 *
 * SALIDA ESPERADA:
 *  Preparando pizza tipo Margarita para Mesa 5
 */
public interface Flyweight {
    void servir(String mesa); // Estado extrínseco
}
