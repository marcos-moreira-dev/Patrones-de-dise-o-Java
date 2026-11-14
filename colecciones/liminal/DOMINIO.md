# Expedición Liminal — DOMINIO.md

> **Contrato normativo del dominio, versión 1.0 (2026-11-13).** Estas reglas son decisiones didácticas del proyecto, no afirmaciones de que exista un canon único de Backrooms. Los ejercicios no pueden contradecirlas sin declarar una excepción específica y motivada.
>
> **Apoyo no normativo:** el [atlas visual](GUIA_VISUAL.md) ilustra pasillos, piscinas y oficinas con imágenes generadas. Su aspecto no crea nuevas reglas.

## 1. Propósito y límites

El sistema simula pequeñas situaciones de **exploración de espacios liminales** para enseñar patrones de diseño GoF con Java 21, diagramas UML y salidas por consola. Sus referencias visuales incluyen Backrooms, Dreamcore, Weirdcore y *The Complex: Expedition*. No se recrean mecánicas, mapas, diálogos ni material gráfico protegido de otros juegos.

**Dentro del dominio:** habitaciones, conexiones, puertas, exploradores, objetos, luces, mecanismos, señales, criaturas, registro narrativo y acciones discretas.

**Fuera del dominio base:** renderizado 2D/3D, JavaFX, red, autenticación real, persistencia, físicas, colisiones, combates, multijugador y simulación continua del tiempo. Un patrón puede simular una capacidad local sin convertirla en obligación global.

**Regla de autoridad R-00:** prevalecen las reglas escritas en este documento sobre interpretaciones de internet y sobre la conveniencia de cualquier patrón. Un patrón no define qué es verdad en el dominio.

## 2. Diccionario obligatorio

| Concepto / posible nombre Java | Definición | Identidad |
| --- | --- | --- |
| `Expedicion` | Ejecución autocontenida de una exploración | ID propio |
| `MapaLiminal` | Conjunto de habitaciones y salidas de la expedición | ID propio |
| `Nivel` | Agrupación temática de habitaciones (amarillo, piscinas, oficinas) | Identidad opcional; no implica propiedad UML |
| `Habitacion` | Lugar distinguible e identificable en el mapa | ID único dentro del mapa |
| `Salida` | Conexión **dirigida** entre dos habitaciones | ID único; origen y destino válidos |
| `Puerta` | Mecanismo que puede impedir el tránsito por una salida | ID propio; estado explícito |
| `Explorador` | Personaje con ubicación actual | ID propio |
| `Artefacto` | Objeto del mundo; puede ser portable o fijo | ID propio si se mueve o modifica |
| `Inventario` | Colección de artefactos portátiles que lleva un explorador | No duplica artefactos |
| `Linterna`, `Llave`, `Radio`, `Camara` | Tipos reconocibles de artefacto | Sus características dependen del ejemplo |
| `Luz` / `Interruptor` / `Alarma` | Dispositivos de señalización y control | Identidad propia |
| `MonstruoOscuro` | Criatura ficticia de comportamiento limitado | ID propio |
| `RegistroExpedicion` | Conjunto de entradas creadas para una expedición | ID propio |
| `EntradaRegistro` | Observación perteneciente a un registro | Identidad local del registro |

**Nombres oficiales:** `Habitacion`, no alternar arbitrariamente con `Sala` o `Cuarto`. `MonstruoOscuro` evita exigir conocimiento de nombres especializados de comunidades en internet. Los nombres de los **patrones** se mantienen en inglés: `Strategy`, `Observer`, etc.

Las imágenes representan **ambientes** y **objetos**; no establecen mecánicas nuevas por sí solas.

## 3. Reglas fundamentales verificables

### R-01. Identidad

Cada `Habitacion`, `Salida`, `Puerta`, `Explorador` y entidad activa tiene identificador no nulo, no vacío y único **dentro de su colección de identidad** en una expedición. Dos objetos con igual descripción pueden ser entidades distintas.

- **No válido:** registrar dos habitaciones con ID `H-01` dentro del mismo mapa.
- **Prueba:** el mapa rechaza el segundo ID.

### R-02. Integridad del mapa

Toda `Salida` conecta una habitación **origen** existente con una habitación **destino** existente del mismo `MapaLiminal`. Puede no existir una conexión inversa. La geometría representada en una ilustración no determina el recorrido.

- **Formal:** `∀ s ∈ Salidas, origen(s) ∈ Habitaciones ∧ destino(s) ∈ Habitaciones`.
- **No válido:** salida de H-01 a H-99 si H-99 no existe.
- **Prueba:** el alta de la salida inválida es rechazada.

### R-03. Ubicación única

Todo explorador **activo** en una expedición tiene exactamente una habitación actual y esa habitación existe en el mapa de su expedición.

- **Formal:** `∀ e ∈ ExploradoresActivos, ubicacion(e) ∈ HabitacionesDelMapa`; `|ubicacionesActuales(e)| = 1`.
- **No válido:** explorador simultáneamente en H-01 y H-02.
- **Prueba:** al moverse se actualiza la misma instancia de explorador; no se clona.

### R-04. Movimiento

`mover(destino)` solo puede completarse si existe una `Salida` desde la ubicación actual hacia `destino`, y su puerta opcional permite atravesar. Una salida sin puerta es transitable.

- **Precondiciones:** salida válida y puerta abierta, si existe.
- **Postcondición de éxito:** `ubicacion(explorador) = destino`.
- **Postcondición de rechazo:** ubicación y objetos involucrados conservan su estado.
- **Pruebas:** ruta válida, ruta inexistente, puerta cerrada, puerta bloqueada.

### R-05. Estados de una puerta

Una puerta tiene exactamente un estado de `{ABIERTA, CERRADA, BLOQUEADA}`. Nunca tiene dos simultáneamente.

| Operación | Estado inicial válido | Resultado |
| --- | --- | --- |
| `abrir` | CERRADA | ABIERTA |
| `cerrar` | ABIERTA | CERRADA |
| `bloquear` | CERRADA | BLOQUEADA |
| `desbloquear` con llave autorizada | BLOQUEADA | CERRADA |
| `atravesar` | ABIERTA | Tránsito permitido, estado permanece |
| Operación no admitida | Cualquier otro caso | Rechazo sin mutación |

La llave **no se consume** al desbloquear, salvo que una regla local lo declare explícitamente. Si una puerta no tiene llave, se puede dejar sin bloqueo en el ejemplo.

### R-06. Artefactos portátiles y referencias

Un artefacto portable **físicamente** puede encontrarse en una sola habitación o en el inventario de un único explorador a la vez, no en ambos. Otros objetos pueden **referenciarlo** para consultas, informes o notificaciones; eso no duplica ni transfiere su posesión.

- **Formal:** `|lugaresFisicos(a)| ≤ 1` para cada artefacto portable `a`.
- **No válido:** la misma linterna está físicamente en dos inventarios.
- **Válido:** dos observadores mantienen referencias de lectura a esa linterna.
- **Prueba:** transferir primero retira al propietario anterior; añadir a dos inventarios sin transferencia se rechaza.

No se exige que todos los objetos sean portables: una luz fija de una habitación no entra en un inventario ordinario.

### R-07. Dispositivos y avisos

Una `Luz` está ENCENDIDA o APAGADA. Un `Interruptor` puede solicitar cambios a uno o más dispositivos compatibles. `Alarma` emite avisos, pero no cambia arbitrariamente la ubicación de los exploradores.

- Un dispositivo puede ser observado por varios interesados.
- **Suscripción/observación no implica propiedad UML.**
- **Prueba:** un evento del interruptor notifica a dos receptores sin crear copias de estos.

### R-08. Criaturas y comportamientos

Una criatura activa tiene una ubicación válida en el mapa. Puede estar en la misma habitación que un explorador. Cuando se configura desplazamiento por estrategia, tiene **a lo sumo una estrategia activa por instante**; cambiarla no duplica la criatura.

- Los comportamientos candidatos son patrullar, perseguir y esperar.
- No se presupone daño, combate ni muerte.
- **Prueba:** cambiar de estrategia conserva identidad y ubicación válida.

### R-09. Registro narrativo y composición

Cada `EntradaRegistro` activa pertenece a **exactamente un** `RegistroExpedicion`. Una entrada no puede ser parte compuesta de dos registros simultáneamente. El registro es su propietario en este modelo.

- **Formal:** `∀ a ∈ EntradasActivas, |propietariosCompuestos(a)| = 1`.
- En este dominio, al eliminar un registro del modelo se eliminan sus entradas asociadas del modelo; esto **no es una instrucción al recolector de basura**.
- Un informe puede conservar una referencia de lectura a una entrada sin ser su propietario.
- **Prueba:** rechazar insertar la misma entrada como parte propia de otro registro; comprobar eliminación lógica conjunta.

### R-10. Identidad frente a copias

Crear un objeto con `new` no determina composición. Añadirlo a `List` no lo copia. Si un ejercicio utiliza Prototype, el clon de una **plantilla** recibe identidad nueva cuando se convierte en entidad activa; nunca se duplican inadvertidamente IDs de habitaciones.

- **Prueba:** dos instancias copiadas como entidades deben tener IDs diferentes; una referencia repetida sigue apuntando a la misma instancia.

### R-11. Tiempo y reproducibilidad

La simulación básica avanza por **acciones discretas**, sin relojes reales, hilos, temporizadores ni aleatoriedad implícita. Un ejercicio con azar debe proporcionar una fuente determinista o una semilla fija para probarlo.

- **Prueba:** dos ejecuciones de una demo determinista producen resultados funcionales equivalentes.

### R-12. Fallos controlados y atomicidad conceptual

Una operación inválida no debe dejar una transición de dominio **a medias**. Debe rechazar la acción de forma observable (valor de resultado o excepción documentada) y preservar los invariantes.

- **Prueba:** al fallar una transferencia de artefacto, no queda registrado en dos inventarios ni desaparece de ambos.

### R-13. Escenarios intercambiables

Las habitaciones amarillas, piscinas vacías, oficinas y hoteles son **estéticas o configuraciones**. No cambian por sí solas las reglas globales de ubicación, identidad y movimiento. Una regla nueva (por ejemplo, una salida que teletransporta) es un **caso local documentado**.

- **Prueba:** el mismo contrato de movimiento se cumple en dos ambientaciones distintas.

### R-14. Los patrones no forman parte de la ficción

`FactoryMethod`, `Observer` o `Composite` son **decisiones de diseño del software**, no leyes del mundo. Cada demostración puede escoger colaboraciones distintas, siempre que respete R-01 a R-13.

- **No válido:** afirmar «toda habitación contiene un Singleton» solo para demostrar Singleton.
- **Válido:** elegir un gestor compartido dentro del alcance de una demo y explicar por qué se eligió esa organización.

## 4. Relaciones UML: cuándo declararlas

| Vínculo conceptual | Representación razonable | Precaución |
| --- | --- | --- |
| Explorador → habitación actual | Asociación navegable; una ubicación actual por explorador activo | No significa que el explorador cree la habitación |
| Mapa → habitaciones | Asociación con multiplicidad; una composición solo si se define realmente propiedad y ciclo de vida | No inferir rombo negro por usar `List` |
| Registro → entradas propias | **Composición**, fundamentada por R-09 | Referencias externas no significan otro propietario |
| Equipo de exploradores → exploradores (si aparece en una demo) | Asociación o agregación compartida *si* el dominio local la define | Agregación no se deduce de una colección |
| Interruptor → receptores | Asociación / dependencias según el diseño | Notificar no equivale a ser dueño |
| Criatura → estrategia | Asociación al contrato de estrategia; realización de sus implementaciones | Strategy no requiere agregación |
| Puerta → estado | Asociación/realización según patrón State | UML refleja la implementación elegida sin cambiar R-05 |

**Regla UML:** nunca forzar rombos de agregación/composición para «mostrar más UML». Solo se dibujan cuando las decisiones semánticas lo justifican; leer además [UML_GUIDE.md](../../UML_GUIDE.md).

## 5. Reglas locales de los patrones

Una demo puede introducir `R-LOCAL-<patron>-NN` si lo necesita. Debe informar:

- **Propósito:** qué se quiere demostrar.
- **Ámbito:** solo ese ejemplo, no todo el mundo.
- **Regla explícita:** qué se permite/prohíbe.
- **Relación con R-01…R-14:** no contradicción.
- **Prueba:** mínimo una situación válida y una inválida o límite.

Ejemplo: `R-LOCAL-OBSERVER-01`: un interruptor controla dos luces concretas en esa escena. **Esto no significa** que *todos* los interruptores del dominio tengan exactamente dos luces.

## 6. Glosario de decisiones y cambios

- `V1.0`: reglas iniciales; versiones posteriores requieren explicación del cambio, impacto sobre clases, UML y pruebas.
- **Ninguna imagen es prueba de una regla.** Las ilustraciones son apoyo de contexto, no especificación ejecutable.
- Una contradicción debe registrarse y resolverse **antes** de escribir o ajustar la implementación para ocultarla.

## 7. De este documento al código

Antes de programar cada patrón:

1. Seleccionar reglas R-XX pertinentes.
2. Formular la escena en 2–5 frases, con nombres del glosario.
3. Identificar clase cliente y responsabilidades.
4. Elegir el patrón por la colaboración que resuelve un problema de diseño.
5. Dibujar UML mínimo (con multiplicidades y rombos solo si están justificados).
6. Implementar Java 21 con código breve, comentarios discretos.
7. Ejecutar por consola y probar éxito, rechazo e invariantes.
8. Añadir imagen inspiradora **solo después**, con licencia clara y estética coherente.

Para aprender a derivar estas decisiones, consultar [Cómo definir un dominio](../../docs/modelado/COMO_DEFINIR_UN_DOMINIO.md). Para el inventario de patrones y la estructura, consultar [PLAN.md](PLAN.md).
