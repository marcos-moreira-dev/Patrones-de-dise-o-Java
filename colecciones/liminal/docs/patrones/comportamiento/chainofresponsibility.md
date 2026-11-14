# Chain of Responsibility — ¿Se puede cruzar?

![UML de la cadena de validaciones](../../uml/chainofresponsibility.svg)

**Escena.** Frente a una puerta del pasillo amarillo, el explorador intenta pasar de H-01 a H-02. Primero se comprueba que el destino exista; después, que haya una salida dirigida y que la puerta esté abierta.

**Dominio:** [R-02, R-04, R-05 y R-12](../../../DOMINIO.md). Esta demostración **valida la solicitud**; no cambia la ubicación de un explorador.

**Patrón:** `ValidadorCruce` contiene una referencia a su `siguiente` validador. `VerificarDestino`, `VerificarSalida` y `VerificarPuerta` pueden **rechazar** o pasar la responsabilidad al siguiente. El cliente construye la cadena.

**Qué se aprende en Java y UML:** cada validador es un objeto distinto que hereda de la clase abstracta. Pasar una solicitud no crea ni copia sus habitaciones. La asociación con el siguiente es una referencia; no se deduce composición UML.

**Consola:**
~~~text
Puerta cerrada -> PUERTA_CERRADA
Puerta abierta -> PERMITIDO
~~~

**Prueba:** destino inexistente, salida ausente y puerta cerrada se detectan en la etapa correspondiente. La solicitud es inmutable.

**Cuándo no usarlo:** con una única validación fija, una sola condición resulta más fácil.

[Ver código y cliente](../../../src/main/java/liminal/comportamiento/chainofresponsibility/ClienteChainOfResponsibility.java).
