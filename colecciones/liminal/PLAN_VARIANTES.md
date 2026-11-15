# Expedición Liminal — Plan de variantes GoF y laboratorio `class` / `record`

> **Estado: 7/7 variantes implementadas, laboratorio Java terminado, 17/17 fichas restantes comparadas por escrito y primera auditoría de andamiaje realizada.** Versión 1.2 — 14 de noviembre de 2026. **Estado de auditoría:** clientes y fichas de los 23 GoF, siete variantes, dos laboratorios, enlaces y revisión textual/geométrica de UML comprobados; **pendiente** lectura semántica exhaustiva de todas las clases colaboradoras y revisión visual rasterizada de los diagramas. Los patrones técnicos originales y los microproyectos están fuera del alcance.
> Esta hoja de ruta amplía el catálogo existente; no sustituye [PLAN.md](PLAN.md) ni modifica las reglas de [DOMINIO.md](DOMINIO.md). No añadir nuevas mecánicas al mundo simplemente para justificar una variante.

## 1. Resultado que queremos conseguir

El catálogo principal tiene **23 patrones GoF** con ejemplos independientes por consola, clientes, UML, pruebas y fichas, más un laboratorio de relaciones UML. La colección técnica original también tiene los 23 ejemplos. La nueva etapa debe enseñar **cómo elegir una variante de implementación** sin exigir comprender un juego completo.

**Tres rutas de estudio:**

1. **Base clásica (obligatoria):** leer la escena, reglas R-XX, UML, Cliente, clases individuales, consola y prueba del patrón principal. Sigue siendo la entrada más fácil.
2. **Variantes (profundización):** comparar soluciones que preservan la intención GoF y el mismo dominio, pero cambian las responsabilidades, copias, acoplamientos o costes.
3. **Java moderno (laboratorio separado):** estudiar cuándo `record` agrega valor frente a `class`. Los `record` no son variantes de patrones GoF.

**No objetivos:** JavaFX, backend HTTP, multijugador, motor gráfico, base de datos, red real, tiempo real, concurrencia introducida sin justificación, reestructurar las carpetas técnicas antiguas o crear un motor de dominio común para los 23 ejemplos.

## 2. Contrato del mundo: lo que NO cambia

La autoridad normativa continúa siendo [DOMINIO.md](DOMINIO.md), incluida la regla R-00.

| Regla | Impacto sobre las variantes | Requisito de aceptación |
| --- | --- | --- |
| **R-01 / R-10** | Prototype crea plantillas o nuevas entidades sin duplicar IDs activos | Dos entidades materializadas en un mapa no comparten ID |
| **R-06** | Copia profunda de plantillas de objetos ≠ duplicación física de linternas y llaves existentes | Nunca aparece el mismo artefacto portable físicamente en dos inventarios |
| **R-05 / R-12** | Command con deshacer debe respetar transiciones de puerta y rechazo sin mutación parcial | El deshacer no crea estados imposibles; fallo no altera el estado |
| **R-02 / R-04 / R-12** | Chain como pipeline valida antes de ejecutar cruces | Una ruta sin puerta puede pasar; un rechazo no mueve al explorador |
| **R-07 / R-11** | Observer básico usa notificaciones síncronas; comparar `push` y `pull` sin hilos | Orden de avisos reproducible; suscribir no implica propiedad UML |
| **R-11 / R-12** | Proxy de caché usa una cámara simulada con contador/versiones, sin servicio remoto real | Se puede verificar cada acierto, invalidación y rechazo |
| **R-13** | Builder con Director construye ambientes amarillo/piscina con la misma semántica del mundo | Cambiar estética no habilita nuevas reglas de movimiento |
| **R-09 / R-14** | No utilizar composición como adorno ni llamar variante GoF a una construcción de Java | Cada rombo negro debe tener una regla de propiedad y ciclo de vida |

Cuando una variante necesite un supuesto nuevo, escribir una ficha `R-LOCAL-<PATRON>-NN` con **ámbito, enunciado, ejemplo válido, caso inválido y prueba**; nunca editar silenciosamente R-01…R-14. Las variantes funcionan en paquetes aislados: que varias clases se llamen `Habitacion` en demos distintas no las convierte en el mismo tipo Java.

## 3. Organización física propuesta

Se mantiene intacta la ejecución actual de `DemoLiminal todos` y el conteo de **23 patrones base**. Los ejemplos nuevos viven **dentro de la carpeta de su patrón**, pero en subpaquetes de variantes para no mezclar roles ni imports.

```text
colecciones/liminal/
├── README.md
├── DOMINIO.md
├── PLAN.md
├── PLAN_VARIANTES.md                      ← este plan
├── GUIA_LECTURA_CODIGO.md
├── docs/
│   ├── patrones/creacionales/prototype.md  ← ficha principal, breve
│   ├── variantes/
│   │   ├── creacionales/
│   │   │   ├── prototype/
│   │   │   │   ├── copia-profunda.md
│   │   │   │   └── registro-prototipos.md
│   │   │   └── builder/director.md
│   │   ├── estructurales/proxy/cache.md
│   │   └── comportamiento/
│   │       ├── command/deshacer.md
│   │       ├── chainofresponsibility/pipeline.md
│   │       └── observer/push-pull.md
│   └── laboratorios/java/class-vs-record.md
├── src/main/java/liminal/
│   ├── creacionales/
│   │   ├── builder/
│   │   │   ├── ClienteBuilder.java         ← base, NO mover
│   │   │   ├── ConstructorHabitacion.java
│   │   │   ├── Habitacion.java
│   │   │   └── variantes/director/         ← clases individuales
│   │   └── prototype/
│   │       ├── ClientePrototype.java      ← base, NO mover
│   │       └── variantes/
│   │           ├── copiaprofunda/
│   │           └── registro/
│   ├── estructurales/proxy/variantes/cache/
│   ├── comportamiento/
│   │   ├── command/variantes/deshacer/
│   │   ├── chainofresponsibility/variantes/pipeline/
│   │   └── observer/variantes/pushpull/
│   └── laboratorios/java/classvsrecord/
└── src/test/java/liminal/
    ├── ...                                  ← pruebas de los 23 originales
    ├── .../variantes/                      ← pruebas propias de cada variante
    └── laboratorios/java/classvsrecord/
```

Las rutas anteriores corresponden a la **estructura implementada** (los nombres de subpaquete son sus identificadores Java sin guiones). La carpeta base contiene **un tipo superior por archivo**, y cada variante deberá respetar el mismo criterio. La guía técnica extendida vive en Markdown; no saturar Java con comentarios.

**Lanzamiento:** cada variante puede ejecutar su propio `ClienteVariante.main`. Opcionalmente añadir `DemoVariantes` con menús separados; nunca añadir siete modos a `DemoLiminal todos` si eso rompe la ruta de estudio de 23 ejemplos. Compilar con `--release 21`; ninguna dependencia externa.

## 4. Primera etapa: coherencia entre clases tradicionales, UML y `record`

### 4.1. Convención del catálogo clásico

- **Participantes GoF principales** (creador, producto-entidad, observador, estrategia, comando, etc.): preferir `class` / `interface` tradicionales y atributos/constructores/métodos visibles.
- Hacer **inventario** de usos existentes de `record` antes de cambiar nada; no aplicar reemplazos automáticos en lote.
- Revisión completada: `creacionales/builder/Habitacion.java` y `creacionales/prototype/HabitacionPrototipo.java` **eran records** y ya se convirtieron en clases inmutables con atributos y constructores explícitos. Los principales roles GoF de otras carpetas se alinearon de la misma forma. **Identidad Java (`==`), ID de dominio y valor no son equivalentes.**
- Mantener la consola y pruebas del patrón intactas; modificar UML y fichas si el tipo o los métodos cambian.
- Si un `record` representa un simple valor, no es automáticamente un error técnico. Sin embargo, en la ruta clásica debe justificarse; los ejemplos en los que su sintaxis sea el objetivo pertenecen al laboratorio Java moderno.

### 4.2. Laboratorio independiente: `class` frente a `record`

**Escena:** las paredes de un pasillo amarillo comparten una **configuración de textura**; cada habitación, en cambio, tiene identidad propia y puede cambiar algunos atributos durante su vida.

- `record ClaveTextura(String material, String patron)` como **objeto-valor inmutable** utilizado como clave en un `Map` de caché: dos claves con los mismos valores producen igualdad y `hashCode()` equivalentes. Aquí los métodos generados son útiles, no solo ahorro de líneas.
- `class Habitacion` como **entidad** con ID y comportamiento, cuya igualdad de dominio no debe depender accidentalmente de todos los atributos.
- **Contraste:** una clase-valor equivalente necesitaría `equals()`, `hashCode()` y accesores explícitos; un record con componentes mutables **no** da inmutabilidad profunda automáticamente.
- **Material:** dos Java clientes simples (o un cliente con dos métodos), pruebas de igualdad, claves de mapa, identidad, mutabilidad y copia defensiva; UML de clases con nota aclarando que `«record»` es una convención del proyecto, **no un estereotipo UML estándar universal**.
- **Resultado:** un [Markdown conceptual] con tabla **cuándo elegir clase / cuándo elegir record / cuándo no usar record**. Este laboratorio no cuenta como GoF número 24 ni introduce un framework.

## 5. Variantes priorizadas: seis patrones, siete demostraciones

Las siete demostraciones **no sustituyen** las siete implementaciones originales de sus patrones. Cada una tiene un problema distinto, clase cliente, prueba, UML y comparación con la base.

| Orden | Patrón / variante | Escena liminal y decisión de ingeniería | Reglas y pruebas clave |
| --- | --- | --- | --- |
| 1 | **Prototype — copia profunda frente a superficial** | Una plantilla de habitación contiene **plantillas mutables de luminarias** y decoración. Comparar compartir un objeto interno frente a copiarlo y evitar contaminación de plantillas | R-01, R-06, R-10: originales y copias entidades nuevas tienen ID nuevo; una mutación de la copia profunda no afecta el origen; nunca duplicar artefactos físicos |
| 2 | **Prototype — registro de prototipos** | Un catálogo de plantillas amarillo, piscinas y oficinas crea habitaciones a partir de modelos registrados | R-01, R-10, R-13: plantilla no activa, claves únicas, error por plantilla ausente, nueva identidad de habitación materializada |
| 3 | **Builder — Director** | Dos secuencias de construcción reutilizables preparan habitaciones amarillas o de piscinas; el Director controla el **orden**, el Builder los pasos y el producto el resultado | R-01, R-12, R-13: constructor incompleto falla; el Director no cambia las leyes de puerta/movimiento |
| 4 | **Proxy — caché y caducidad explícita** | Una cámara local ficticia genera una vista costosa de un pasillo. El proxy guarda la vista, controla una versión y la invalida cuando cambia la escena | R-07, R-11, R-12: contador determinista de consultas, acierto/miss observable, invalidación correcta, sin red real |
| 5 | **Command — deshacer/rehacer** | Un panel abre una puerta y permite revertir la operación cuando el estado lo permite | R-05, R-12: no se puede «deshacer» si otra acción ha vuelto incompatible el estado; historial no recrea objetos ni cambia IDs |
| 6 | **Chain of Responsibility — pipeline completo** | Varios validadores comprueban ruta, puerta, mapa y posibles condiciones locales antes de autorizar una acción | R-02, R-04, R-12: salida sin puerta permitida; todos los controles observan; **no** se modifica el mundo antes de completar validaciones |
| 7 | **Observer — `push` frente a `pull` síncronos** | Interruptor informa el valor completo a una luz o solo avisa que cambió, permitiendo que el receptor consulte al emisor | R-07, R-11: mismos resultados finales, referencias compartidas, orden reproducible, cancelación de suscripción, sin hilos |

**Cambios de UML específicos:**

- Prototype: diagrama de **objetos** antes/después (copia profunda versus referencias compartidas); explicar que las plantillas no son artefactos físicos activos.
- Builder: UML de roles `Director → Builder/ConcreteBuilder → Product`, junto al Builder fluido existente; si comparten métodos, identificarlo con precisión.
- Proxy: interfaz común, proxy con referencia al objeto real y memoria de caché; *caché no implica composición UML*.
- Command: invocador, comando, receptor e historial; un diagrama de **estados** para deshacer cuando cambian las precondiciones.
- Chain: dos diagramas de **flujo** para primer resultado frente a validación total; evitar efectos parciales.
- Observer: dos diagramas breves de **secuencia** mostrando datos enviados y consultas recibidas.

### Variante asíncrona de Observer: NO incluida en esta entrega

La entrega base del dominio excluye hilos, tiempo real y ejecución concurrente (R-11). Una variante asíncrona introduciría políticas adicionales: colas, orden, reintentos, idempotencia, fallos de receptores, cierre y pruebas deterministas. **Por ahora solo se documenta como evolución opcional**. Si en otro momento se implementa, requerirá un laboratorio avanzado aislado con reglas locales explícitas, no un cambio silencioso del Observer original.

## 6. Los otros 17 patrones: documentar alternativas sin código extra

Para los restantes no es necesario crear otra clase o variante si no hay una **decisión de ingeniería observable**. Cada ficha incluirá una sección corta “Otras implementaciones y por qué no aparecen en esta demo”.

| Categoría | Patrones no priorizados para segunda demo | Alternativa a comparar por escrito |
| --- | --- | --- |
| Creacionales | Singleton, Factory Method, Abstract Factory | Eager/lazy/enum; fábrica parametrizada frente a método virtual; fábrica configurable frente a familia de clases |
| Estructurales | Adapter, Bridge, Composite, Decorator, Facade, Flyweight | Adaptador por herencia vs referencia; implementación intercambiable; API uniforme vs segura; orden de decoradores; fachadas parciales; separación de estado intrínseco/extrínseco |
| Comportamiento | Interpreter, Iterator, Mediator, Memento, State, Strategy, Template Method, Visitor | Gramáticas más extensas; iterador vivo vs instantánea; mediador de mensajes; instantánea total vs incremental; estado compartido; estrategias con lambdas; hooks; visitante acíclico/pattern matching |

**Regla de crecimiento:** añadir código para alguna de estas variantes solo si (1) hay escenario entendible, (2) un contrato o limitación real cambia, (3) UML y pruebas evidencian la diferencia y (4) la comparación enseña algo más que sintaxis.

## 7. Plantilla editorial para CADA variante

Cada `.md` adicional debe incluir, en este orden:

1. **Imagen/escena** (reutilizar pasillo, piscina, oficina o collage ya existentes; no generar una imagen por obligación).
2. **Problema del ejemplo original** y por qué la variante aporta algo nuevo.
3. **Contrato:** R-XX y, si procede, R-LOCAL-XX, incluyendo precondición/postcondición, contraejemplo y prueba.
4. **Mapa de roles GoF** y **diagrama UML** basado en clases reales; multiplicidades justificadas; ninguna agregación/composición decorativa.
5. **Ruta de lectura:** primero Cliente, luego contratos, implementaciones, pruebas, un archivo Java superior por archivo.
6. **Consola** esperada con pasos o métodos que indiquen el orden.
7. **Pruebas:** caso válido, caso inválido, conservación de identidad, rechazo atómico y comparación de variantes.
8. **Tabla base/variante:** complejidad añadida, comportamiento observable, costes y **cuándo NO usarla**.

Las descripciones pedagógicas largas van en Markdown; comentarios Java cortos. Las imágenes son **atmosféricas, no normativas**, con procedencia documentada. Las ilustraciones actuales (tres ambientes, lámina de objetos, State/Strategy/Observer) deben reutilizarse primero; crear nuevas solo si aclaran una diferencia conceptual que UML no comunica.

## 8. Estado de ejecución y compuertas de calidad

**Implementación confirmada:** los 23 GoF principales permanecen independientes y hay siete clientes nuevos: dos Prototype (copia superficial/profunda y registro), Builder con Director, Proxy con caché, Command con deshacer/rehacer, Chain de pipeline y Observer push/pull. El laboratorio `class` / `record` tiene un cliente adicional. Sus pruebas se ejecutan desde `PruebasLiminal` con Java 21.

**Alineación clásica:** se revisaron los usos de `record` en las 132 fuentes base; los participantes principales se representan con `class` y cuatro records de datos/valor permanecen intencionalmente. Véase [auditoría del andamiaje](AUDITORIA_ANDAMIAJE.md).

**Cobertura editorial completada:** los 17 patrones sin segunda implementación incluyen una sección breve «Otras implementaciones (sin código extra)». La [auditoría de andamiaje](AUDITORIA_ANDAMIAJE.md) tiene clasificación y candidatos de simplificación.

**Aún no demostrado por esta etapa:** una auditoría semántica línea por línea de los **291 archivos de `colecciones/liminal/`**, o una auditoría visual exhaustiva de sus diagramas UML. No mezclar esas tareas con la verificación de compilación.

### Etapas de implementación originalmente planificadas

| Fase | Trabajo | Evidencia para pasar |
| --- | --- | --- |
| **0 — Auditoría de línea base** | Guardar estado de pruebas del repositorio; inventariar `record`; analizar igualdad/identidad; detectar texto antiguo en `PLAN.md` que todavía habla de archivos “FUTURO” | Informe de impacto; 23/23 demos liminales y 23 técnicos sin regresión |
| **1 — Clases y laboratorio moderno** | Convertir entidades/roles principales de los ejemplos clásicos que justifiquen `class` sin alterar comportamiento; crear laboratorio Java moderno separado | Tests de valor versus identidad; UML y docs concordantes; mismas salidas base |
| **2 — Creacionales** | Prototype: copias + registro; Builder: Director | Tres demos nuevas autónomas con UML, pruebas y comparación |
| **3 — Estructurales y operaciones reversibles** | Proxy: caché; Command: undo/redo | Dos demos nuevas; pruebas de invalidación/atomicidad; sin red |
| **4 — Colaboración** | Chain: validación completa; Observer: push/pull síncrono | Dos demos nuevas; sin efectos intermedios ni hilos |
| **5 — Cobertura editorial de los 23** | Incorporar la comparación breve en las 17 fichas restantes; actualizar navegación desde las seis fichas con variantes | El lector distingue lo obligatorio de lo opcional |
| **6 — Auditoría y cierre** | Verificar compilación Java 21, Linux/Windows, UML/SVG, enlaces, reglas locales, ortografía y lectura “a mano” | Catálogo 23 intacto + 7 variantes + 1 laboratorio nuevo, pruebas reproducibles |

**Definición de terminado por variante:** contrato R-XX citado; cliente legible; clases separadas; un UML fiel; una comparación con la implementación clásica; pruebas positivas/negativas; salida reproducible; enlace desde ficha base; compilación en CI. **No cuenta como patrón GoF nuevo**.

Si una variante se vuelve más compleja que el problema que debía enseñar, simplificarla o descartarla. Mejor tres variantes bien justificadas que diez demostraciones artificiales.

## 9. Decisiones explícitamente aplazadas

- Hilos, eventos asíncronos, servicios HTTP, JavaFX, persistencia y dependencias externas.
- Un dominio Java compartido entre los 23 patrones (introduciría acoplamiento entre lecciones).
- Copia profunda de objetos físicos activos sin resolver antes R-06.
- Imágenes de capturas protegidas de *The Complex: Expedition*: mantener referencias estéticas generales y material original.
- Convertir `record` indiscriminadamente sin identificar si es entidad o valor.
- Modificar los 23 patrones originales para meter variaciones en un único `ClienteX`.

## 10. Primer paso implementado y próximas revisiones

**Completado:** la [variante de copia profunda](docs/variantes/creacionales/prototype/copia-profunda.md) distingue referencias mutables; el [catálogo de prototipos](docs/variantes/creacionales/prototype/registro-prototipos.md) garantiza IDs locales; el [laboratorio Java](docs/laboratorios/java/class-vs-record.md) muestra cuándo una clave de caché justifica `record`. Las 17 fichas adicionales ya comparan alternativas, las siete variantes tienen sus pruebas, y la [auditoría de andamiaje](AUDITORIA_ANDAMIAJE.md) documenta qué se verificó y qué queda fuera de una certificación exhaustiva.

[Catálogo principal](README.md) · [Dominio normativo](DOMINIO.md) · [Guía de lectura de clases](GUIA_LECTURA_CODIGO.md) · [Guía de modelado de dominios](../../docs/modelado/COMO_DEFINIR_UN_DOMINIO.md).
