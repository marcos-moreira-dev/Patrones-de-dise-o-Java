# Plan del proyecto integrador — Expedición Liminal

> **Estado (15 de noviembre de 2026): integrador con cuatro guiones lúdicos, Strategy + Observer + Mediator, dominio y pruebas Java 21.** Matriz 23/23 y demos aisladas independientes. Versión 1.5.
>
> **Cierre editorial de los tres bloques:** [dictamen académico 1.0](../../docs/CIERRE_ACADEMICO_GOF.md). Los apartados de diseño previo conservan decisiones históricas, pero no significan que el código aún esté pendiente.
>
> **Fuente normativa inicial:** [DOMINIO.md](DOMINIO.md). **Planes existentes:** [PLAN.md](PLAN.md), [PLAN_VARIANTES.md](PLAN_VARIANTES.md), [AUDITORIA_ANDAMIAJE.md](AUDITORIA_ANDAMIAJE.md). Cada cambio de dominio se justifica y prueba; los patrones no dictan las reglas del mundo.

## 1. Tres bloques independientes y para qué sirve cada uno

| Bloque | Ubicación actual/propuesta | Propósito | Política de dependencia |
| --- | --- | --- | --- |
| 1. GoF técnicos clásicos | Carpetas actuales de patrones técnicos (creacionales, estructurales, comportamiento) | Estudiar roles y teoría GoF | **No modificar** por este proyecto |
| 2. GoF liminales aislados | `colecciones/liminal/` | Cada patrón en su propio paquete: cliente, clases, UML, prueba y ficha; siete variantes y dos laboratorios aparte | **Mantener autonomía.** No importar clases del integrador |
| 3. Expedición Liminal integrada | **Implementado y auditado:** `proyectos/expedicion-liminal-integrada/` | Estudiar un dominio compartido, arquitectura, SOLID y colaboración real de patrones | No importar clases de las demos aisladas; compartir **contratos conceptuales**, no implementaciones |

Los proyectos `arena-java` y `miniso` y los workflows/scripts generales existentes permanecen sin modificar. El integrador vive en `proyectos/expedicion-liminal-integrada/` y dispone de **su propio script y workflow de CI**.

**No es un videojuego de entretenimiento como requisito.** Es un simulador lógico por consola con **cuatro expediciones predeterminadas reproducibles**. La lectura de arquitectura y Java es más importante que interactuar con él.

## 2. Visión del mundo: espacios liminales sin reducirlo a abrir puertas

Cinco familias narrativas reutilizables, todas compatibles con la autoridad del dominio:

| Familia | Escena posible | Aprendizaje |
| --- | --- | --- |
| Exploración y desorientación | Habitaciones de apariencia idéntica, mapa bidimensional, conexiones dirigidas | Identidad frente a valor, mapa, ubicación e invariantes |
| Criaturas | Monstruo oscuro que patrulla, persigue o espera; apariciones registradas | Strategy, Factory Method solo si se necesita, extensibilidad |
| Vigilancia y señales | Ruido en oficinas, luces, central de control, cámaras | Eventos, desacoplamiento, Observer/Mediator/Proxy |
| Equipo y artefactos | Linterna, configuración, posesión física y referencias | Decorator/Memento, R-06, encapsulamiento |
| Memoria de la expedición | Observaciones, entradas de registro e informes | Ciclo de vida, propiedad UML, R-09, responsabilidades |

Las puertas son **solo una mecánica entre varias**. Mantener R-05 donde corresponda, sin usarlo como ejemplo predeterminado de todas las decisiones de ingeniería. Los patrones son herramientas de diseño, no sucesos mágicos del mundo.

## 3. Contrato del integrador antes de programar

1. Heredar explícitamente R-00 a R-14 de [DOMINIO.md](DOMINIO.md). El integrador tendrá un `DOMINIO.md` propio con **referencia, alcance y reglas locales nuevas**, no una copia divergente y silenciosa.
2. `Posicion(x, y)` puede ser un `record` inmutable de valor. `Habitacion` y `Explorador` son entidades con identidad; dos habitaciones visualmente iguales no son la misma.
3. Una rejilla/`Map<Posicion, Habitacion>` permite **situar** lugares; las `Salida`s dirigidas permiten **transitar**. **Celdas adyacentes no garantizan tránsito**. Una salida válida requiere origen y destino existentes; una puerta opcional puede bloquearla.
4. Un explorador activo tiene una ubicación válida y única. La ubicación del monstruo es igualmente válida; coexistir en una habitación **no implica combate ni daño**.
5. Un turno es discreto y determinista. Fijar por escrito un orden estable de pasos. Sin tiempo real ni azar implícito; si se introduce azar, inyectar semilla/fuente determinista.
6. Rechazar un movimiento inválido antes de mutar ubicación o registro. **Regla local propuesta para el MVP:** el rechazo no consume turno; documentarla y probarla.
7. No introducir teletransportes, combate, daño, inventarios complejos ni mecánicas especiales porque una escena parezca extraña. Toda nueva regla debe tener caso válido, rechazo y prueba.

**Primera historia mínima (propuesta):** un explorador comienza en un pasillo amarillo, consulta señales de una oficina, detecta indicios de una criatura que cambia su estrategia y alcanza una salida segura. No hay que reproducir juegos ajenos ni imponer canon comunitario.

## 4. Arquitectura mínima: capas orientadas al dominio, sin andamiaje artificial

**Elección inicial:** monolito modular Java 21; separación `presentacion → aplicacion → dominio`. El dominio no conoce consola, archivos, servicios, ni clases de aplicación. Puertos/adaptadores de estilo hexagonal **solo cuando se aísle una dependencia efectiva**. MVC completo no aporta sin interfaz de usuario compleja.

~~~text
proyectos/expedicion-liminal-integrada/            (CIERRE ACADÉMICO, 3 GoF INTEGRADOS)
├── README.md
├── DOMINIO.md                                      (reglas locales + vínculo normativo)
├── docs/
│   ├── ARQUITECTURA.md                             (diagramas, dependencias)
│   ├── ESCENARIO_01.md                             (narrativa y turnos)
│   └── AUDITORIA_FINAL.md                          (SOLID, riesgos, suficiencia)
└── src/
    ├── main/java/liminal/integrado/
    │   ├── dominio/
    │   │   ├── mundo/                              (Posicion, Habitacion, Salida, MapaLiminal)
    │   │   ├── exploracion/                        (Explorador, Expedicion)
    │   │   ├── criaturas/                          (MonstruoOscuro, EstrategiaMovimiento)
    │   │   └── vigilancia/                         (SensorRuido, CentroVigilancia, luz, alarma)
    │   ├── aplicacion/                             (ProcesarTurno, ResultadoTurno)
    │   └── presentacion/                           (ClienteExpedicion)
    └── test/java/liminal/integrado/                 (dominio + escenarios)
~~~

**No crear carpetas/clases vacías por anticipado.** `infraestructura/`, `dispositivos/`, `registro/` y contratos de puertos se añaden solo si aparecen necesidades comprobables. Organizar por conceptos y responsabilidades, **no por 23 carpetas nombradas con los GoF**.

- **Dominio:** reglas, entidades, objetos-valor, movimientos válidos, estado de criaturas.
- **Aplicación:** ordena un turno y coordina casos de uso, sin redefinir invariantes del mapa.
- **Presentación:** `ClienteExpedicion` construye los objetos, ejecuta una secuencia fija y muestra resultados. `Scanner` y un parser de comandos interactivo **no son requisitos**.
- **Infraestructura:** solo implementaciones concretas necesarias; almacenamiento en memoria basta al principio.
- **Pruebas:** verifican transiciones, invariantes y un recorrido reproducible sin introducir servicios externos.

**Evitar:** una clase `MotorJuego` que haga todo, “un servicio por entidad”, interfaces para una sola implementación sin beneficio, contenedores de dependencias y capas no utilizadas, importaciones cíclicas y herencia innecesaria.

## 5. Secuencia didáctica del cliente (sin interacción obligatoria)

Una sola clase cliente inicia una expedición reproducible y delega el trabajo:

1. **Preparar:** definir mapa pequeño y posiciones, un explorador, un monstruo y un objetivo concreto.
2. **Observar:** consultar una sala, señal o registro sin avanzar el turno innecesariamente.
3. **Mover:** intentar una salida válida; comprobar nueva ubicación.
4. **Rechazar:** intentar una ruta inexistente o un paso prohibido; verificar que no cambia el estado.
5. **Criatura:** procesar el turno bajo una estrategia, intercambiarla y seguir respetando el mapa.
6. **Resolver:** llegar a una salida segura; comprobar resultado de expedición y registro.

El cliente **orquesta** y narra: no decide si una salida es válida, no calcula rutas directamente y no modifica campos internos de entidades.

## 6. Patrones integrados: selección justificada, nunca cuota 23/23

**Implementados y justificados:** `Strategy` para las políticas del monstruo, `Observer` para publicar los eventos del sensor y `Mediator` para coordinar la central con luz y alarma. Su ubicación, participantes, coste y prueba constan en [la auditoría](../../proyectos/expedicion-liminal-integrada/docs/AUDITORIA_FINAL.md). `Facade`, `Factory Method` y los otros 18 GoF no son necesarios en este integrador y permanecen en los ejemplos aislados.

**Extensiones opcionales, no pendientes:** `Proxy` (cámara), `Decorator/Memento` (linterna), `Iterator/Visitor` (consulta), `Builder/Abstract Factory/Prototype/Flyweight` (creación y configuración), `Command/State/Chain` (acciones), `Adapter/Bridge/Interpreter` (comunicación e instrucciones), `Template Method` (rutinas). `Singleton` exige especial cautela: solo introducirlo ante un alcance único real, no para completar la lista.

El **mapa de los tres patrones usados** aparece en [AUDITORIA_FINAL.md](../../proyectos/expedicion-liminal-integrada/docs/AUDITORIA_FINAL.md): problema, clases, justificación, dependencia, reglas R-INT y pruebas. Los otros 20 patrones **no se implementan por cuota** y siguen disponibles como ejemplos aislados.

## 7. Alineación innegociable de los 23 ejemplos liminales aislados

Esta tarea tiene **su propia pista editorial**, independiente de la implementación del integrador. **No refactorizar los 23 clientes para que participen en el proyecto nuevo.**

### Inventario de escenarios y correspondencia

| Familia | Patrones aislados que ya explican esa faceta |
| --- | --- |
| Criaturas, aparición y decisiones | Factory Method, Strategy |
| Habitaciones, ambientación e identidad | Abstract Factory, Builder, Prototype, Flyweight, Iterator, Template Method |
| Dispositivos, mensajes, luz y vigilancia | Adapter, Bridge, Composite, Proxy, Mediator, Observer |
| Artefactos, inspección y estados | Decorator, Memento, Visitor, State |
| Acciones y validaciones | Facade, Chain of Responsibility, Command, Interpreter |
| Avisos compartidos de la demo | Singleton |

Esta matriz cubre los **23 patrones base**. La clasificación es **editorial**, no afirma que sus clases ya formen un programa común. Mantener las **siete variantes** y **dos laboratorios** asociados a sus patrones y objetivos originales.

### Lista de verificación por patrón aislado

- Escena concisa, entendible y diversa; evitar recurrir a puertas donde la escena real trata de criatura, cámara, registro, textura o señal.
- Roles GoF, colaboradores, cliente y prueba **autónomos**. No introducir un motor central ni imports del integrador.
- Contrato R-XX realmente usado, con validaciones, resultados y errores coherentes; cada regla local tiene alcance.
- UML acorde a Java: visibilidad, dirección, multiplicidad y propiedad **sin rombos ornamentales**.
- Comentarios de ingeniería **escasos y contextuales**: arquitectura y organización, cohesión/acoplamiento, SOLID cuando aplique, buen criterio de excepciones, identidad y colecciones; explicar el **porqué**, no narrar cada línea. Meta orientativa de **10–20 observaciones nuevas relevantes en toda la colección**, no por patrón.
- Conservar `class/interface` para roles/entidades e introducir `record` o genéricos cuando exista **relación de tipos o valor justificada**; no aplicar conversiones cosméticas.
- Cada corrección debe surgir de una incongruencia comprobable. La auditoría de suficiencia académica ya da el conjunto por utilizable; **no hay que rediseñarlo por perfeccionismo**.
- Si un nuevo caso del integrador descubre una contradicción normativa, **registrar el hallazgo y analizar impacto en ambas pistas**. Un cambio del juego **no obliga** a cambiar 23 demos que cumplen su contrato.

### Orden editorial de la alineación

1. **Completado:** crear la [matriz de alineación de 23 patrones](MATRIZ_ALINEACION.md) con escenas, roles, reglas R-XX, decisiones pertinentes, riesgo editorial y trazabilidad de comentarios.
2. Resolver solo errores demostrados de vocabulario/reglas/código/UML; documentación y pruebas en el mismo cambio.
3. **Completado:** 17 comentarios puntuales explican decisiones no evidentes en colaboradores reales; otros seis patrones se apoyan en sus fichas/Javadoc. **No inventar arquitectura por comentar.**
4. Verificar en CI Java 21 y confirmar que **ninguna modificación afectó la colección técnica clásica ni Arena Java/MiniSO**. La alineación es suficiente cuando CI pase y no haya contradicción reproducible.

## 8. Etapas y condiciones verificables de cierre

| Etapa | Trabajo | Resultado medible | Criterio de salida |
| --- | --- | --- | --- |
| **0 — Contrato y alineación** | [Matriz 23/23](MATRIZ_ALINEACION.md) y comentarios de ingeniería acotados | Matriz y 17 comentarios escritos, sin alterar el comportamiento | **Alineación editorial realizada**; validar con CI final |
| **1 — Núcleo mínimo** | **Completado:** mapa bidimensional, salidas dirigidas, explorador, expedición y procesamiento de turnos | Cliente determinista, cuatro pasos válidos, dos rechazos y pruebas | Java 21 con CI específica en Ubuntu/Windows |
| **2 — Criatura y caso de uso** | **Completado:** criatura con Strategy (`Patrullar`, `Perseguir`, `Esperar`) integrada por `ProcesarTurno` | Identidad estable, no teletransporte, encuentro narrado y expedición finalizada | Pruebas específicas Java 21 y CI Linux/Windows |
| **3 — Integración selectiva** | **Completada:** sensor SR-03, Observer y Mediator coordinan luz y alarma | Un evento real del guion, suscripciones y pruebas dedicadas | Sin red, cámaras, frameworks ni patrones decorativos |
| **4 — Cierre editorial conjunto** | **Completado:** UML/diagramas actualizados, [auditoría final](../../proyectos/expedicion-liminal-integrada/docs/AUDITORIA_FINAL.md), trazabilidad y enlaces | Tres rutas de estudio visibles, pruebas y riesgos delimitados | CI Linux/Windows, sin mezcla de proyectos |

**Regla de suficiencia para el integrador:** las etapas 1 a 4 están **terminadas**, con una expedición completa, dominio coherente, capas, pruebas deterministas, Strategy, Observer, Mediator y auditoría explícita. **El proyecto académico es suficiente y se detiene aquí**. Los 20 GoF restantes se estudian en sus ejemplos aislados, sin obligación de integrarlos. La **alineación de los aislados** alcanza suficiencia al cerrar la etapa 0 y resolver únicamente hallazgos concretos, con un chequeo final después.

**No objetivos:** JavaFX, gráficos, física, combate, red, persistencia, multijugador, IA compleja, aleatoriedad libre, servicios HTTP, interfaz interactiva obligatoria, framework pesado, 23 patrones integrados por cuota, monolito de 23 demos conectadas, ni cambios a otros proyectos.

## 9. Cómo proceder sin olvidar tareas

Antes de cualquier commit del integrador, marcar en el plan cuál etapa se está ejecutando y qué demuestra. No mezclar en el mismo commit cambios del integrador y refactorizaciones de demos aisladas. Ante un conflicto, el contrato explícito del dominio prevalece; actualizar cada área afectada solo cuando corresponda.

**Cierre:** los tres bloques de aprendizaje están enlazados e independientes. Los 23 ejemplos GoF liminales siguen [alineados](MATRIZ_ALINEACION.md); el integrador finaliza con [Strategy + Observer + Mediator](../../proyectos/expedicion-liminal-integrada/README.md), [UML](../../proyectos/expedicion-liminal-integrada/docs/ARQUITECTURA.md) y [auditoría final](../../proyectos/expedicion-liminal-integrada/docs/AUDITORIA_FINAL.md). No hay trabajo obligatorio adicional; futuras mejoras solo ante defectos comprobados o nuevas metas de estudio.

## 10. Variedad narrativa: tres guiones complementarios

El integrador conserva [la expedición de oficinas](../../proyectos/expedicion-liminal-integrada/docs/ESCENARIO_01.md) y añade [piscinas, radio y luces](../../proyectos/expedicion-liminal-integrada/docs/ESCENARIOS_02_04.md): tres relatos con clientes y mapas independientes, **pero sobre el mismo dominio**. Las piscinas enseñan identidad y conexiones dirigidas; la radio, mensajes predefinidos según lugar y frecuencia (R-INT-12); las luces, la colaboración existente Strategy + Observer + Mediator. [PruebasGuiones](../../proyectos/expedicion-liminal-integrada/src/test/java/liminal/integrado/PruebasGuiones.java) y el verificador validan los cuatro relatos.

**La regla de parada se mantiene:** no hay interacción manual obligatoria ni motor gráfico, ni nuevos GoF por cuota, y los 23 ejemplos encapsulados no dependen del integrador.
