# Escenario 01 — El ruido detrás de las oficinas

> **Escena implementada y probada en Java 21** mediante [ClienteExpedicion](../src/main/java/liminal/integrado/presentacion/ClienteExpedicion.java), [PruebasExpedicion](../src/test/java/liminal/integrado/PruebasExpedicion.java), [PruebasCriaturas](../src/test/java/liminal/integrado/PruebasCriaturas.java) y [PruebasVigilancia](../src/test/java/liminal/integrado/PruebasVigilancia.java). Incluye Strategy, Observer y Mediator, sin combate ni interfaz interactiva.

## 1. Escena narrativa

Un explorador despierta en un pasillo amarillo. Las luces fluorescentes repiten un zumbido que parece venir de una oficina vacía. Hay una salida segura identificada al noreste, pero el mapa no permite caminar libremente entre cualquier par de celdas vecinas.

El puesto de observación sirve como punto de partida del **monstruo oscuro M-01**. La sesión lo crea internamente antes del primer turno. Una oficina en H-03 tiene un sensor de movimiento que notifica una central. La puerta bloqueada no es el centro de esta escena: aquí se aprenden **mapa, identidad, turnos, Strategy, Observer y Mediator**, sin combate.

## 2. Mapa 4×3 — coordenadas absolutas

`x` crece hacia la derecha, `y` crece hacia abajo; (0,0) es esquina superior izquierda. Las celdas vacías son espacio no registrado, **no necesariamente paredes físicas**.

~~~text
          x=0                 x=1                 x=2                 x=3
    ┌───────────────────┬───────────────────┬───────────────────┬───────────────────┐
y=0 │      vacío        │ H-06 Observación  │ H-04 Señal       │ H-05 Salida      │
    ├───────────────────┼───────────────────┼───────────────────┼───────────────────┤
y=1 │ H-01 Pasillo      │ H-02 Cruce        │ H-03 Oficinas    │      vacío        │
    ├───────────────────┼───────────────────┼───────────────────┼───────────────────┤
y=2 │      vacío        │      vacío        │      vacío        │      vacío        │
    └───────────────────┴───────────────────┴───────────────────┴───────────────────┘
~~~

| ID | Nombre | Posición | Función didáctica |
| --- | --- | --- | --- |
| H-01 | Pasillo amarillo | (0,1) | Aparición inicial del explorador |
| H-02 | Cruce de fluorescentes | (1,1) | Bifurcación de salidas dirigidas |
| H-03 | Oficinas silenciosas | (2,1) | Sensor SR-03 y alerta por movimiento del monstruo |
| H-04 | Habitación de la señal | (2,0) | Último tramo antes de escapar |
| H-05 | Salida segura | (3,0) | Objetivo de la expedición |
| H-06 | Puesto de observación | (1,0) | Origen de la criatura M-01 |

## 3. Salidas dirigidas explícitas

| ID de salida | Origen | Dirección | Destino | Inversa |
| --- | --- | --- | --- | --- |
| S-01 | H-01 | ESTE | H-02 | S-02 |
| S-02 | H-02 | OESTE | H-01 | S-01 |
| S-03 | H-02 | ESTE | H-03 | S-04 |
| S-04 | H-03 | OESTE | H-02 | S-03 |
| S-05 | H-03 | NORTE | H-04 | **No se registra** |
| S-06 | H-04 | ESTE | H-05 | **No se registra** |
| S-07 | H-02 | NORTE | H-06 | S-08 |
| S-08 | H-06 | SUR | H-02 | S-07 |

**No hay salidas automáticas por estar en celdas contiguas.** Desde H-01, `NORTE` fracasa aunque esa dirección pueda corresponder a una coordenada válida del marco; no existe una salida.

## 4. Guion de prueba y resultado esperado

| Orden | Operación | Resultado | Ubicación | Turnos válidos | Estado |
| --- | --- | --- | --- | ---: | --- |
| 0 | Crear expedición EXP-01 | Preparada | H-01 | 0 | ACTIVA |
| 1 | NORTE | Rechazo sin salida desde H-01 | H-01 | 0 | ACTIVA |
| 2 | ESTE | Cruza S-01 | H-02 | 1 | ACTIVA |
| 3 | ESTE | Cruza S-03 | H-03 | 2 | ACTIVA |
| 4 | NORTE | Cruza S-05 | H-04 | 3 | ACTIVA |
| 5 | ESTE | Cruza S-06 | H-05 | 4 | FINALIZADA |
| 6 | OESTE | Rechazo por sesión terminada | H-05 | 4 | FINALIZADA |

El cliente **cuenta esta historia sin `Scanner`**. No hace falta que el estudiante la juegue. La **prueba** confirma que cada paso ocurre en la misma instancia de explorador y que ningún rechazo modifica el modelo; también verifica identidades, coordenadas, límites y salidas no inversas.

## 5. El monstruo oscuro y Strategy (etapa 2 implementada)

`MonstruoOscuro` M-01 comienza en H-06 con `Patrullar`. El cliente cambia su estrategia a `Perseguir` y luego a `Esperar` **sin crear otro monstruo**. El mapa es el mismo que usa el explorador; la clase y sus estrategias son **nuevas dentro del proyecto integrador**, sin importar el Strategy de los ejemplos GoF aislados.

| Acción del cliente | Explorador después | Estrategia activa | Monstruo después | Consecuencia |
| --- | --- | --- | --- | --- |
| NORTE rechazado | H-01 | Patrullar | H-06 | No avanza el turno ni la criatura |
| ESTE aceptado, turno 1 | H-02 | Patrullar | H-02 | Encuentro sin combate |
| ESTE aceptado, turno 2 | H-03 | Perseguir | H-03 | Persecución; SR-03 notifica a la central, luz encendida, alarma +1 |
| NORTE aceptado, turno 3 | H-04 | Esperar | H-03 | La criatura permanece quieta |
| ESTE aceptado, turno 4 | H-05, expedición finalizada | Esperar | H-03 | La criatura procesa la última acción válida |
| OESTE rechazado | H-05 | Esperar | H-03 | No hay quinto turno ni movimiento |

**Políticas deliberadamente pequeñas:** `Patrullar` elige la primera salida dirigida disponible en orden estable; `Perseguir` solo alcanza a un explorador que esté en un destino directamente conectado; `Esperar` conserva la ubicación. No hay búsqueda de camino ni daño por encuentros. `MonstruoOscuro` **valida todas las propuestas** antes de cambiar su ubicación, incluso si se inyecta una Strategy defectuosa.

**Límite de atomicidad:** `Expedicion` ejecuta el paso del explorador y, si procede, el del monstruo dentro de un mismo turno, pero **no promete rollback global**. Si una Strategy falla, el explorador conserva su paso válido y `AvanceExpedicion` informa el rechazo secundario. `ProcesarTurno` decide si comunicar movimiento al sensor. Una acción rechazada del explorador no inicia ninguna de las dos acciones posteriores.

Las [pruebas del monstruo](../src/test/java/liminal/integrado/PruebasCriaturas.java) verifican identidad, objetivos ajenos, no teletransporte, turno rechazado, vistas inmutables y control de estrategia por la sesión. [PruebasVigilancia](../src/test/java/liminal/integrado/PruebasVigilancia.java) cubre sensores, suscripciones durante callbacks, coordinación y ausencia de avisos si no hubo desplazamiento.

## 6. Observer + Mediator en H-03

El sensor `SR-03` observa únicamente la **misma instancia de habitación** H-03; no escucha físicamente sonidos. El caso de uso emite el evento después de confirmar que el monstruo **entró** en H-03 durante un movimiento válido:

1. `ProcesarTurno` comprueba `criaturaSeMovio` y `sensor.vigila(ubicacion)`.
2. `SensorRuido` genera un `EventoRuido` y notifica a los `ObservadorRuido` inscritos.
3. `CentroVigilancia`, que participa como observador y mediador, valida el sensor autorizado, enciende `LuzAlerta` y registra el evento en `Alarma`.
4. La consola narra el aviso. La misma señal no se repite cuando la criatura espera, cuando se rechaza el paso del explorador o después de la salida final.

**Decisión de diseño:** Observer evita que el sensor conozca al centro concreto; Mediator evita que el sensor coordine directamente luz y alarma. El callback ocurre después de confirmar el turno; si un observador lanza un error inesperado, no existe mecanismo de rollback global.

## 7. Pruebas de aceptación

- IDs duplicados o coordenadas ocupadas se rechazan sin registrar parcialmente.
- Fuera del mapa, coordenadas negativas y salidas diagonales se rechazan.
- La dirección inversa no se presupone; solo funciona si está registrada.
- Posición y turno no cambian si falla una acción.
- Cada éxito incrementa exactamente una unidad el turno.
- Alcanzar H-05 finaliza la expedición; acciones posteriores no alteran el resultado.
- Dos ejecuciones con el mismo mapa y guion producen la misma secuencia de resultados.
- No hay referencias a clases de las 23 demos liminales aisladas ni a Arena Java o MiniSO.

**Cierre académico final:** el cliente recorre H-01 → H-05, cambia tres estrategias sobre una misma criatura protegida por `Expedicion`, activa SR-03 exactamente al entrar el monstruo y registra una alerta por Mediator. El verificador `scripts/verificar.sh` y GitHub Actions comprueban el código en Linux y Windows. Cámaras, radio, inventarios y otros GoF **no son pendientes obligatorios**.
