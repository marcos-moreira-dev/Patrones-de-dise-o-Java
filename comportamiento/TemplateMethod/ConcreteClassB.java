

/**
 * CLASE CONCRETA: ConcreteClassB
 *
 * ROL EN EL PATRÓN:
 *  - Implementa los pasos específicos del algoritmo definidos en AbstractClass.
 *  - Representa una variación distinta en el flujo general.
 *
 * PROPÓSITO:
 *  Mostrar cómo una subclase puede alterar solo algunos pasos del proceso
 *  sin cambiar el orden definido en la plantilla.
 *
 * CRITERIOS DE USO:
 *  - Cuando distintos objetos deben ejecutar el mismo proceso general con variaciones internas.
 *
 * EJEMPLO EN BACKEND:
 *  - Procesar una solicitud “Actualizar pedido”.
 *
 * EJEMPLO EN PIZZERÍA ONLINE:
 *  - Preparar pizza “Hawaiana” (misma estructura general, diferentes ingredientes).
 *
 * USO EN CLASE CLIENTE:
 *  AbstractClass procesoB = new ConcreteClassB();
 *  procesoB.templateMethod();
 *
 * SALIDA ESPERADA:
 *  Iniciando proceso base...
 *  Ejecutando paso 1 de B
 *  Ejecutando paso 2 de B
 *  Finalizando proceso base.
 */
public class ConcreteClassB extends AbstractClass {

    @Override
    protected void stepOne() {
        System.out.println("Ejecutando paso 1 de B");
    }

    @Override
    protected void stepTwo() {
        System.out.println("Ejecutando paso 2 de B");
    }
}
