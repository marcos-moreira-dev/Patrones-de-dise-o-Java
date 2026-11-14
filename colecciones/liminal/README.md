# Expedición Liminal — ruta visual de los 23 patrones GoF

**Estado: catálogo Java 21 completo (23/23).** Todos los patrones cuentan con una clase cliente ejecutable por consola, una prueba autónoma, un diagrama UML SVG y una ficha Markdown. Las **imágenes atmosféricas originales** de Backrooms/Dreamcore/Weirdcore quedan para la etapa artística, sin alterar las reglas del dominio.

La colección técnica original y los microproyectos Arena Java y MiniSO **se conservan intactos en su organización**.

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

## Ejecutar en Java 21

Sin JavaFX, Maven, redes ni frameworks. Desde la **raíz del repositorio**:

En Git Bash o Linux:

~~~bash
bash colecciones/liminal/scripts/verificar.sh
java -cp colecciones/liminal/out liminal.DemoLiminal todos
java -cp colecciones/liminal/out liminal.DemoLiminal strategy
java -cp colecciones/liminal/out liminal.DemoLiminal visitor
~~~

En PowerShell:

~~~powershell
powershell -ExecutionPolicy Bypass -File .\colecciones\liminal\scripts\verificar.ps1
java -cp colecciones/liminal/out liminal.DemoLiminal todos
~~~

Los scripts compilan código y pruebas, ejecutan las **23 verificaciones** y muestran las demostraciones por consola. También se ejecutan en la [verificación principal](../../scripts/verificar.sh) y en GitHub Actions, sin intervención del usuario.

## Regla editorial de los ejemplos

**Escena sencilla → reglas del dominio → necesidad del patrón → objetos, referencias y UML → Java → salida de consola → pruebas → cuándo no utilizarlo.** Las explicaciones extensas viven en las fichas Markdown; el código conserva comentarios breves y nombres del dominio en español.

Los diagramas muestran dependencia, asociación, realización y herencia cuando aportan. **No inferimos agregación o composición simplemente porque una clase contenga un `List` o invoque `new`.** Las composiciones reales deben justificarse en las invariantes del dominio.

## Etapa de ilustraciones atmosféricas

Se crearán imágenes originales de pasillos amarillos, piscinas vacías, oficinas, habitaciones y objetos con estética liminal analógica. Las imágenes de referencia del videojuego *The Complex: Expedition* y de la cultura Backrooms sirven para fijar la dirección visual; no se reproducirán mapas ni imágenes protegidas. Las ilustraciones **no tienen autoridad sobre el dominio**. El [inventario artístico](PLAN.md) sigue siendo el plan para esa etapa.
