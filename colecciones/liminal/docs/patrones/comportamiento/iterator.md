# Iterator — Recorrer habitaciones

![UML de Iterator: colección, iterador y habitación](../../uml/iterator.svg)

**Escena.** El explorador quiere leer todas las habitaciones de su mapa: H-01, pasillo amarillo, y H-02, piscina vacía.

**Dominio:** [R-01 y R-02](../../../DOMINIO.md). No se permite registrar dos habitaciones con el mismo ID dentro del mapa.

**Patrón:** `RegistroHabitaciones` implementa el contrato estándar de Java `Iterable<Habitacion>`. `RecorridoHabitaciones` implementa `Iterator<Habitacion>` con `hasNext()` y `next()`.

**Qué sucede en Java:** el iterador recibe una **instantánea** de las referencias a habitaciones, no una copia de cada habitación; no expone una lista modificable del registro. En Java 21, `Iterator` e `Iterable` ya proporcionan estos contratos estándar.

**Consola:**
~~~text
H-01: Pasillo amarillo
H-02: Piscina vacía
~~~

**Pruebas:** fin de recorrido, estabilidad de la instantánea e ID repetido. Al añadir H-02 después de crear el iterador inicial, este sigue recorriendo solo H-01.

**Cuándo no usarlo:** no se recomienda implementar un iterador manual si basta con usar las colecciones e iteradores existentes.

[Ver código y cliente](../../../src/main/java/liminal/comportamiento/iterator/ClienteIterator.java).
