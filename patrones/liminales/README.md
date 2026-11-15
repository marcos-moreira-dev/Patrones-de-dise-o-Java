# Expedición Liminal — ruta visual de los 23 patrones GoF

![Pasillo amarillo vacío con luces fluorescentes y arquitectura repetitiva](assets/ambientes/pasillo-amarillo.webp)

*Una expedición por pasillos, piscinas y oficinas imposibles. Lee la escena primero; después descubre cómo funciona el patrón en Java.*

**Estado: catálogo Java 21 completo (23/23).** Todos los patrones cuentan con una clase cliente ejecutable por consola, una prueba autónoma, un diagrama UML SVG y una ficha Markdown. La colección incluye **tres escenarios originales y una lámina de cuatro objetos y criatura**; las secuencias didácticas de **State, Strategy y Observer** también están ilustradas en SVG.

La colección técnica original y los microproyectos Arena Java y MiniSO **se conservan intactos en su organización**.

## Una mirada al mundo

| Piscinas vacías | Oficinas oníricas |
| --- | --- |
| ![Piscina de azulejos y agua turquesa iluminada artificialmente](assets/ambientes/piscinas-vacias.webp) | ![Oficina vacía de alfombra rosa, luces artificiales y muebles](assets/ambientes/oficinas-oniricas.webp) |

**[Abrir el atlas visual](GUIA_VISUAL.md)**: las tres ambientaciones y cuatro objetos conectados con las clases y las reglas R-XX del dominio. Consulta también la [procedencia de las ilustraciones](assets/README.md).

**[Guía para leer las clases a mano](GUIA_LECTURA_CODIGO.md)**: compara Builder con el ejemplo técnico, presenta las carpetas con un tipo por archivo y explica por dónde comenzar en los clientes y las pruebas.

**[Variantes GoF y laboratorio class/record](PLAN_VARIANTES.md)**: **siete variantes implementadas** en seis patrones y un laboratorio independiente sobre valores e identidad. Los **23 ejercicios principales** conservan sus clientes y su menú.

**[Integrador Expedición Liminal — cuatro guiones](../../proyectos/expedicion-liminal-integrada/README.md)**: tercera ruta **ya implementada** en Java 21, con dominio propio, Strategy, Observer y Mediator, pruebas y UML. El [plan y la alineación de los 23 ejemplos](PLAN_INTEGRADOR.md) conservan los criterios y la historia de su desarrollo. **Los ejemplos aislados no se fusionaron con el integrador.**

**[Matriz de alineación de los 23 patrones](MATRIZ_ALINEACION.md)**: escenas, reglas R-XX, decisiones de diseño y 17 puntos de comentarios de ingeniería realmente presentes en Java; no convierte los ejemplos en un único programa.

**[Cierre académico de los tres bloques](../../docs/CIERRE_ACADEMICO_GOF.md)**: estado, ruta de lectura y límites de revisión. **[Auditoría pedagógica y semántica](AUDITORIA_PEDAGOGICA.md)**: correcciones, pruebas y mejoras opcionales. **[Auditoría del andamiaje técnico](AUDITORIA_ANDAMIAJE.md)**: distingue el dominio, las clases GoF, los clientes de estudio y las herramientas de verificación.

## Laboratorio adicional de relaciones UML

**[Asociación, agregación y composición: guía con dos diagramas](docs/laboratorios/relaciones-uml.md)**. Este ejercicio es **independiente de los 23 patrones GoF**: demuestra con referencias Java cómo un explorador puede estar en dos equipos y cómo un registro posee exclusivamente sus entradas activas (R-09).

Se ejecuta por consola, se prueba automáticamente y no requiere JavaFX.

## Empieza aquí

1. [Cómo se define un dominio](../../docs/modelado/COMO_DEFINIR_UN_DOMINIO.md): supuestos, invariantes, restricciones, precondiciones, postcondiciones y contraejemplos.
2. [DOMINIO.md](DOMINIO.md): reglas verificables del mundo Expedición Liminal, que prevalecen sobre cualquier patrón.
3. [PLAN.md](PLAN.md): decisiones editoriales, estructura y trazabilidad del catálogo.
4. [UML_GUIDE.md](../../UML_GUIDE.md): clases, referencias, asociaciones, dependencia, agregación, composición y multiplicidades.
5. Escoge una ficha de la tabla siguiente. Mira primero la escena y el UML, luego el cliente Java y finalmente la prueba.

## Catálogo completo y enlaces de estudio

| Categoría | Patrón | Ficha con UML | Nombre para ejecutar |
| --- | --- | --- | --- |
| Creacionales | Singleton | [Panel de avisos](docs/patrones/creacionales/singleton.md) | `singleton` |
| Creacionales | Factory Method | [Apariciones](docs/patrones/creacionales/factorymethod.md) | `factorymethod` |
| Creacionales | Abstract Factory | [Dos ambientes](docs/patrones/creacionales/abstractfactory.md) | `abstractfactory` |
| Creacionales | Builder | [Construir una habitación](docs/patrones/creacionales/builder.md) | `builder` |
| Creacionales | Prototype | [Copiar una plantilla](docs/patrones/creacionales/prototype.md) | `prototype` |
| Estructurales | Adapter | [Radio antigua](docs/patrones/estructurales/adapter.md) | `adapter` |
| Estructurales | Bridge | [Alertas y canales](docs/patrones/estructurales/bridge.md) | `bridge` |
| Estructurales | Composite | [Grupos de luces](docs/patrones/estructurales/composite.md) | `composite` |
| Estructurales | Decorator | [Linterna con mejoras](docs/patrones/estructurales/decorator.md) | `decorator` |
| Estructurales | Facade | [Cruzar un pasillo](docs/patrones/estructurales/facade.md) | `facade` |
| Estructurales | Flyweight | [Texturas compartidas](docs/patrones/estructurales/flyweight.md) | `flyweight` |
| Estructurales | Proxy | [Cámara vigilada](docs/patrones/estructurales/proxy.md) | `proxy` |
| Comportamiento | Chain of Responsibility | [Validación en cadena](docs/patrones/comportamiento/chainofresponsibility.md) | `chainofresponsibility` |
| Comportamiento | Command | [Panel de botones](docs/patrones/comportamiento/command.md) | `command` |
| Comportamiento | Interpreter | [Lenguaje de instrucciones](docs/patrones/comportamiento/interpreter.md) | `interpreter` |
| Comportamiento | Iterator | [Recorrer habitaciones](docs/patrones/comportamiento/iterator.md) | `iterator` |
| Comportamiento | Mediator | [Central de control](docs/patrones/comportamiento/mediator.md) | `mediator` |
| Comportamiento | Memento | [Recuerdo de linterna](docs/patrones/comportamiento/memento.md) | `memento` |
| Comportamiento | Observer | [Interruptor y avisos](docs/patrones/comportamiento/observer.md) | `observer` |
| Comportamiento | State | [Puerta bloqueada](docs/patrones/comportamiento/state.md) | `state` |
| Comportamiento | Strategy | [Monstruo que se mueve](docs/patrones/comportamiento/strategy.md) | `strategy` |
| Comportamiento | Template Method | [Rutas de exploración](docs/patrones/comportamiento/templatemethod.md) | `templatemethod` |
| Comportamiento | Visitor | [Inspector de objetos](docs/patrones/comportamiento/visitor.md) | `visitor` |

## Cómo está organizado el código

La colección sigue el estilo de los ejemplos técnicos: **un tipo Java superior por archivo**. Las clases cliente solo demuestran; las comprobaciones se guardan en `src/test/java`. El lanzador [`DemoLiminal.java`](src/main/java/liminal/DemoLiminal.java) agrupa los métodos por categorías y permite escoger un patrón individual.

Para ver los cambios directamente: [carpeta de Builder](src/main/java/liminal/creacionales/builder/) · [guía de lectura de clases](GUIA_LECTURA_CODIGO.md).

## Ejecutar en Java 21

Sin JavaFX, Maven, redes ni frameworks. Desde la **raíz del repositorio**:

En Git Bash o Linux:

~~~bash
bash patrones/liminales/scripts/verificar.sh
java -cp patrones/liminales/out liminal.DemoLiminal todos
java -cp patrones/liminales/out liminal.DemoLiminal creacionales
java -cp patrones/liminales/out liminal.DemoLiminal strategy
java -cp patrones/liminales/out liminal.DemoLiminal visitor
~~~

En PowerShell:

~~~powershell
powershell -ExecutionPolicy Bypass -File .\colecciones\liminal\scripts\verificar.ps1
java -cp patrones/liminales/out liminal.DemoLiminal todos
~~~

Los scripts compilan código y pruebas, ejecutan las **23 verificaciones** y muestran las demostraciones por consola. También se ejecutan en la [verificación principal](../../scripts/verificar.sh) y en GitHub Actions, sin intervención del usuario.

## Regla editorial de los ejemplos

**Escena sencilla → reglas del dominio → necesidad del patrón → objetos, referencias y UML → Java → salida de consola → pruebas → cuándo no utilizarlo.** Las explicaciones extensas viven en las fichas Markdown; el código conserva comentarios breves y nombres del dominio en español.

Los diagramas muestran dependencia, asociación, realización y herencia cuando aportan. **No inferimos agregación o composición simplemente porque una clase contenga un `List` o invoque `new`.** Las composiciones reales deben justificarse en las invariantes del dominio.

## Ilustraciones y próximas mejoras

Ya existen **tres fondos originales y una lámina de cuatro objetos**: puerta, linterna, interruptor y monstruo oscuro. Se muestran en el [atlas visual](GUIA_VISUAL.md) y están optimizados como WebP. Además, hay tres [láminas de comportamiento](GUIA_VISUAL.md#5-qué-cambia-cuando-se-ejecuta-el-código): transiciones de puertas, recorrido de monstruo y notificaciones de dispositivos. Pueden ampliarse siguiendo el [plan](PLAN.md).

El juego *The Complex: Expedition* y la cultura Backrooms son referencias estéticas, no fuentes de capturas ni de reglas normativas. Las ilustraciones **no tienen autoridad sobre el dominio**.
