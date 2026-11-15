# Escenario 01 — El ruido detrás de las oficinas

> **Etapa 1 implementada en Java 21 y probada** mediante [ClienteExpedicion](../src/main/java/liminal/integrado/presentacion/ClienteExpedicion.java) y [PruebasExpedicion](../src/test/java/liminal/integrado/PruebasExpedicion.java). La secuencia es pequeña y determinista; la criatura de etapa 2 aún no está implementada.

## 1. Escena narrativa

Un explorador despierta en un pasillo amarillo. Las luces fluorescentes repiten un zumbido que parece venir de una oficina vacía. Hay una salida segura identificada al noreste, pero el mapa no permite caminar libremente entre cualquier par de celdas vecinas.

Una posición secundaria, el puesto de observación, sirve como punto de partida del **monstruo oscuro de la etapa 2**. En la etapa 1 no se crea ninguna criatura todavía. La puerta bloqueada no es el centro de esta escena: aquí se aprenden primero **mapa, identidad, conexiones y turnos**.

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
| H-06 | Puesto de observación | (1,0) | Ruta secundaria y origen futuro de criatura |

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

## 5. Cuando entre el monstruo (etapa 2)

El `MonstruoOscuro` M-01 comienza en H-06 y puede alternar entre **patrullar, perseguir o esperar**, conservando identidad. Su comportamiento trabaja sobre el **mismo mapa integrado**, pero no se importa desde `colecciones/liminal/comportamiento/strategy`.

Para conservar reproducibilidad, el simulador procesará una acción de criatura **después de un turno permitido** y nunca ante un intento rechazado. Una criatura y un explorador pueden coincidir en una habitación; no hay combate ni daño. La escena debe mostrar indicios narrativos sin requerir un sistema de percepción complejo.

No es una exigencia de la primera entrega: el dominio y los turnos deben quedar correctos **antes** de añadir al monstruo.

## 6. Pruebas de aceptación previas al código

- IDs duplicados o coordenadas ocupadas se rechazan sin registrar parcialmente.
- Fuera del mapa, coordenadas negativas y salidas diagonales se rechazan.
- La dirección inversa no se presupone; solo funciona si está registrada.
- Posición y turno no cambian si falla una acción.
- Cada éxito incrementa exactamente una unidad el turno.
- Alcanzar H-05 finaliza la expedición; acciones posteriores no alteran el resultado.
- Dos ejecuciones con el mismo mapa y guion producen la misma secuencia de resultados.
- No hay referencias a clases de las 23 demos liminales aisladas ni a Arena Java o MiniSO.

**Cierre de etapa 1:** existe código Java 21 que recorre H-01 → H-05, conserva posición y turnos frente a rechazos y termina al alcanzar la meta. El verificador independiente `scripts/verificar.sh` y GitHub Actions ejecutan sus pruebas en Linux y Windows. **Siguiente:** incorporar al monstruo oscuro mediante Strategy, sin comprometer el dominio ni añadir combate.
