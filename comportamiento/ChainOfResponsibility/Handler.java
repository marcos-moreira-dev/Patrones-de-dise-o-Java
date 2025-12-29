package comportamiento.ChainOfResponsibility;

/**
 * PATRÓN: Chain of Responsibility (Cadena de Responsabilidad)
 *
 * INTERFAZ: Handler
 *
 * ROL EN EL PATRÓN:
 *  - Define la interfaz para manejar una solicitud y establecer el siguiente
 *    manejador (siguiente eslabón de la cadena).
 *
 * PROPÓSITO:
 *  Permitir que múltiples objetos manejen una solicitud sin acoplar al emisor
 *  con el receptor. Cada manejador decide si procesa o pasa la solicitud.
 *
 * CRITERIOS DE USO:
 *  ✅ Cuando varias clases pueden manejar una misma petición.
 *  ✅ Cuando el orden de procesamiento puede variar o extenderse dinámicamente.
 *  ✅ Cuando se quiere evitar una estructura condicional enorme (if-else o switch).
 *
 * VENTAJAS:
 *  + Desacopla emisores y receptores.
 *  + Facilita la adición de nuevos manejadores sin modificar los existentes.
 *
 * DESVENTAJAS:
 *  - Puede ser difícil depurar si la cadena es larga o mal configurada.
 *
 * EJEMPLO EN BACKEND:
 *  - Middleware de validación HTTP o procesamiento de peticiones REST.
 *
 * EJEMPLO EN PIZZERÍA ONLINE:
 *  - Flujo de pedido: Recepción → Cocina → Reparto.
 *
 * USO EN CLASE CLIENTE:
 *  Handler recepcion = new ConcreteHandlerA();
 *  Handler cocina = new ConcreteHandlerB();
 *  recepcion.setNext(cocina);
 *  recepcion.handleRequest("Preparar pizza");
 *
 * SALIDA ESPERADA:
 *  Recepción: verificando pedido...
 *  Cocina: preparando pizza.
 */
/*
 * NOTA CONCEPTUAL:
 *
 * Chain of Responsibility comparte similitudes con otros patrones:
 *  - Con Command: ambos encapsulan una acción, pero Chain permite delegar en múltiples receptores.
 *  - Con Mediator: Chain pasa la responsabilidad en secuencia, Mediator coordina centralmente.
 *  - Con Observer: ambos pueden notificar múltiples receptores, pero Chain es secuencial y Observer es reactivo.
 *
 * Estructuralmente, puede verse como una “lista enlazada de responsabilidades”,
 * ideal para validaciones encadenadas o procesamiento por etapas.
 */

public abstract class Handler {
    protected Handler next;

    public void setNext(Handler next) {
        this.next = next;
    }

    public abstract void handleRequest(String request);
}
