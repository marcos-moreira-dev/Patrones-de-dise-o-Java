# Arena Java: un juego pequeño para estudiar diseño

Un personaje contra un adversario en un combate **por turnos**, con dos
escenarios de reglas distintas. Todo funciona en una consola de texto con
**Java 21 y su biblioteca estándar**; no usa JavaFX, Spring Boot, Maven, Gradle
ni bibliotecas externas para ejecutarse.

No es un motor gráfico: el ASCII muestra únicamente vida, energía, turno y
acciones. La finalidad es justificar las clases, los patrones y las reglas
que hacen funcionar un juego sencillo.

Para aprender la herramienta de compilación desde cero, empieza por
**[BUILD.md](BUILD.md)**: JDK, `javac`, classpath, pruebas, empaquetado
JAR, limpieza y errores frecuentes. Los comandos siguientes son la
versión rápida.

## Jugar

Desde la raíz del repositorio, con Java 21 instalado:

**Git Bash o Linux**

```bash
mkdir -p out
find proyectos/arena-java/src -name '*.java' -print0 | xargs -0 javac -encoding UTF-8 -d out
java -cp out arena.presentacion.ArenaJava
```

**PowerShell**

```powershell
New-Item -ItemType Directory -Force out | Out-Null
$archivos = Get-ChildItem .\proyectos\arena-java\src -Recurse -Filter *.java | ForEach-Object FullName
javac -encoding UTF-8 -d out $archivos
java -cp out arena.presentacion.ArenaJava
```

Elige **1 = Arena** o **2 = Bosque**. Después puedes utilizar:

| Orden | Acción | Regla observable |
| --- | --- | --- |
| 1 | Atacar | Golpe de 4 a 6 puntos |
| 2 | Defender | Reduce en 2 el próximo golpe y recupera 1 energía |
| 3 | Especial | Consume 3 de energía; añade 3 de daño con Decorator |
| 4 | Saltar | Solo en bosque: esquiva las raíces cuando aparecen |
| 5 | Huir | Solo en bosque: termina el encuentro sin victoria |
| 0 | Salir | Detiene la ejecución, sin cerrar procesos externos |

El adversario responde después de una acción válida, salvo que sea derrotado
o que el personaje huya. En el bosque, las raíces dañan **cada tercer turno**
si no saltas. La pantalla avisa cuando el peligro está activo. Una orden
prohibida o un especial sin energía **no consume turno**.

## Diseño por responsabilidades

| Paquete | Contenido | Ejemplo |
| --- | --- | --- |
| `dominio` | Entidades, reglas y mecánicas sin consola | `Jugador`, `Partida`, `MotorCombate` |
| `aplicacion` | Caso de uso y coordinación de órdenes | `ServicioPartida`, `Comando` |
| `infraestructura` | Adaptación de una API técnica | `AzarJava` |
| `presentacion` | Entrada y salida textual | `ConsolaArena`, `RenderizadorAscii` |

No existe un árbitro como personaje: `MotorCombate` es un **servicio de
dominio** porque aplica una interacción entre dos entidades. `Jugador`
conserva sus invariantes: la vida nunca baja de cero y la energía nunca supera
su máximo. `ServicioPartida` coordina el caso de uso y no reimplementa esas
reglas internas.

### UML: Strategy para cambiar de escenario

![ReglasEscenario, ReglasArena y ReglasBosque en UML](docs/uml/escenarios.svg)

Fuente editable: [escenarios.uml](docs/uml/escenarios.uml).
La línea **discontinua con triángulo hueco** significa realización de una
interfaz, no herencia entre clases. La decisión de qué acciones se permiten
pertenece al escenario, no a un `if` que pregunte el tipo del escenario.

### Por qué aparecen estos patrones

| Patrón | Problema que resuelve en el juego | Código |
| --- | --- | --- |
| Strategy | Dos terrenos con restricciones distintas | `ReglasEscenario` y sus implementaciones |
| Command | Interpretar órdenes sin mezclar lectura de teclado con combate | `Comando`, `Comandos` |
| Decorator | Potenciar un ataque manteniendo su contrato | `AtaquePotenciado` |
| Observer | Informar a la interfaz sin imprimir desde el dominio | `ObservadorPartida` |
| Adapter | Cambiar el azar real por uno predecible en pruebas | `FuenteAzar` y `AzarJava` |

La lista de `ReglasAccion` comprueba, en orden, que la partida esté activa,
que el escenario permita la acción y que exista energía suficiente. **No todo
`if` es un problema**: aquí se concentran las decisiones que de otro modo
se repetirían en cada comando.

El rechazo de una acción es un resultado normal representado por
`ResultadoAccion.Rechazada`, no una excepción. Las excepciones se reservan
para argumentos imposibles o violaciones de contratos del programador.

Los `record` transportan resultados y comandos sin sustituir a la entidad
`Jugador`, que sí debe proteger estado y comportamiento. El `enum Estado`
de `Partida` **no se presenta falsamente como el patrón State**: para los
cuatro finales posibles no hace falta ese patrón.

## Probar sin dependencias

Después de compilar, desde la raíz:

```bash
java -cp out arena.PruebasArena
```

Las pruebas usan azar fijo y cubren restricciones de cada entorno, gasto de
energía, daño, defensa, trampas, victoria, derrota, huida, eventos Observer,
invariantes y la presentación ASCII. GitHub Actions también las ejecuta con
Java 21 en cada pull request.

## Qué no incluye

No hay movimiento por coordenadas, inventarios, guardado de partidas, red,
persistencia ni concurrencia real. El salto no es una animación: representa
una decisión táctica contra las raíces. Estas omisiones son deliberadas para
mantener el código pequeño y comprensible.
