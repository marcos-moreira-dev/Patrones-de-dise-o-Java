# Construir MiniSO: JDK, classpath y Maven sin magia

Esta guía enseña el *proceso de construcción* y no solo comandos para copiar.
Piensa en CMake: distinguir configuración, compilación, pruebas y producto final
ayuda a diagnosticar fallos sin depender de un IDE.

Si ya tienes **JDK 21 y Maven**, desde la raíz del repositorio ejecuta
**una sola orden** en Git Bash para probar Arena y MiniSO, empaquetar sus JAR
y verificar el arranque:

```bash
bash scripts/verificar.sh
```

Consulta [CONTRIBUTING.md](../../CONTRIBUTING.md) para la matriz de pruebas,
el flujo de PR y el criterio profesional para dar una entrega por terminada.

## 1. Qué hace cada herramienta

- **Java 21 (JDK)**: aporta el compilador `javac`, la máquina virtual `java`,
  la herramienta `jar` y las APIs del programa. Un JRE sin compilador no basta
  para construir desde el código fuente.
- **`javac`**: convierte `.java` en archivos de bytecode `.class`.
- **`java`**: carga las clases y ejecuta su método `main`.
- **`jar`**: reúne clases y un manifiesto en un único archivo ejecutable.
- **Maven 3.9+ (opcional)**: describe esas etapas en `pom.xml` y descarga
  herramientas de compilación. **No añade bibliotecas de ejecución a MiniSO.**

Comprueba las versiones: `java -version`, `javac -version`, y si usas Maven,
`mvn -version`. Este último también te indica **qué Java utiliza Maven**.

| CMake, como analogía | Con herramientas Java | Salida |
| --- | --- | --- |
| Configurar | Elegir JDK 21 y rutas de fuente/salida | Opciones `-cp`, `-d` |
| Compilar | `javac` o `mvn compile` | `.class` |
| Ejecutar pruebas | `java ... PruebasMiniSO` o `mvn test` | Resultado textual |
| Empaquetar | `jar` o `mvn package` | `.jar` |
| Limpiar | Regenerar `out/` o `mvn clean` | Sin archivos derivados |

## 2. Compilación directa desde la raíz del repositorio

**Linux o Git Bash** (desde la raíz del repositorio):

```bash
mkdir -p out
find proyectos/miniso/src/main/java -name '*.java' -print0 | xargs -0 javac -encoding UTF-8 -d out
find proyectos/miniso/src/test/java -name '*.java' -print0 | xargs -0 javac -encoding UTF-8 -cp out -d out
java -cp out miniso.PruebasMiniSO
java -cp out miniso.presentacion.MiniSO
```

**PowerShell (Windows)** (también desde la raíz):

```powershell
New-Item -ItemType Directory -Force out | Out-Null
$fuentes = Get-ChildItem .\proyectos\miniso\src\main\java -Recurse -Filter *.java | ForEach-Object FullName
javac -encoding UTF-8 -d out $fuentes
$pruebas = Get-ChildItem .\proyectos\miniso\src\test\java -Recurse -Filter *.java | ForEach-Object FullName
javac -encoding UTF-8 -cp out -d out $pruebas
java -cp out miniso.PruebasMiniSO
java -cp out miniso.presentacion.MiniSO
```

`-d out` define la **raíz** de los archivos `.class`. Por ejemplo,
`miniso/dominio/Proceso.class` queda dentro de `out/`. El nombre completo
`miniso.presentacion.MiniSO` incluye el paquete; los paquetes son Java,
no un invento de Maven.

`-cp out` significa *classpath*: dónde buscar las clases compiladas. Si
divides `out/classes` y `out/test-classes`, únelos con `:` en Linux/macOS
o `;` en Windows (incluso si escribes el comando en Git Bash).

## 3. Construir el JAR sin Maven

Para **distribuir solo el programa**, compila las fuentes principales por
separado desde la raíz del repositorio (sin incluir pruebas):

```bash
mkdir -p out/classes
find proyectos/miniso/src/main/java -name '*.java' -print0 | xargs -0 javac -encoding UTF-8 -d out/classes
jar --create --file out/miniso.jar --main-class miniso.presentacion.MiniSO -C out/classes .
java -jar out/miniso.jar
```

El JAR no contiene las pruebas ni la máquina virtual: el destino necesita
Java compatible. En PowerShell, sustituye `find ... | xargs` por
`Get-ChildItem ... | ForEach-Object FullName`, como en la sección anterior.

## 4. Construir con Maven (opcional)

Desde la raíz del repositorio:

```bash
mvn -f proyectos/miniso/pom.xml clean verify
java -jar proyectos/miniso/target/miniso-1.0.0-SNAPSHOT.jar
```

En `pom.xml`, `groupId + artifactId + version` identifican el artefacto,
`maven.compiler.release=21` fija las APIs del JDK y los *plugins* realizan
la compilación, empaquetado y ejecución de pruebas.

| Comando | Acción | Artefacto principal |
| --- | --- | --- |
| `mvn compile` | Compila el programa | `target/classes/` |
| `mvn test` | Compila y ejecuta `PruebasMiniSO` | Salida de consola |
| `mvn package` | Pruebas + empaquetado | `target/miniso-1.0.0-SNAPSHOT.jar` |
| `mvn verify` | Ciclo completo hasta verificación | JAR y resultado de pruebas |
| `mvn clean` | Borra la salida de Maven | Quita `target/` |

Nuestro programa de pruebas tiene un `main`: lo ejecuta **Exec Maven Plugin**
en la fase `test`. *Surefire* está presente pero no encuentra JUnit porque
deliberadamente no hemos incorporado esa dependencia. En un proyecto real,
JUnit suele ser preferible; aquí priorizamos el JDK puro y la transparencia.

Maven puede requerir Internet la primera vez para conseguir sus plugins,
pero `javac` funciona sin Maven ni descargas.

## 5. Cómo comprobar fallos habituales

| Síntoma | Diagnóstico |
| --- | --- |
| `javac` no se encuentra | Falta JDK o su directorio `bin` en `PATH` |
| `release version 21 not supported` | Maven utiliza un JDK anterior a Java 21 |
| `ClassNotFoundException` | Revisar `-cp`, `-d` y nombre completo de paquete |
| `UnsupportedClassVersionError` | Ejecutas con un Java más viejo que el compilador |
| `mvn` usa Java 17 | Revisa `mvn -version` y `JAVA_HOME` |
| JAR no arranca | Revisa `Main-Class` y vuelve a construir |

`out/` y `target/` son **archivos generados**, ignorados por Git. No borres
`src/`. GitHub Actions ejecuta ambas rutas de construcción con Java 21 en el
PR, además de generar Javadoc del repositorio.
