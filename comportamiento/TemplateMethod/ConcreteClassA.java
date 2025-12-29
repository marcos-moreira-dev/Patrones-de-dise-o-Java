package comportamiento.TemplateMethod;

/**
 * CLASE CONCRETA: ConcreteClassA
 *
 * ROL EN EL PATRÓN:
 *  - Implementa los pasos específicos del algoritmo definidos en AbstractClass.
 *
 * PROPÓSITO:
 *  Personalizar la ejecución de cada paso de la plantilla para un caso concreto.
 *
 * CRITERIOS DE USO:
 *  - Cuando se desea una implementación específica de los pasos definidos en la plantilla.
 *
 * EJEMPLO EN BACKEND:
 *  - Procesar una solicitud “Crear usuario”.
 *
 * EJEMPLO EN PIZZERÍA ONLINE:
 *  - Preparar pizza de tipo “Margarita”.
 *
 * USO EN CLASE CLIENTE:
 *  AbstractClass procesoA = new ConcreteClassA();
 *  procesoA.templateMethod();
 *
 * SALIDA ESPERADA:
 *  Iniciando proceso base...
 *  Ejecutando paso 1 de A
 *  Ejecutando paso 2 de A
 *  Finalizando proceso base.
 */
public class ConcreteClassA extends AbstractClass {

    @Override
    protected void stepOne() {
        System.out.println("Ejecutando paso 1 de A");
    }

    @Override
    protected void stepTwo() {
        System.out.println("Ejecutando paso 2 de A");
    }
}
