# Escenario 01 — El ruido detrás de las oficinas

> **Etapas 1 y 2 implementadas en Java 21 y probadas** mediante [ClienteExpedicion](../src/main/java/liminal/integrado/presentacion/ClienteExpedicion.java), [PruebasExpedicion](../src/test/java/liminal/integrado/PruebasExpedicion.java) y [PruebasCriaturas](../src/test/java/liminal/integrado/PruebasCriaturas.java). La secuencia es pequeña, determinista y sin combate.

## 1. Escena narrativa

Un explorador despierta en un pasillo amarillo. Las luces fluorescentes repiten un zumbido que parece venir de una oficina vacía. Hay una salida segura identificada al noreste, pero el mapa no permite caminar libremente entre cualquier par de celdas vecinas.

El puesto de observación sirve como punto de partida del **monstruo oscuro M-01**. El cliente lo registra antes del primer turno. La puerta bloqueada no es el centro de esta escena: aquí se aprenden **mapa, identidad, conexiones, turnos y Strategy**, sin combate.

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
| H-03 | Oficinas silenciosas | (2,1) | Contraste ambiental; futuro ruido |
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
| ESTE aceptado, turno 2 | H-03 | Perseguir | H-03 | Persecución por una salida existente |
| NORTE aceptado, turno 3 | H-04 | Esperar | H-03 | La criatura permanece quieta |
| ESTE aceptado, turno 4 | H-05, expedición finalizada | Esperar | H-03 | La criatura procesa la última acción válida |
| OESTE rechazado | H-05 | Esperar | H-03 | No hay quinto turno ni movimiento |

**Políticas deliberadamente pequeñas:** `Patrullar` elige la primera salida dirigida disponible en orden estable; `Perseguir` solo alcanza a un explorador que esté en un destino directamente conectado; `Esperar` conserva la ubicación. No hay búsqueda de camino ni daño por encuentros. `MonstruoOscuro` **valida todas las propuestas** antes de cambiar su ubicación, incluso si se inyecta una Strategy defectuosa.

**Límite de atomicidad:** cada acción del dominio tiene sus propias pre/postcondiciones. Si falla la segunda acción (criatura), el movimiento previamente confirmado del explorador **sigue aceptado** y el error se informa por separado; no hay rollback del turno completo. Una acción rechazada del explorador nunca inicia la de la criatura.

Las [pruebas del monstruo](../src/test/java/liminal/integrado/PruebasCriaturas.java) verifican identidad, objetivos ajenos, prohibición de teletransporte, turno rechazado, meta, reemplazo de estrategia y reproducibilidad sin hilos.

## 6. Pruebas de aceptación previas al código

- IDs duplicados o coordenadas ocupadas se rechazan sin registrar parcialmente.
- Fuera del mapa, coordenadas negativas y salidas diagonales se rechazan.
- La dirección inversa no se presupone; solo funciona si está registrada.
- Posición y turno no cambian si falla una acción.
- Cada éxito incrementa exactamente una unidad el turno.
- Alcanzar H-05 finaliza la expedición; acciones posteriores no alteran el resultado.
- Dos ejecuciones con el mismo mapa y guion producen la misma secuencia de resultados.
- No hay referencias a clases de las 23 demos liminales aisladas ni a Arena Java o MiniSO.

**Cierre académico básico (etapas 1 y 2):** el cliente recorre H-01 → H-05, alterna tres estrategias sobre una misma criatura, conserva las ubicaciones válidas y los turnos frente a rechazos, y alcanza la salida. El verificador independiente `scripts/verificar.sh` y GitHub Actions comprueban ambas etapas en Linux y Windows. Cámaras, radio, inventarios y otros GoF son ampliaciones opcionales.
