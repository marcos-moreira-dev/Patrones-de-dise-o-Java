package comportamiento.Visitor;

/**
 * PATRÓN: Visitor
 *
 * INTERFAZ: Visitor
 *
 * ROL EN EL PATRÓN:
 *  - Define la interfaz que declara una operación "visitar" para cada tipo concreto
 *    de elemento que pueda existir en la jerarquía.
 *  - Separa la lógica de procesamiento de los objetos que se visitan.
 *
 * PROPÓSITO:
 *  Permitir agregar nuevas operaciones a una estructura de clases
 *  sin modificar las clases de los elementos sobre los cuales se opera.
 *
 * MECANISMO:
 *  - Cada elemento acepta un Visitor.
 *  - El Visitor ejecuta una operación concreta sobre el elemento.
 *  - Esto permite centralizar comportamientos externos sin alterar los modelos.
 *
 * CRITERIOS DE USO:
 *  ✅ Cuando necesitas agregar operaciones sobre una jerarquía de clases estable.
 *  ✅ Cuando quieres mantener el principio de abierto/cerrado (añadir sin modificar).
 *  ✅ Cuando las operaciones deben variar independientemente de los elementos.
 *
 * ⚠️ Evitarlo cuando:
 *  - La jerarquía de clases cambia con frecuencia (requiere actualizar todos los visitantes).
 *
 * VENTAJAS:
 *  + Permite añadir operaciones sin modificar las clases base.
 *  + Mantiene separadas las responsabilidades.
 *
 * DESVENTAJAS:
 *  - Requiere duplicar métodos "visit" por cada tipo de elemento.
 *
 * EJEMPLO EN BACKEND:
 *  - Analizar una estructura de objetos JSON/XML o nodos de una API para aplicar operaciones.
 *
 * EJEMPLO EN PIZZERÍA ONLINE:
 *  - Permitir que distintos “visitantes” recorran los pedidos:
 *    un visitante genera un reporte de ventas, otro calcula descuentos.
 *
 * USO EN CLASE CLIENTE:
 *  Visitor visitor = new ConcreteVisitor();
 *  Element a = new ConcreteElementA();
 *  Element b = new ConcreteElementB();
 *  a.accept(visitor);
 *  b.accept(visitor);
 * 
 *  Salida esperada:
 *  Visitando A → Elemento A procesado.
    Visitando B → Elemento B procesado.
 */
public interface Visitor {
    void visitConcreteElementA(ConcreteElementA elementA);
    void visitConcreteElementB(ConcreteElementB elementB);
}
