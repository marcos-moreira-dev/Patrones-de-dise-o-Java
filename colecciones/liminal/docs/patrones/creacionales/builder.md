# Builder — Construir una habitación paso a paso

![Pasillo amarillo con alfombra, paredes repetidas y luces fluorescentes](../../../assets/ambientes/pasillo-amarillo.webp)

*Referencia ambiental de esta escena. El contrato válido se define en [DOMINIO.md](../../../DOMINIO.md), no en la imagen.*

![Diagrama de Builder: constructor fluido y objeto resultante](../../uml/builder.svg)

**Escena:** preparas una habitación del pasillo amarillo con cuatro luces y una puerta. Primero seleccionas el ID, después el ambiente, las luces y la existencia de una puerta.

**Dominio:** [R-01, R-12 y R-13](../../../DOMINIO.md). `R-LOCAL-BUILDER-01`: una habitación creada en esta demo declara un ambiente, un ID y una cantidad de luces no negativa. La identidad debe ser no vacía. La unicidad dentro de un mapa corresponde al mapa, no a Builder.

**Problema:** constructores con muchos parámetros posicionales son difíciles de leer cuando se describen ambientes visuales.

**Patrón:** `ConstructorHabitacion` guarda temporalmente los valores y produce un objeto `Habitacion` validado en `construir()`. **Builder necesita esencialmente dos roles:** producto y constructor paso a paso. La demostración **no necesita** un mapa ni un registro de IDs: eso es responsabilidad de otro ejemplo de dominio.

**Dónde está cada tipo en GitHub:** ahora cada uno tiene un archivo independiente:

- [`ClienteBuilder.java`](../../../src/main/java/liminal/creacionales/builder/ClienteBuilder.java) — indica el recorrido con los métodos `construirHabitacion()` y `mostrarHabitacion()`.
- [`ConstructorHabitacion.java`](../../../src/main/java/liminal/creacionales/builder/ConstructorHabitacion.java) — configura paso a paso.
- [`Habitacion.java`](../../../src/main/java/liminal/creacionales/builder/Habitacion.java) — producto inmutable.
- [`PruebaBuilder.java`](../../../src/test/java/liminal/creacionales/builder/PruebaBuilder.java) — pruebas separadas del cliente.

**Java y UML:** el constructor y el producto son **objetos distintos**. El hecho de crear un objeto en `construir()` no prueba por sí solo propiedad compuesta. El producto exige un ID no vacío, pero no asigna unicidad global en un mapa externo.

**Resultado de consola:**

~~~text
Habitación: H-01
Ambiente: Pasillo amarillo
Luces: 4
Puerta: sí
~~~

**Prueba:** se rechazan la configuración incompleta, los IDs vacíos y las luces negativas. La duplicación de IDs en un mapa se verifica en otros ejemplos, no en Builder.

**Cuándo no usarlo:** para un `record` de dos campos obligatorios, su constructor puede ser suficiente.

[Abrir la carpeta con todas las clases](../../../src/main/java/liminal/creacionales/builder/).
