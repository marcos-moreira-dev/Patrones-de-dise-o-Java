# Auditoría de comportamientos — Dominio frente a narración

> **15 de noviembre de 2026.** Revisión dirigida de los **21 archivos Java en `dominio/`** del proyecto integrado, más sus cuatro clientes. Se comprobó la ausencia de `System.out`, `TODO`, `FIXME` y `return null;` en esos archivos de dominio. **No es una prueba formal de ausencia de todos los defectos**, ni exige que cada clase sea mutable o tenga acciones teatrales.

## 1. La pregunta central

**¿Un foco simplemente escribe «estoy encendido» o cambia su estado?** En este proyecto, [`LuzAlerta.encender()`](../src/main/java/liminal/integrado/dominio/vigilancia/LuzAlerta.java) hace `encendida = true`; [`encendida()`](../src/main/java/liminal/integrado/dominio/vigilancia/LuzAlerta.java) lo consulta. No imprime nada. Después, el cliente puede narrar **un cambio comprobado** desde apagada a encendida. Repetir `encender()` deja el mismo estado, sin fabricar otro encendido narrativo.

Un comportamiento real no necesita ser visualmente complejo: **mutar un estado válido, consultar información o propagar un evento de dominio ya es comportamiento**. Un método que solo imprime una frase y no cambia ni informa nada sería, en cambio, escaso como modelo.

## 2. Inventario de las principales responsabilidades y métodos

| Objeto | Operaciones que sí afectan al mundo | Consultas o datos | ¿Qué no hace? |
| --- | --- | --- | --- |
| [`MapaLiminal`](../src/main/java/liminal/integrado/dominio/mundo/MapaLiminal.java) | `agregarHabitacion`, `agregarSalida`, con validaciones | `habitacion`, `salidaDesde`, `destinosDesde`, contadores | No narra ni inventa conexión inversa |
| [`Habitacion`](../src/main/java/liminal/integrado/dominio/mundo/Habitacion.java) | Ninguna tras construirse | ID, nombre y posición | No es una puerta ni un motor de eventos |
| [`Salida`](../src/main/java/liminal/integrado/dominio/mundo/Salida.java) | Ninguna tras construirse | Origen, destino y dirección | No tiene `abrir()` porque es conexión dirigida, no puerta física |
| [`Explorador`](../src/main/java/liminal/integrado/dominio/exploracion/Explorador.java) | `mover` con acceso de paquete y validación del mapa | Ubicación, identidad | No permite que el cliente salte los turnos |
| [`Expedicion`](../src/main/java/liminal/integrado/dominio/exploracion/Expedicion.java) | `incorporarCriatura`, `cambiarEstrategiaCriatura`, `avanzar` | Turnos, finalización, `estadoCriatura` | No entrega al monstruo mutable ni imprime |
| [`MonstruoOscuro`](../src/main/java/liminal/integrado/dominio/criaturas/MonstruoOscuro.java) | `cambiarEstrategia`, `avanzar`; valida propuesta | ID, ubicación | No se teletransporta, no combate y no tiene `desaparecer()` |
| [`RadioLiminal`](../src/main/java/liminal/integrado/dominio/comunicaciones/RadioLiminal.java) | `sintonizar`, `registrarMensaje` | `frecuenciaKhz`, `escucharEn` | No conversa en vivo ni reproduce audio |
| [`SensorRuido`](../src/main/java/liminal/integrado/dominio/vigilancia/SensorRuido.java) | `suscribir`, `desuscribir`, `detectarMovimiento` (emite evento) | `vigila`, `cantidadObservadores` | No conoce la luz ni simula sonido físico |
| [`CentroVigilancia`](../src/main/java/liminal/integrado/dominio/vigilancia/CentroVigilancia.java) | `alDetectar` coordina receptores | ID | No genera por sí mismo el movimiento |
| [`LuzAlerta`](../src/main/java/liminal/integrado/dominio/vigilancia/LuzAlerta.java) | `encender` modifica un booleano | `encendida` | No tiene `apagar` ni `parpadear` |
| [`Alarma`](../src/main/java/liminal/integrado/dominio/vigilancia/Alarma.java) | `avisar` incrementa contador y guarda evento | `avisos`, `ultimo` | No emite sonido real |
| **Records** (`Posicion`, `EstadoCriatura`, `AvanceExpedicion`, `EventoRuido`, `ResultadoTurno`) | Validaciones en construcción; sin mutadores | Valores y resultados | No son esqueletos vacíos: su rol deliberado es transportar datos inmutables |

Las interfaces, como `EstrategiaMovimiento` y `ObservadorRuido`, declaran operaciones **abstractas intencionalmente**; no son métodos `TODO`. `Patrullar`, `Perseguir` y `Esperar` contienen políticas concretas y deterministas.

## 3. El mundo cambia primero; el cliente relata después

| Escena | Hecho del dominio | Narración nueva derivada de ese hecho | Prueba |
| --- | --- | --- | --- |
| [Oficinas](../src/main/java/liminal/integrado/presentacion/ClienteExpedicion.java) | El monstruo cambia de habitación; la central pasa de luz apagada a encendida | «Una silueta abandona H-06 y alcanza H-02»; «La luz de alerta se enciende» | Misma secuencia en dos ejecuciones; **2** desplazamientos de criatura, **1** encendido narrado |
| [Piscinas](../src/main/java/liminal/integrado/presentacion/ClientePiscinas.java) | P-02 y P-03 conservan identidad diferente; el regreso OESTE es rechazado y no consume turno | «Los azulejos parecen iguales, pero ahora estás en P-03»; «Sigues en P-03» | El relato mantiene P-03 y el turno 2 ante el rechazo |
| [Radio](../src/main/java/liminal/integrado/presentacion/ClienteRadio.java) | `escucharEn` no encuentra emisión; `sintonizar` cambia de 1040 a 880 kHz | Estática sin emisión registrada; cambio de frecuencia sin moverse de R-02 | Mensajes y frecuencias comparados; **1** cambio narrado |
| [Luces](../src/main/java/liminal/integrado/presentacion/ClienteLuces.java) | Criatura entra en L-03, el sensor notifica y la central cambia estado de luz/alarma | «La sombra se desplaza»; «Los fluorescentes de alerta se encienden» | **2** movimientos narrados y **1** encendido, ninguno inventado tras rechazo |

Los cuatro clientes devuelven `List<String>` inmutable mediante `ejecutar()`; `main()` solo imprime cada línea. [`PruebasGuiones`](../src/test/java/liminal/integrado/PruebasGuiones.java) compara dos ejecuciones de cada relato y verifica las frases sensibles. El resto de pruebas comprueba los **estados reales**, no solo las cadenas.

Se admite una **descripción ambiental estática** («zumbido fluorescente», «piscina de azulejos») como narrativa de ficción, pero no se afirma que el simulador modele ondas sonoras, parpadeos o cambios físicos no implementados. Las **frases que describen transiciones** sí deben derivarse de estados o resultados.

## 4. Acciones que se consideraron y no se fingieron

- **Apagar un foco:** actualmente no existe `LuzAlerta.apagar()`. Tendría sentido si una escena exigiera `encendida = false`, una transición observada y una prueba. Añadirlo por simetría no es obligatorio.
- **Parpadear:** requeriría un estado o evento explícito de intermitencia y una semántica reproducible. Escribir «parpadea» sin tal evento sería una representación engañosa.
- **Abrir una puerta:** en el [patrón State aislado](../../../colecciones/liminal/src/main/java/liminal/comportamiento/state/Puerta.java) sí existen `abrir`, `cerrar`, `bloquear`, `desbloquear`. En **este integrador** las `Salida` son enlaces dirigidos, no puertas físicas. Importar la demo State para adornar este mapa rompería la separación.
- **Aparecer/desaparecer monstruo:** `Expedicion.incorporarCriatura` ya añade una entidad antes del primer turno; no es un mecanismo de aparición/desaparición dinámica. Si se exigiera, habría que definir cuándo ocurre, efectos sobre el turno y cómo lo observan los clientes.
- **Reproducir radio y alarma:** hoy hay **mensajes y eventos**, no audio. Su valor didáctico está en estado, referencias, notificaciones y coordinación.

## 5. Conclusión y regla de parada

**Resultado de esta pasada:** no se identificaron métodos de dominio que se limiten a `System.out.println` o marcadores `TODO/FIXME` en los 21 archivos inspeccionados. Se mejoró **la narración de los cuatro guiones** y se reforzaron sus pruebas sin agregar nuevas reglas ni patrones GoF. Los **tres bloques del repositorio siguen independientes**.

**Suficiente:** el código produce cambios de estado y eventos verificables; la presentación relata los resultados sin simular mecánicas inexistentes. No es necesario que cada objeto tenga un método teatral, ni introducir gráficos, físicas o un juego interactivo para estudiar arquitectura orientada a objetos.

**Ejecución automatizada:** [`scripts/verificar.sh`](../scripts/verificar.sh) compila Java 21 y ejecuta las pruebas. El resultado definitivo de cada commit debe confirmarse en [GitHub Actions](https://github.com/marcos-moreira-dev/Patrones-de-dise-o-Java/actions).
