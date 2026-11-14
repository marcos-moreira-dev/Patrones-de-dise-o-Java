# Abstract Factory — Dos ambientes liminales

![Diagrama de Abstract Factory: fábricas de conjuntos coherentes](../../uml/abstractfactory.svg)

**Escena:** al preparar un pasillo amarillo aparecen una luz fluorescente amarilla y una puerta beige. En las piscinas vacías aparecen un reflejo azul y una puerta blanca.

**Dominio:** [R-05, R-13 y R-14](../../../DOMINIO.md). El cambio de estética **no cambia las reglas universales de las puertas**; en este ejemplo sus representaciones comienzan CERRADAS.

**Problema:** mezclar luces del tema amarillo con puertas de piscinas puede romper la coherencia visual de una escena.

**Patrón:** `FabricaAmbiente` promete crear `LuzDelNivel` y `PuertaDelNivel`. `FabricaAmarilla` y `FabricaPiscinas` proporcionan familias compatibles.

**Java y UML:** el cliente solicita dos productos a través de **interfaces**. La clase `Escena` verifica que compartan el mismo `Tema`. No hay asociación de propiedad compuesta simplemente por conservar ambas referencias.

**Resultado de consola:**

~~~text
AMARILLO: fluorescente amarilla y puerta beige
PISCINAS: reflejo azul y puerta blanca
~~~

**Prueba:** se obtienen dos familias consistentes y se rechaza crear una escena mezclando sus productos.

**Cuándo no usarlo:** si solo hay un objeto que construir, puede bastar Factory Method o un constructor.

[Ver código y clase cliente](../../../src/main/java/liminal/creacionales/abstractfactory/ClienteAbstractFactory.java).
