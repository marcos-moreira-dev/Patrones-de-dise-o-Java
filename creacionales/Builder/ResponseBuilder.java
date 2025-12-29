
/**
 * PATRÓN: Builder
 *
 * CLASE: ResponseBuilder
 *
 * ROL EN EL PATRÓN:
 *  - Es el "constructor paso a paso" que sabe cómo ensamblar el objeto Response.
 *
 * PROPÓSITO:
 *  Proporcionar una forma controlada y fluida de construir objetos complejos.
 *
 * MECANISMO:
 *  - Cada método devuelve el propio Builder (fluidez).
 *  - El método final `build()` crea el objeto `Response`.
 *
 * CRITERIOS DE USO:
 *  ✅ Cuando el objeto tiene parámetros opcionales o dependencias.
 *  ✅ Cuando la construcción debe ser clara y flexible.
 *
 * VENTAJAS:
 *  + Facilita la lectura del código cliente.
 *  + Reduce el riesgo de errores de construcción.
 *
 * DESVENTAJAS:
 *  - Requiere más clases, aunque mejora la mantenibilidad.
 *
 * EJEMPLO EN BACKEND:
 *  Crear respuestas HTTP dinámicas:
 *    Response res = new ResponseBuilder()
 *        .statusCode(404)
 *        .message("Not Found")
 *        .body("{'error':'Pizza no encontrada'}")
 *        .build();
 *
 * EJEMPLO EN PIZZERÍA ONLINE:
 *  Construir una pizza personalizada paso a paso antes de confirmarla:
 *    Pizza p = new PizzaBuilder()
 *        .masa("gruesa")
 *        .ingrediente("pepperoni")
 *        .extraQueso(true)
 *        .build();
 *
 * USO EN CLASE CLIENTE:
 *  El cliente (por ejemplo, `Main`) usa el builder de esta manera:
 *    Response res = new ResponseBuilder()
 *        .statusCode(200)
 *        .message("OK")
 *        .body("{'pedido':'Pizza Margarita'}")
 *        .build();
 *    System.out.println(res);
 */
public class ResponseBuilder {
    int statusCode;
    String message;
    String body;

    public ResponseBuilder statusCode(int code) {
        this.statusCode = code;
        return this;
    }

    public ResponseBuilder message(String msg) {
        this.message = msg;
        return this;
    }

    public ResponseBuilder body(String body) {
        this.body = body;
        return this;
    }

    public Response build() {
        return new Response(this); // ✅ ahora compila, constructor agregado en Response.java
    }
}
