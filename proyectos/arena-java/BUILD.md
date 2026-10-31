# Construir Arena Java: del código fuente al programa

Esta guía es deliberadamente parecida a un *onboarding* de una herramienta
como CMake: explica **qué ocurre en cada etapa**, qué artefactos se producen
y cómo repetir el proceso sin depender de un IDE.

## 1. Herramientas y decisiones

- Se necesita un **JDK 21**, no únicamente un JRE: verifica
  `java -version` y `javac -version`.
- No hace falta Maven, Gradle, JavaFX ni Spring Boot.
- Las únicas APIs de ejecución proceden de Java 21 (`java.base`).
- Este repositorio ya enseña compilación directa; por eso mantenemos esa
  forma como procedimiento principal. Maven podría añadirse en el futuro
  como **herramienta de construcción**, sin cambiar el dominio ni las reglas.

En CMake normalmente distingues configuración, compilación y pruebas.
Aquí no existe una fase separada de configuración: nosotros entregamos
directamente a `javac` los archivos fuente y la ruta de salida.

| Etapa | Qué significa | En Arena Java |
| --- | --- | --- |
| Preparar | Verificar herramienta y directorio | JDK 21 + crear `out/` |
| Compilar | Convertir `.java` en `.class` | `javac -d out/classes ...` |
| Probar | Ejecutar aserciones del comportamiento | `java -cp ... arena.PruebasArena` |
| Ejecutar | Arrancar el punto de entrada | `java -cp ... arena.presentacion.ArenaJava` |
| Empaquetar | Crear un artefacto transportable | `jar --create ...` (opcional) |

## 2. Compilar en Git Bash o Linux

Desde la **raíz del repositorio**:

```bash
cd proyectos/arena-java
mkdir -p out/classes out/test-classes
find src/main/java -name '*.java' -print0 | xargs -0 javac -encoding UTF-8 -d out/classes
find src/test/java -name '*.java' -print0 | xargs -0 javac -encoding UTF-8 -cp out/classes -d out/test-classes
```

¿Qué significan las opciones?

- `-encoding UTF-8`: interpreta correctamente los textos del código fuente.
- `-d out/classes`: guarda los archivos compilados fuera de `src/`.
- `-cp out/classes`: las pruebas encuentran las clases del programa.
- `src/main/java`: código que se necesita para jugar.
- `src/test/java`: pruebas independientes, que no entrarán en el JAR final.

Para **probar en Linux**:

```bash
java -cp out/classes:out/test-classes arena.PruebasArena
```

En **Git Bash sobre Windows**, el proceso Java utiliza el separador de
classpath de Windows:

```bash
java -cp 'out/classes;out/test-classes' arena.PruebasArena
```

Para **jugar**:

```bash
java -cp out/classes arena.presentacion.ArenaJava
```

El separador del classpath depende del sistema donde funciona Java:
`:` en Linux/macOS y `;` en Windows, incluso usando Git Bash.

## 3. Compilar en PowerShell (Windows)

Desde la raíz del repositorio:

```powershell
Set-Location .\proyectos\arena-java
New-Item -ItemType Directory -Force out\classes, out\test-classes | Out-Null
$fuentes = Get-ChildItem .\src\main\java -Recurse -Filter *.java | ForEach-Object FullName
javac -encoding UTF-8 -d out\classes $fuentes
$pruebas = Get-ChildItem .\src\test\java -Recurse -Filter *.java | ForEach-Object FullName
javac -encoding UTF-8 -cp out\classes -d out\test-classes $pruebas
java -cp 'out\classes;out\test-classes' arena.PruebasArena
java -cp out\classes arena.presentacion.ArenaJava
```

Los comandos usan rutas y nombres reales del repositorio. No hay instalación
global de paquetes ni un proyecto oculto dentro del IDE.

## 4. ¿Qué es el classpath y para qué sirven los paquetes?

El fuente `src/main/java/arena/dominio/Jugador.java` declara:

```java
package arena.dominio;
```

Con `-d out/classes`, el compilador coloca el resultado en
`out/classes/arena/dominio/Jugador.class`. El classpath se fija en la
**raíz de las clases compiladas**, no en la carpeta `arena/`.
Por eso ejecutamos el nombre completo
`arena.presentacion.ArenaJava`, con sus puntos.

Esto es independiente de Maven: **los paquetes son una característica del
lenguaje Java, no del sistema de construcción**.

## 5. Crear un JAR ejecutable (opcional)

Después de compilar, desde `proyectos/arena-java`:

```bash
jar --create --file out/arena-java.jar --main-class arena.presentacion.ArenaJava -C out/classes .
java -jar out/arena-java.jar
```

El JAR contiene las clases del juego, no un JRE ni las clases de prueba.
La computadora que lo ejecute también necesita una versión compatible de Java.

## 6. Limpiar, reconstruir y diagnosticar

`out/` contiene **únicamente resultados regenerables**. Si necesitas una
compilación limpia, verifica que estás dentro de `proyectos/arena-java`,
elimina esa carpeta de salida y repite el paso 2 o 3. No elimines `src/`.
Git ya ignora `out/` y los archivos `.class`.

Problemas frecuentes:

| Síntoma | Qué comprobar |
| --- | --- |
| `javac` no existe | Instalaste un JRE, no un JDK, o falta su ruta en PATH |
| Error al compilar un `switch` de patrones | Estás usando un compilador anterior a Java 21 |
| `ClassNotFoundException` | Revisa `-cp`, `-d` y el nombre completo del paquete |
| `UnsupportedClassVersionError` | Compilaste para una versión de Java más nueva que el ejecutor |
| Caracteres extraños en mensajes | Usa archivos UTF-8 y un terminal compatible |

## 7. Integración continua

El archivo `.github/workflows/java21.yml` usa Java 21 para compilar todos
los ejemplos, ejecutar `arena.PruebasArena` y generar Javadoc. Así se
comprueba el proyecto incluso si el equipo local todavía no tiene ese JDK.

Cuando MiniSO se incorpore al mismo repositorio, podrá conservar la misma
rutina sin obligarnos a adoptar un framework.
