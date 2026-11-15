# Escenarios 02–04 — Tres excursiones liminales adicionales

> **Implementados en Java 21, sin entrada por teclado.** Son guiones de presentación independientes que comparten el **mismo modelo y las mismas reglas** del dominio, pero crean mapas distintos. Complementan [el escenario 01 de oficinas](ESCENARIO_01.md); no reemplazan las 23 demostraciones GoF encapsuladas.

| Escenario | Cliente | Situación | Pregunta de ingeniería |
| --- | --- | --- | --- |
| **02 — Piscina interminable** | [ClientePiscinas.java](../src/main/java/liminal/integrado/presentacion/ClientePiscinas.java) | Dos salas de piscina con la misma apariencia y salidas diferentes | ¿Compartir un nombre significa compartir identidad? |
| **03 — Radio que responde sola** | [ClienteRadio.java](../src/main/java/liminal/integrado/presentacion/ClienteRadio.java) | Señales pregrabadas según habitación y frecuencia | ¿Dónde vive el estado de sintonía y cómo se valida? |
| **04 — Monstruo detrás de las luces** | [ClienteLuces.java](../src/main/java/liminal/integrado/presentacion/ClienteLuces.java) | La presencia del monstruo activa luz y alarma al entrar en una zona | ¿Cómo colaboran Strategy, Observer y Mediator sin acoplarse? |

Los tres clientes exponen `ejecutar(): List<String>`, que crea su propio escenario y devuelve un relato **inmutable y reproducible**; el `main` solo lo imprime. [PruebasGuiones.java](../src/test/java/liminal/integrado/PruebasGuiones.java) puede verificar el resultado sin capturar `System.out`.

## 02. La piscina interminable

~~~text
             x=0                x=1               x=2
y=0          vacío              P-05 Filtros       P-04 Escalera
y=1          P-01 Vestíbulo     P-02 Piscina       P-03 Piscina
~~~

P-02 y P-03 tienen el mismo nombre: **«Piscina de azulejos»**, pero poseen IDs y coordenadas distintos. La ruta principal es **P-01 → P-02 → P-03 → P-04**. Existe un desvío P-02 ↔ P-05.

**Guion:** ESTE, ESTE, OESTE rechazado, NORTE. El regreso desde P-03 no está permitido aunque P-02 esté justo al lado: **no hay salida dirigida inversa**. El rechazo deja la sesión en P-03 con turno 2; la expedición acaba en P-04 en el turno 3.

**Estudio:** identidad frente a apariencia, restricciones del grafo, rechazo atómico y objetos-valor. **No se introduce Prototype** únicamente porque existan habitaciones con nombres iguales.

## 03. La radio que responde sola

~~~text
             x=0                x=1                 x=2
y=0          vacío              R-04 Almacén        vacío
y=1          R-01 Recepción     R-02 Cabina         R-03 Salida
~~~

La clase [RadioLiminal.java](../src/main/java/liminal/integrado/dominio/comunicaciones/RadioLiminal.java) representa un receptor de **mensajes ficticios predefinidos** por identidad de habitación y frecuencia en kHz. No simula una red, ondas físicas ni un interlocutor real.

| Lugar y frecuencia | Respuesta |
| --- | --- |
| R-01, 1040 kHz | Solo estática |
| R-02, 1040 kHz | «¿Sigues ahí? No mires el reloj.» |
| R-02, 880 kHz | «Sube al almacén. Hay otra señal.» |
| R-04, 880 kHz | «Este mensaje fue grabado mañana.» |

**Guion:** desde R-01 ir al ESTE (R-02), sintonizar 880 kHz, ir al NORTE (R-04), volver al SUR (R-02) y salir por ESTE (R-03). Cuatro movimientos válidos, sin gastar turnos para sintonizar.

**Contrato R-INT-12:** una emisión exige habitación del mismo mapa, frecuencia positiva y texto no vacío; una combinación de habitación/frecuencia no se registra dos veces. Un rechazo no modifica catálogo ni frecuencia anterior. Una sala sin emisión retorna `Optional.empty()`.

**Estudio:** estado de dispositivo, validación antes de mutar, asociación con entidad por identidad. **No se añade Adapter o Bridge de relleno.**

## 04. El monstruo detrás de las luces

~~~text
             x=0                  x=1                  x=2
y=0          vacío                L-04 Cuarto negro     L-05 Escalera
y=1          L-01 Refugio         L-02 Corredor         L-03 Fluorescentes
~~~

El monstruo empieza en L-04 con `Patrullar`. El `SensorRuido` vigila L-03 y notifica al `CentroVigilancia`, que coordina `LuzAlerta` y `Alarma`.

**Guion:** NORTE rechazado; ESTE aceptado (el monstruo patrulla de L-04 a L-02); cambio a `Perseguir`; ESTE aceptado (el monstruo entra en L-03 y enciende la luz, **alarma +1**); cambio a `Esperar`; OESTE rechazado (no vuelve a sonar la alarma); NORTE aceptado hasta L-05 (fin, criatura inmóvil).

**Estudio:** Strategy propone un destino válido, Observer difunde un evento y Mediator coordina dos receptores. Un movimiento rechazado o una criatura inmóvil **no genera avisos**. No hay combate, físicas ni eventos arbitrarios.

## Cómo verificar y cuándo detenerse

El script [verificar.sh](../scripts/verificar.sh) compila Java 21, ejecuta **cuatro pruebas autónomas** y verifica los cuatro clientes (el de oficinas y estos tres). [PruebasGuiones.java](../src/test/java/liminal/integrado/PruebasGuiones.java) revisa reproducibilidad, rechazos, mensajes, fuentes ajenas y número de alarmas.

**Suficiencia:** cuatro escenas variadas, un dominio reutilizado y tres GoF integrados por necesidad. No hace falta crear más mapas, motores, interfaces, gráficos ni patrones por cuota. Cada escenario tiene un objetivo de estudio propio.
