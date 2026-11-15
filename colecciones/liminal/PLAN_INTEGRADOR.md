# Plan del proyecto integrador — Expedición Liminal

> **Estado (15 de noviembre de 2026): planificación, NO implementado.** El plan define el tercer bloque de aprendizaje del repositorio. No crea un motor ejecutable ni altera los 23 ejemplos liminales. Versión 1.0.
>
> **Fuente normativa inicial:** [DOMINIO.md](DOMINIO.md). **Planes existentes:** [PLAN.md](PLAN.md), [PLAN_VARIANTES.md](PLAN_VARIANTES.md), [AUDITORIA_ANDAMIAJE.md](AUDITORIA_ANDAMIAJE.md). Cada cambio de dominio se justifica y prueba; los patrones no dictan las reglas del mundo.

## 1. Tres bloques independientes y para qué sirve cada uno

| Bloque | Ubicación actual/propuesta | Propósito | Política de dependencia |
| --- | --- | --- | --- |
| 1. GoF técnicos clásicos | Carpetas actuales de patrones técnicos (creacionales, estructurales, comportamiento) | Estudiar roles y teoría GoF | **No modificar** por este proyecto |
| 2. GoF liminales aislados | `colecciones/liminal/` | Cada patrón en su propio paquete: cliente, clases, UML, prueba y ficha; siete variantes y dos laboratorios aparte | **Mantener autonomía.** No importar clases del integrador |
| 3. Expedición Liminal integrada | **Propuesto:** `proyectos/expedicion-liminal-integrada/` | Estudiar un dominio compartido, arquitectura, SOLID y colaboración real de patrones | No importar clases de las demos aisladas; compartir **contratos conceptuales**, no implementaciones |

Los proyectos `arena-java` y `miniso` y los workflows/scripts globales están fuera de alcance. El nombre de carpeta propuesto no existe todavía y podrá fijarse antes de crear código.

**No es un videojuego de entretenimiento como requisito.** Será un simulador lógico por consola con una expedición predeterminada reproducible. La lectura de arquitectura y Java es más importante que interactuar con él.

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
proyectos/expedicion-liminal-integrada/            (PROPUESTO, no creado)
├── README.md
├── DOMINIO.md                                      (reglas locales + vínculo normativo)
├── docs/
│   └── arquitectura.md                             (paquetes, dependencias, decisiones)
└── src/
    ├── main/java/liminal/integrado/
    │   ├── dominio/
    │   │   ├── mundo/                              (Posicion, Habitacion, Salida, MapaLiminal)
    │   │   ├── exploracion/                        (Explorador, Expedicion)
    │   │   └── criaturas/                          (MonstruoOscuro, EstrategiaMovimiento)
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

**Naturales para una primera versión:** `Strategy` en comportamiento del monstruo; podrían aparecer `Facade` si se demuestra una coordinación repetida que simplifique al cliente, y `Factory Method` si la creación de tipos de criaturas se hace variable. No imponer ninguno antes de observar el problema.

**Extensiones justificables más adelante:** `Observer/Mediator` (señales), `Proxy` (cámara), `Decorator/Memento` (linterna), `Iterator/Visitor` (consulta), `Builder/Abstract Factory/Prototype/Flyweight` (creación y configuración), `Command/State/Chain` (acciones), `Adapter/Bridge/Interpreter` (comunicación e instrucciones), `Template Method` (rutinas). `Singleton` exige especial cautela: solo introducirlo ante un alcance único real, no para completar la lista.

Un **mapa de patrones** en `docs/arquitectura.md` registrará por cada patrón **utilizado**: problema real, clases participantes, justificación frente a solución simple, dependencia introducida, regla R-XX y prueba. Los no usados se listan como “no necesario en este integrador”, sin crear código testimonial.

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

1. Crear una **matriz breve por patrón** (23 filas) con escena, problema GoF, reglas R-XX, relación UML, comentario de ingeniería pertinente (o “no hace falta”), riesgo real y estado.
2. Resolver solo errores demostrados de vocabulario/reglas/código/UML; documentación y pruebas en el mismo cambio.
3. Añadir comentarios puntuales donde el código ya contenga decisiones no evidentes: por ejemplo Strategy/identidad, Composite/propiedad, Observer/instantáneas, Facade/invariantes, Prototype/genéricos, Factory Method/registro. **No inventar arquitectura por comentar.**
4. Cerrar con CI Java 21 y confirmar que **ninguna modificación afectó la colección técnica clásica ni Arena Java/MiniSO**.

## 8. Etapas y condiciones verificables de cierre

| Etapa | Trabajo | Resultado medible | Criterio de salida |
| --- | --- | --- | --- |
| **0 — Contrato y alineación** | Conservar este plan; inventariar escenas y reglas de los aislados | Matriz 23/23, decisiones y alcance declarados | No hay contradicciones conocidas entre vocabulario y R-XX; no se han mezclado proyectos |
| **1 — Núcleo mínimo** | Crear nueva carpeta cuando se apruebe implementar; mapa bidimensional + salidas, entidad explorador, turnos | Cliente determinista con recorrido válido y rechazo; pruebas | Compila Java 21; invariantes R-01…R-04 y R-12 aplicables pasan |
| **2 — Criatura y caso de uso** | Introducir una criatura y su comportamiento cambiable; objetivo de salida | Recorrido completo narrado, Strategy justificado y un evento relevante | Pruebas de identidad, ubicación, rutas y resultado; no depende de consola interactiva |
| **3 — Integración selectiva** | Solo después: señales, cámara, linterna o registro, según la necesidad de estudio | 1–2 colaboraciones transversales reales con patrón y coste documentados | Cada añadido tiene escenario, responsabilidad, prueba y alternativa sencilla |
| **4 — Cierre editorial conjunto** | UML de paquetes y 1–2 secuencias, mapa de dependencias, matriz de trazabilidad, comentarios selectivos en aislados | Tres rutas de estudio visibles desde índices, CI final | No hay errores conocidos de compilación, invariantes, enlaces, navegación o separación |

**Regla de suficiencia para el integrador:** la **etapa 2** ya puede ser una versión académica *terminada* si presenta una expedición completa, dominio coherente, capas y pruebas deterministas. Las etapas 3 y 4 son enriquecimiento y cierre editorial, no una obligación de introducir los 23 patrones. La **alineación de los aislados** alcanza suficiencia al cerrar la etapa 0 y resolver únicamente hallazgos concretos, con un chequeo final después.

**No objetivos:** JavaFX, gráficos, física, combate, red, persistencia, multijugador, IA compleja, aleatoriedad libre, servicios HTTP, interfaz interactiva obligatoria, framework pesado, 23 patrones integrados por cuota, monolito de 23 demos conectadas, ni cambios a otros proyectos.

## 9. Cómo proceder sin olvidar tareas

Antes de cualquier commit del integrador, marcar en el plan cuál etapa se está ejecutando y qué demuestra. No mezclar en el mismo commit cambios del integrador y refactorizaciones de demos aisladas. Ante un conflicto, el contrato explícito del dominio prevalece; actualizar cada área afectada solo cuando corresponda.

**Próximo paso razonable:** desarrollar la **matriz de alineación editorial de los 23 aislados**, identificar cinco escenarios narrativos y definir la primera historia/diagrama de dominio del integrador. Después se puede iniciar la etapa 1 de Java **solo cuando la planificación esté suficientemente alineada**.
