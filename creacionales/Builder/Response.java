
/**
 * PATRÓN: Builder
 *
 * CLASE: Response
 *
 * ROL EN EL PATRÓN:
 *  - Representa el "producto complejo" que se va a construir paso a paso.
 *  - En este caso, simula una respuesta HTTP (por ejemplo, de una API).
 *
 * PROPÓSITO:
 *  Separar la construcción de un objeto complejo (Response)
 *  de su representación final, para permitir una creación más legible y controlada.
 *
 * MECANISMO:
 *  - El objeto Response tiene un constructor privado que recibe un Builder.
 *  - El Builder asigna los valores necesarios y finalmente llama a build().
 *
 * CRITERIOS DE USO:
 *  ✅ Cuando un objeto tiene muchos parámetros opcionales.
 *  ✅ Cuando deseas mejorar la legibilidad y evitar constructores largos.
 *  ✅ Cuando necesitas construir objetos inmutables o consistentes.
 *
 * VENTAJAS:
 *  + Mayor legibilidad y control en la creación de objetos.
 *  + Permite añadir parámetros opcionales fácilmente.
 *  + Reduce errores de inicialización.
 *
 * DESVENTAJAS:
 *  - Requiere más código (una clase Builder adicional).
 *
 * EJEMPLO EN BACKEND:
 *  Se usa para construir respuestas HTTP o estructuras JSON.
 *  Ejemplo:
 *    Response r = new ResponseBuilder()
 *        .statusCode(200)
 *        .message("OK")
 *        .body("{'resultado':'éxito'}")
 *        .build();
 *
 * EJEMPLO EN PIZZERÍA ONLINE:
 *  Se puede usar para crear pedidos personalizados:
 *    Pedido pedido = new PedidoBuilder()
 *        .masa("fina")
 *        .salsa("tomate")
 *        .ingrediente("queso extra")
 *        .build();
 *
 * USO EN CLASE CLIENTE:
 *  Dentro de la clase Main:
 *    Response r = new ResponseBuilder()
 *        .statusCode(201)
 *        .message("Pedido creado con éxito")
 *        .body("{'pizza':'Margarita'}")
 *        .build();
 *    System.out.println(r);
 */
public class Response {
    private int statusCode;
    private String message;
    private String body;

    // 👇 Constructor requerido por el Builder (corrección del error)
    public Response(ResponseBuilder builder) {
        this.statusCode = builder.statusCode;
        this.message = builder.message;
        this.body = builder.body;
    }

    @Override
    public String toString() {
        return "Response [statusCode=" + statusCode + ", message=" + message + ", body=" + body + "]";
    }
}
