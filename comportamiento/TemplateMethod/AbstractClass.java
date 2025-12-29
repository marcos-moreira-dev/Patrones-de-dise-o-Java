package comportamiento.TemplateMethod;

/**
 * PATRÓN: Template Method
 *
 * CLASE ABSTRACTA: AbstractClass
 *
 * ROL EN EL PATRÓN:
 *  - Define el esqueleto de un algoritmo (método plantilla) en un método final.
 *  - Delega algunos pasos a las subclases sin cambiar la estructura general.
 *
 * PROPÓSITO:
 *  Reutilizar la estructura de un algoritmo permitiendo a las subclases redefinir
 *  ciertos pasos específicos.
 *
 * MECANISMO:
 *  - El método plantilla (templateMethod) marca el orden fijo de ejecución.
 *  - Las subclases concretas implementan los pasos específicos.
 *
 * CRITERIOS DE USO:
 *  ✅ Cuando varias clases comparten una secuencia común de pasos, pero difieren en algunos detalles.
 *  ✅ Cuando se busca mantener el principio de inversión de control: el flujo general está definido en la superclase.
 *  ✅ Cuando se desea extender el comportamiento sin duplicar código.
 *
 * ⚠️ Evitarlo cuando:
 *  - Las variaciones entre subclases son demasiado grandes (violará la plantilla).
 *
 * VENTAJAS:
 *  + Promueve reutilización y consistencia.
 *  + Facilita la extensión sin alterar la estructura global.
 *
 * DESVENTAJAS:
 *  - El acoplamiento con la clase base puede crecer.
 *
 * EJEMPLO EN BACKEND:
 *  - Flujo de procesamiento de solicitudes HTTP: validación → ejecución → respuesta.
 *
 * EJEMPLO EN PIZZERÍA ONLINE:
 *  - Preparación de una pizza: preparar masa → agregar ingredientes → hornear → empacar.
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
public abstract class AbstractClass {

    // Método plantilla: define la secuencia del algoritmo
    public final void templateMethod() {
        System.out.println("Iniciando proceso base...");
        stepOne();
        stepTwo();
        System.out.println("Finalizando proceso base.");
    }

    // Pasos que las subclases deben implementar
    protected abstract void stepOne();
    protected abstract void stepTwo();
}
