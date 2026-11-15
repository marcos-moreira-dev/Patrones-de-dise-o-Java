# Contrato del dominio — Expedición Liminal Integrada

> **Estado:** reglas **R-INT-01…R-INT-11 implementadas y probadas** en Java 21. Strategy, Observer y Mediator colaboran sin combate, IA compleja ni persistencia. Rige primero el [dominio liminal común](../../colecciones/liminal/DOMINIO.md), reglas **R-00…R-14**. Estas reglas adicionales son decisiones locales **del integrador**, no afirmaciones de un canon de Backrooms.

## 1. Lenguaje ubicuo y alcance

**`MapaLiminal`**: mapa de una expedición, identificado, con tamaño finito, habitaciones y salidas. **`Habitacion`**: entidad con ID, nombre ambiental y posición. **`Posicion`**: valor cartesiano inmutable `(x, y)`, sin identidad propia. **`Salida`**: entidad identificada y conexión dirigida entre habitaciones. **`Explorador`**: entidad identificada con exactamente una habitación activa del mapa. **`Expedicion`**: sesión identificada, con explorador, meta, número de turnos válidos y resultado. **`Direccion`**: instrucción cardinal para pedir desplazamiento; no es orientación del cuerpo. **`MonstruoOscuro`**: entidad opcional de una expedición, cuya ubicación siempre permanece válida y cuya estrategia puede sustituirse sin crear otra criatura.

**No confundir conceptos:** una celda adyacente no es automáticamente una salida; un ID repetido no es una nueva entidad; una referencia a un artefacto no significa posesión; `record Posicion` no convierte a `Habitacion` en objeto-valor.

## 2. Reglas locales del integrador (R-INT)

| ID | Regla verificable | Caso válido | Caso de rechazo |
| --- | --- | --- | --- |
| **R-INT-01** | Un mapa tiene `ancho>0` y `alto>0`; `0 ≤ x < ancho` y `0 ≤ y < alto`, con origen arriba a la izquierda e `y` creciendo hacia abajo. Posición es un valor, no entidad | `(2, 1)` en un mapa 4×3 | `(4,1)` o coordenada negativa |
| **R-INT-02** | Cada habitación pertenece a una celda y tiene un ID único dentro del mapa; no puede haber dos habitaciones en la misma posición | H-01 en (0,1), H-02 en (1,1) | otro ID en (0,1) o repetir H-01 |
| **R-INT-03** | Cada salida tiene ID único, conecta habitaciones existentes del **mismo mapa** y es dirigida. En la primera expedición solo une celdas ortogonalmente adyacentes. A lo sumo una salida desde un origen por dirección | H-01 → ESTE → H-02 | destino diagonal, celda vacía, salida duplicada o inversa supuesta |
| **R-INT-04** | `NORTE/SUR/ESTE/OESTE` son direcciones **absolutas** del mapa: arriba/abajo/derecha/izquierda; no existe todavía orientación `mirando hacia...` | `ESTE` desde H-01 conduce a H-02 | un giro relativo de cámara inventado |
| **R-INT-05** | Una solicitud rechazada por falta de salida o estado incompatible conserva posición, turno y estado de expedición. **No consume turno** | desde H-01, `NORTE` rechazado; turno sigue 0 | mover o incrementar contador antes de validar |
| **R-INT-06** | Cada movimiento permitido consume **exactamente un turno**. Llegar a la meta H-05 finaliza la sesión; mover después es inválido y no altera el estado | H-01 → H-02 → H-03 → H-04 → H-05 = cuatro turnos | quinto movimiento después de la meta |
| **R-INT-07** | La criatura de etapa 2 tiene ID y posición válida, máximo una estrategia activa por instante; su estrategia **propone** un destino que el mapa valida. Encuentro en una habitación **no causa daño** ni derrota | patrullar desde un origen con salida existente | teletransportar a una celda sin salida |
| **R-INT-08** | Todas las acciones son síncronas y deterministas. `Expedicion.avanzar()` valida y mueve primero al explorador y solo **después de un éxito** procesa **una** acción de criatura, incluso al alcanzar la meta. `ProcesarTurno` comunica el resultado y después puede activar la vigilancia. Un paso rechazado no mueve la criatura ni dispara señales | repetir un guion reproduce la misma secuencia | criatura moviéndose por una acción rechazada o un hilo oculto |
| **R-INT-09** | Una sesión admite **a lo sumo una criatura**, creada internamente antes del primer turno y del **mismo mapa**. Ningún cliente recibe la instancia mutable: `estadoCriatura()` entrega un `record` inmutable. Cambiar Strategy se hace mediante la sesión | incorporar M-01 en H-06 y consultar su vista | criatura de otro mapa, segunda criatura, acceso mutable o incorporación tardía |
| **R-INT-10** | Un sensor vigila **una habitación por identidad**. La aplicación emite un `EventoRuido` **solo si el monstruo cambia de habitación y entra en la vigilada** después de un paso válido del explorador | M-01 llega a H-03 y se notifica SR-03 | evento por rechazo, por permanecer quieto o por otra habitación |
| **R-INT-11** | Observer comunica eventos de forma síncrona y con instantánea estable de destinatarios. `CentroVigilancia` (Mediator) acepta señales del sensor autorizado y coordina encender luz y registrar alarma, sin que el sensor conozca sus receptores | suscriptor central recibe SR-03, activa ambos | suscriptor duplicado, sensor no autorizado o alterar destinatarios de la ronda actual |

**Cobertura:** R-INT-01…R-INT-06 pertenecen al núcleo de etapa 1; R-INT-07…R-INT-09 corresponden a Strategy, coordinación y protección de la sesión; R-INT-10…R-INT-11 corresponden al escenario de vigilancia Observer/Mediator. La persecución es **local**: si el explorador no está en una salida directa de la criatura, `Perseguir` espera; `Patrullar` escoge la primera salida en orden estable y `Esperar` no avanza. Ni la proximidad geométrica ni una estrategia habilitan teletransportes.

## 3. Precondiciones, postcondiciones y excepciones

### Registrar una habitación

- **Pre:** mapa válido, ID y nombre no vacíos, posición dentro de límites, ID y celda libres.
- **Post si acepta:** una sola entrada nueva en el mapa, accesible por ID y coordenada.
- **Post si rechaza:** mapa sin cambios. Argumentos nulos → `NullPointerException`; duplicados, texto vacío o posición fuera de rango → `IllegalArgumentException`.

### Registrar una salida

- **Pre:** ID no vacío y libre; origen y destino previamente registrados; celdas adyacentes y cardinales; no existe ya salida desde origen en esa dirección.
- **Post si acepta:** una salida nueva y dirigida, **sin generar la inversa**.
- **Post si rechaza:** no se modifica ningún índice de salidas. Los datos de registro inválidos se rechazan con `IllegalArgumentException`.

### Procesar un movimiento del explorador

- **Pre:** dirección no nula, sesión activa y habitación actual perteneciente al mismo mapa; existe salida dirigida en la dirección solicitada, y cualquier mecanismo opcional permite pasar.
- **Post si acepta:** mismo explorador, una ubicación válida nueva, contador +1 y estado finalizado **solo** si se alcanza la meta.
- **Post si rechaza:** no cambia explorador, contador ni estado finalizado; falta de salida o sesión terminada → `IllegalStateException` en dominio. **La aplicación puede traducir ese rechazo a un resultado legible**, sin ocultar errores de programación inesperados.

### Avance de la criatura y fallos independientes

- **Pre:** el objetivo pertenece al mapa de la criatura y la estrategia activa no es nula.
- **Post de éxito:** la nueva ubicación es la misma habitación o uno de los destinos de salidas dirigidas existentes.
- **Post de rechazo:** una propuesta fuera de esas opciones produce `IllegalStateException` **sin mover a la criatura**; objetivo de otro mapa → `IllegalArgumentException`.
- **Coordinación:** `Expedicion.avanzar` procesa **dos acciones consecutivas del dominio** y produce `AvanceExpedicion`. Un movimiento válido del explorador no se deshace porque la propuesta posterior de la criatura sea inválida; el valor devuelto informa ese fallo por separado. Una solicitud rechazada del explorador no intenta ninguna acción de criatura. El caso de uso traduce estos datos sin adquirir una referencia mutable al monstruo.

### Aviso de vigilancia

- **Pre:** sensor y habitación vigilada existentes; solo `ProcesarTurno` decide llamar `detectarMovimiento` tras confirmar `criaturaSeMovio` y la coincidencia de habitación.
- **Post:** Observer entrega `EventoRuido` a una instantánea de observadores; Mediator valida la ID del sensor, enciende luz y registra el aviso en alarma.
- **Sin retroactividad:** el aviso no consume turno adicional ni modifica la posición del explorador o de la criatura.
- **Límite:** los callbacks son síncronos; una excepción inesperada en un observador puede propagarse **después** de confirmar el turno. El proyecto no promete rollback ni reintentos automáticos de notificaciones. Esa política sería otra decisión de diseño, no un comportamiento GoF implícito.

Las excepciones no deben usarse para inventar reglas de negocio; no se crean jerarquías de excepciones sin necesidad real.

## 4. Qué propiedades pertenecen a cada responsabilidad

| Responsabilidad | Propietario conceptual | No debe hacer |
| --- | --- | --- |
| Identidad de habitación y coordenada fija | `Habitacion` | Imprimir consola o determinar la victoria |
| Registro de habitaciones y salidas válidas | `MapaLiminal` | Decidir el turno o la estrategia de la criatura |
| Ubicación actual del personaje | `Explorador` | Registrar mapa o almacenar las habitaciones de todo el mundo |
| Meta, turno válido, finalización y propiedad de la criatura activa | `Expedicion` | Parsear comandos, decidir cómo avisar al sensor o crear efectos visuales |
| Traducción del avance y coordinación del sensor | `ProcesarTurno` (aplicación) | Repetir las reglas de movimiento que ya protege `Expedicion` |
| Preparación de dependencias y relato de consola | `ClienteExpedicion` | Manipular campos privados o realizar reglas del dominio |
| Estrategia de movimiento del monstruo | `EstrategiaMovimiento` propone, `MonstruoOscuro` valida | Saltarse el mapa o decidir los turnos del explorador |
| Distribución de señales | `SensorRuido` / `ObservadorRuido` | Conocer directamente la luz y la alarma |
| Coordinación de dispositivos | `CentroVigilancia` / `LuzAlerta` / `Alarma` | Acoplar cada dispositivo a todos los demás |

**Identidad y ciclo de vida:** `MapaLiminal` mantiene referencias a habitaciones y salidas, pero tener colecciones Java no implica por sí solo composición UML. El modelo deliberadamente no exige destruir habitaciones al cerrar una expedición. La sesión guarda referencias a explorador y criatura (si existe). La criatura **pertenece al límite de mutación de la sesión**, pero esto no permite deducir por sí solo su composición UML ni el ciclo de vida de JVM; el cierre es **lógico**, no una orden al recolector de basura.

## 5. Referencias normativas y decisiones aplazadas

- **R-01/R-02:** IDs únicos por colección e integridad de mapa.
- **R-03/R-04:** ubicación única y cruce válido mediante salida dirigida.
- **R-05:** si se introducen puertas, respetarán tres estados. **No se agregan puertas en la primera implementación**, para evitar repetir innecesariamente su mecánica.
- **R-06/R-07/R-09:** reservadas para artefactos, dispositivos y registros en extensiones justificadas.
- **R-08:** monstruo sin daño ni combate; Strategy protege ubicación.
- **R-10:** `record Posicion` puede compararse por valor; las habitaciones siguen siendo entidades.
- **R-11/R-12:** reproducción determinista y rechazo atómico.
- **R-13/R-14:** la estética no determina mecánicas y GoF no define las leyes del mundo.

**Trazabilidad implementada:** `MapaLiminal` valida ID/posición/salidas y ofrece destinos dirigidos en orden estable e inmodificables; `Explorador` solo se mueve desde `Expedicion` (método de acceso de paquete); `Expedicion` crea y conserva su criatura, controla turnos y devuelve `AvanceExpedicion` y vistas `EstadoCriatura` sin exponer el objeto mutable; `MonstruoOscuro` valida el destino de Strategy antes de mutar; `ProcesarTurno` conecta el avance con `SensorRuido`, y `CentroVigilancia` coordina luz/alarma por Observer + Mediator. Cambiar invariantes R-INT requiere actualizar este documento, el escenario y sus pruebas en un mismo trabajo.
