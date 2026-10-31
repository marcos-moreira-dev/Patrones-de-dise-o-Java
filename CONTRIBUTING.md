# Cómo trabajar profesionalmente en este laboratorio

Este repositorio es un **laboratorio acotado**, no un producto comercial ni
un sistema operativo real. Practicamos hábitos profesionales: cambios pequeños,
contratos claros, revisión, pruebas y evidencia reproducible.

## Preparación desde cero

Necesitas Git, **JDK 21** y **Maven 3.9+** para el flujo automatizado. En
Git Bash (Windows), Linux o macOS:

```bash
git --version
java -version
javac -version
mvn -version
git clone https://github.com/marcos-moreira-dev/Patrones-de-dise-o-Java.git
cd Patrones-de-dise-o-Java
```

Si ya existe la carpeta, **no vuelvas a clonar**. Desde tu clon actual:

```bash
git status --short
git pull --ff-only origin main
```

Antes de actualizar, revisa los cambios locales con `git status --short`.
Si muestra archivos modificados que quieres conservar, **no fuerces el
pull**: decide primero si guardarlos en una rama o commit. No utilices
`git reset --hard` para «arreglar» un proyecto de estudio.

## Un comando para verificar la entrega

Desde la raíz del repositorio, en **Git Bash**:

```bash
bash scripts/verificar.sh
```

Este comando **no modifica el SO real**. Comprueba que Java y Maven usan
JDK 21; compila y ejecuta el menú de los patrones clásicos (incluida una
entrada inválida), ejecuta las suites de Arena y MiniSO, construye los dos
JAR y prueba automáticamente que arrancan y responden a comandos.

El resultado final esperado es:

```text
VERIFICACION COMPLETA: 22 patrones clasicos, 2 microproyectos, 2 suites y 3 pruebas de consola correctas.
```

Un comando que termina con código **0** significa que no se detectaron
errores. Si falla, se detiene en el primer problema con código distinto de
0; **no declares aprobada la entrega hasta corregirlo**.

## Comandos individuales, sin scripts ocultos

Para depurar cada fase por separado:

```bash
mvn -f proyectos/arena-java/pom.xml clean verify
mvn -f proyectos/miniso/pom.xml clean verify
java -jar proyectos/arena-java/target/arena-java-1.0.0-SNAPSHOT.jar
java -jar proyectos/miniso/target/miniso-1.0.0-SNAPSHOT.jar
```

En **PowerShell**, los mismos comandos `mvn` y `java` funcionan, con rutas
entre comillas si es necesario. El script `.sh` se ejecuta desde Git Bash.
Si todavía no has instalado Maven, cada proyecto explica la compilación
directa con `javac` en su respectivo `BUILD.md`.

## Matriz mínima de validación

| Nivel | Qué comprueba | Cómo | Criterio para aprobar |
| --- | --- | --- | --- |
| Compilación | Paquetes, tipos y APIs Java 21 | `mvn compile` | Sin errores de compilación |
| Pruebas de dominio | Invariantes y rechazos sin efectos laterales | `mvn test` | Suites terminan sin fallo |
| Algoritmos | FIFO/heap frente a oráculos de JDK | `miniso.PruebasMiniSO` | Orden y desempates coinciden |
| Métricas | Espera, respuesta, retorno y alternancias | `miniso.PruebasMiniSO` | Valores coinciden con cálculo manual |
| Integración | CLI → aplicación → dominio | `bash scripts/verificar.sh` | Dos JAR arrancan y responden |
| Patrones clásicos | Menú de los 22 patrones y entradas inválidas | `bash scripts/verificar.sh` | Tres categorías se ejecutan sin excepción |
| Documentación | UML/SVG y contratos | GitHub Actions / inspección visual | Enlaces legibles y Javadoc generado |
| Usabilidad | Persona real ejecuta el proyecto | Git Bash, dos `java -jar` | Puede completar una partida y un escenario MiniSO |

### Prueba manual corta de MiniSO

Abre el JAR, elige `2` (Round Robin) y escribe:

```text
crear editor 6 2 8
crear musica 2 9 8
crear juego 4 5 12
tick 12
ps
metricas
comparar
salir
```

Espera ver los tres procesos `TERMINADO` y estos valores aproximados en
`comparar`, que ejecuta **tres simulaciones independientes** con esa misma
carga admitida en `t=0`:

| Política | Espera media | Respuesta media | Retorno medio | Alternancias |
| --- | ---: | ---: | ---: | ---: |
| FCFS | 4.67 | 4.67 | 8.67 | 2 |
| Round Robin (q=2) | 4.67 | 2.00 | 8.67 | 5 |
| Prioridad no expropiativa | 2.67 | 2.67 | 6.67 | 2 |

Estos números son **ticks simulados** y dependen de la carga elegida. No
demuestran que un algoritmo sea universalmente mejor ni miden la velocidad
del hardware. Las alternancias solo cuentan cambios de PID entre ticks
consecutivos ocupados: **no cobran costes de cambio de contexto reales**.

## Rutina de pull request y definición de terminado

1. Explica el caso de uso y las reglas de dominio antes de modificar clases.
2. Cambia lo imprescindible; no crees nuevas capas o patrones por catálogo.
3. Añade una prueba que fallaría si se rompiera el comportamiento modificado.
4. Actualiza Javadoc, README, UML y comandos cuando cambie el contrato.
5. Ejecuta `bash scripts/verificar.sh` y deja que GitHub Actions vuelva a
   verificar con Java 21 y generar Javadoc.
6. Revisa el diff, evita archivos generados (`target/`, `out/`, `.class`) y
   fusiona solo cuando las comprobaciones estén en verde.

Un PR está **terminado** cuando cumple esos seis puntos. No exigimos
Hibernate, Spring, redes, persistencia ni añadir todos los patrones posibles.
Los cambios futuros deben resolver un **problema didáctico específico** y
tener un criterio de aceptación verificable. Así el laboratorio puede crecer
sin convertirse en un proyecto interminable.
