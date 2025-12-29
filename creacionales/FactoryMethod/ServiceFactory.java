

/**
 * PATRÓN: Factory Method
 *
 * PROPÓSITO:
 *  Definir una interfaz para crear objetos, pero permitir que las subclases
 *  decidan qué clase concreta instanciar.
 *  De esta forma, el código cliente trabaja con la interfaz general, sin
 *  conocer los detalles de creación ni las clases concretas.
 *
 * MOTIVACIÓN:
 *  En ingeniería de software, a menudo el código necesita crear objetos que
 *  pertenecen a una familia común (por ejemplo, distintos servicios de backend),
 *  pero no debe depender directamente de las clases concretas.  
 *  El patrón desacopla la lógica de creación de la lógica de uso.
 *
 * MECANISMO:
 *  - Una clase abstracta o interfaz define el método fábrica (`createService()`).
 *  - Las subclases implementan dicho método y deciden qué tipo de objeto crear.
 *  - El cliente solo usa la interfaz común (`Service`), no las clases concretas.
 *
 * CRITERIOS DE USO (CUÁNDO APLICARLO):
 *  ✅ Cuando el código necesita crear objetos sin acoplarse a clases concretas.
 *  ✅ Cuando una clase delega la responsabilidad de instanciación a sus subclases.
 *  ✅ Cuando la aplicación debe ser fácilmente extensible (agregar nuevos tipos
 *     sin modificar código existente).
 *  ✅ Cuando deseas centralizar la lógica de construcción de objetos de un tipo específico.
 *
 * ⚠️ Evitarlo cuando:
 *  - Solo existe un tipo de producto y no se prevé extensión futura.
 *  - La jerarquía de clases se vuelve innecesariamente compleja.
 *
 * VENTAJAS:
 *  + Desacopla la creación del uso de objetos.
 *  + Facilita la extensión: agregar nuevos productos no rompe el código existente.
 *  + Mejora la mantenibilidad en sistemas con múltiples variantes de objetos.
 *
 * DESVENTAJAS:
 *  - Introduce más clases (una por producto concreto).
 *  - Puede añadir complejidad si se abusa del patrón.
 *
 * EJEMPLO EN BACKEND:
 *  - Una fábrica que crea distintos tipos de servicios REST según la entidad:
 *      UserServiceFactory, PaymentServiceFactory, ProductServiceFactory, etc.
 *  - Cada fábrica produce un servicio concreto, pero el controlador trabaja
 *    siempre con la interfaz común `Service`.
 *
 * EJEMPLO EN PIZZERÍA ONLINE:
 *  - Una `PizzeriaFactory` podría crear distintos tipos de pizza (`PizzaMargarita`,
 *    `PizzaPepperoni`) según el pedido. El sistema no necesita conocer las clases concretas.
 *
 * USO:
 *  ServiceFactory userFactory = new UserServiceFactory();
 *  Service userService = userFactory.createService();
 *  userService.execute();
 *
 *  ServiceFactory paymentFactory = new PaymentServiceFactory();
 *  Service paymentService = paymentFactory.createService();
 *  paymentService.execute();
 */

 /**
 * CLASE ABSTRACTA: ServiceFactory
 *
 * ROL EN EL PATRÓN:
 *  Define el método fábrica (`createService()`) que las subclases concretas
 *  implementarán para crear los objetos `Service` específicos.
 *
 * PROPÓSITO:
 *  Centralizar la creación de objetos y delegar la decisión de qué clase
 *  concreta instanciar a las subclases.
 *
 * CRITERIOS DE USO:
 *  - Cuando se requiere una jerarquía de fábricas para distintas variantes de producto.
 *  - Cuando se desea ocultar los detalles de creación del cliente.
 *
 * EJEMPLO EN BACKEND:
 *  Controladores o routers que crean servicios sin conocer las clases concretas.
 *
 * EJEMPLO EN PIZZERÍA ONLINE:
 *  Una `PizzeriaFactory` podría crear distintos tipos de pizza (`PizzaMargarita`,
 *  `PizzaPepperoni`) según el pedido.
 */
public abstract class ServiceFactory {
    public abstract Service createService();

    public void runService() {
        Service service = createService();
        service.execute();
    }
}
