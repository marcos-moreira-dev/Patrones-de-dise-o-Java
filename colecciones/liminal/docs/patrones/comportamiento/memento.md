# Memento — Recordar cómo estaba la linterna

![Piscinas interiores de agua turquesa y columnas](../../../assets/ambientes/piscinas-vacias.webp)

*Referencia ambiental, no regla del mundo. Las invariantes están en [DOMINIO.md](../../../DOMINIO.md).*

![UML de Memento: linterna, recuerdo e historial](../../uml/memento.svg)

**Escena.** Una linterna empieza con intensidad 4 y sin filtro azul. Cambias la intensidad a 8 y colocas el filtro. Después restauras la configuración anterior.

**Dominio:** [R-06, R-12 y R-14](../../../DOMINIO.md). Regla local: intensidad entera entre 0 y 10; la restauración cambia **configuración**, no ubicación física del artefacto.

**Patrón:** `Linterna` (originador) crea `Recuerdo`, una clase anidada inmutable con campos privados. `Historial` (cuidador) almacena esas instantáneas y las entrega nuevamente a **la misma instancia** de `Linterna` para restaurar. Al guardar la primera instantánea, el historial queda asociado con ese originador. No es un inventario físico ni duplica el artefacto.

**R-LOCAL-MEMENTO-01:** un historial no mezcla instantáneas de distintas linternas. Guardar o deshacer con otra instancia se rechaza antes de modificar la linterna o consumir la instantánea. El historial conserva referencias opacas al recuerdo y una referencia al originador; **esa asociación no es propiedad exclusiva ni composición UML**.

**Java y UML:** el historial conserva una referencia al recuerdo, pero no conoce sus campos internos. La creación de `Recuerdo` dentro de `Linterna` no demuestra por sí sola composición UML.

**Consola:**
~~~text
Ajuste nuevo: intensidad 8, filtro azul true
Ajuste restaurado: intensidad 4, filtro azul false
~~~

**Pruebas:** se recupera el valor anterior; una intensidad inválida y deshacer sin historial se rechazan sin cambiar el estado. También se comprueba que intentar restaurar la linterna B desde el historial de la A no modifique ninguna y que el recuerdo siga disponible para A.

**Cuándo no usarlo:** si no existe posibilidad de deshacer ni conservar instantáneas, el patrón es innecesario.

[Ver código y cliente](../../../src/main/java/liminal/comportamiento/memento/ClienteMemento.java).

**Explorar los archivos por separado:** [clases Java](../../../src/main/java/liminal/comportamiento/memento/) · [PruebaMemento.java](../../../src/test/java/liminal/comportamiento/memento/PruebaMemento.java) · [guía de lectura del código](../../../GUIA_LECTURA_CODIGO.md).

## Otras implementaciones (sin código extra)

**Instantánea completa o incremental:** guardar todo el estado es fácil de razonar; registrar solo cambios puede ahorrar recursos, pero exige una política de restauración más rigurosa. El cuidador no debe conocer ni modificar el estado interno protegido.
