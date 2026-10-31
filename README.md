# 🧩 Práctica de los Patrones de Diseño en Java

Este repositorio es un **laboratorio de práctica** para estudiar e implementar los **patrones de diseño en Java**, con un enfoque **didáctico y práctico**, no orientado a producción.

Los ejemplos están organizados por tipo de patrón:

* 🏗️ **Creacionales** → Controlan la creación de objetos.
* 🧱 **Estructurales** → Componen clases y objetos.
* ⚙️ **De comportamiento** → Gestionan la comunicación entre objetos.

Los ejemplos clásicos **no utilizan Maven ni Gradle**, para facilitar la
compilación directa desde consola. Los dos microproyectos mantienen esa
posibilidad y añaden cada uno un `pom.xml` **opcional**, documentado, para
estudiar también las fases de construcción con Maven sin frameworks.

### Cómo estudiar este laboratorio

1. Empieza por el problema que resuelve el patrón (puede ser un ejemplo cotidiano, un videojuego o un negocio).
2. Lee el diagrama UML y relaciona cada clase, interfaz y cardinalidad con el código.
3. Ejecuta el ejemplo, cambia una decisión y observa qué comportamiento se modifica.
4. Revisa el Javadoc de los contratos: **qué garantiza cada método, qué recibe y qué devuelve**. La implementación puede llamar internamente a otros métodos sin que cada uno sea un caso de uso.

La [guía UML](UML_GUIDE.md) explica los símbolos, la lectura de multiplicidades, clases abstractas, interfaces (incluida la notación circular), enumeraciones, `record` y el paso del modelo a estructuras Java como `List` y `Map`.

### Microproyectos con arquitectura real

El primer microproyecto es **[Arena Java](proyectos/arena-java/README.md)**:
un juego pequeño de combate por turnos, con dos escenarios, renderizado ASCII,
paquetes `dominio`, `aplicacion`, `infraestructura` y `presentacion`, UML
y pruebas sin dependencias externas. Se compila y ejecuta de forma
independiente de los ejemplos clásicos.

El segundo es **[MiniSO](proyectos/miniso/README.md)**: una maqueta de
planificación de procesos, memoria limitada, E/S y archivos en un árbol
virtual. Implementa desde cero una **cola circular** y un **montículo de
prioridad**, compara FCFS, Round Robin y prioridades, y justifica Strategy y
Composite con diagramas SVG, Javadoc y pruebas. No toca el sistema operativo
real. Su [guía de construcción](proyectos/miniso/BUILD.md) explica Java 21,
`javac`, classpath, Maven y JAR desde lo elemental.

Los comentarios de clase conservan el contexto del patrón; los comentarios de método priorizan contratos, invariantes y decisiones relevantes. Los ejemplos domésticos, lúdicos o empresariales se utilizan cuando aclaran la idea, no como una plantilla repetitiva.

Cada pull request ejecuta una comprobación automática con **Java 21**:
compilación de los ejemplos, pruebas de Arena Java y MiniSO, construcción
Maven opcional de ambos y generación de Javadoc. Los ejemplos antiguos
siguen sin requerir Maven.

### Flujo profesional y pruebas reproducibles

La [guía para contribuir](CONTRIBUTING.md) explica preparación, pruebas,
revisión de PR y **criterios de terminado**. En Git Bash, desde la raíz del
repositorio, basta este comando para comprobar los patrones clásicos,
construir y probar ambos microproyectos y comprobar que sus JAR arrancan:

```bash
bash scripts/verificar.sh
```

MiniSO también permite ejecutar `metricas` y `comparar` para estudiar
con números la espera, respuesta, retorno y alternancia de procesos con
FCFS, Round Robin y prioridad. Son **ticks simulados**, no benchmarks del PC.

---

## 📁 Estructura del proyecto

```
Patrones-de-dise-o-Java/
├── creacionales/
├── estructurales/
├── comportamiento/
├── proyectos/
│   ├── arena-java/
│   └── miniso/
├── App.java
├── Main.java
├── README.md
└── Tree.md
```

* Los ejemplos clásicos **sí declaran paquetes Java** (`creacionales.SingletonInstance`,
  `estructurales.Adapter`, etc.); únicamente `App.java` y `Main.java` permanecen
  en el paquete predeterminado para iniciar el menú.
* Los microproyectos también tienen paquetes, agrupados por responsabilidades
  de arquitectura (`dominio`, `aplicacion`, etc.). Ninguno requiere Maven para
  *definir* esos paquetes.
* `Main.java` actúa como **orquestador** de los ejemplos clásicos, no de los microproyectos.

---

## ⚙️ Compilación y ejecución desde consola

### Opción recomendada: verificar los tres componentes del laboratorio

Desde la **raíz del repositorio**, en Git Bash (Windows) o Linux:

```bash
bash scripts/verificar.sh
```

Compila y ejecuta el menú de patrones clásicos, prueba Arena y MiniSO,
construye sus dos JAR y verifica que arrancan. Consulta
[CONTRIBUTING.md](CONTRIBUTING.md) para los criterios de aceptación.

### Compilar solo los patrones clásicos con `javac`

En Git Bash o Linux, desde la **raíz**:

```bash
mkdir -p out/clasicos
find creacionales estructurales comportamiento -name '*.java' -print0 \
  | xargs -0 javac -encoding UTF-8 -d out/clasicos App.java Main.java
java -cp out/clasicos Main
```

En PowerShell, también desde la **raíz**:

```powershell
New-Item -ItemType Directory -Force out\clasicos | Out-Null
$fuentes = Get-ChildItem .\creacionales, .\estructurales, .\comportamiento -Recurse -Filter *.java | ForEach-Object FullName
javac -encoding UTF-8 -d out\clasicos .\App.java .\Main.java $fuentes
java -cp out\clasicos Main
```

`-d out/clasicos` guarda los `.class` fuera de las carpetas fuente.
El classpath apunta a **esa raíz**, y Java utiliza el nombre completo de
cada clase y su paquete. `Main` permanece sin `package`; desde su menú
selecciona `1` (creacionales), `2` (estructurales), `3` (comportamiento)
o `0` para salir. Una entrada inválida vuelve a solicitar la opción.

Por ejemplo, `SingletonInstance` está en
`creacionales/SingletonInstance/SingletonInstance.java` y **no tiene
`main` propio**: para verlo funcionar, ejecuta `Main` y elige `1`.
El compilador respeta los paquetes sin necesitar Maven o Gradle.

Para jugar Arena o usar MiniSO, sigue los READMEs respectivos o ejecuta
sus JAR después de `bash scripts/verificar.sh`.

---

## ✅ Ejecución de ejemplo (salida real)

Ejemplo ejecutado en **Git Bash (Windows)** desde la raíz del proyecto:

```bash
bash scripts/verificar.sh
java -cp out/clasicos Main
```

Salida (recortada tal cual, demostrando que compila y corre correctamente):

```text
=== DEMO DE PATRONES DE DISEÑO ===
=== ProbarCreacionales ===
=== ProbarEstructurales ===
=== ProbarComportamiento ===

==============================
  MENÚ PRINCIPAL - PATRONES DE DISEÑO
==============================
1. Patrones Creacionales
2. Patrones Estructurales
3. Patrones de Comportamiento
0. Salir
Seleccione una opción: 1

=== PATRONES CREACIONALES ===

[Singleton]
Singleton Instance: Hello World!
¿Ambas instancias son iguales? true

[Abstract Factory]
Guardando datos en base de datos SQL...
Mapeando datos en formato SQL...
Guardando datos en base de datos NoSQL...
Mapeando datos en formato NoSQL...

[Builder]
Response [statusCode=200, message=Operación exitosa, body={data:'OK'}]

[Factory Method]
Ejecutando servicio de usuario...
Ejecutando servicio de pago...

[Prototype]
Original: Configuration [env=localhost, dbHost=8080]
Copia: Configuration [env=localhost, dbHost=8080]
¿Son iguales (misma referencia)? false

... (menú) ...

Seleccione una opción: 2

=== PATRONES ESTRUCTURALES ===

[Adapter]
Adaptando la solicitud...
Solicitud específica del proveedor externo.

[Bridge]
[Sistema de pedidos] Enviando orden al sistema de cocina...
[Cocina Tradicional] Preparando pedido manualmente.
[Sistema de pedidos] Enviando orden al sistema de cocina...
[Cocina Automatizada] Robot preparando pizza con precisión perfecta.

[Composite]
Combo: Menú Principal
  - Pizza
  - Pasta

[Decorator]
Operación base del componente (ej. pizza simple).
✅ Se añadió extra de queso (decoración adicional).

[Facade]
[Subsistema A] Validando pedido: Pizza Napolitana
[Subsistema B] Preparando Pizza Napolitana
[Facade] Pedido completado con éxito.

[Flyweight]
Creando nuevo objeto Flyweight para tipo: Pizza Margherita
Reutilizando objeto Flyweight existente para tipo: Pizza Margherita
Creando nuevo objeto Flyweight para tipo: Pizza Pepperoni
Preparando pizza tipo Pizza Margherita para Mesa 1
Preparando pizza tipo Pizza Margherita para Mesa 2
Preparando pizza tipo Pizza Pepperoni para Mesa 3
¿Son iguales las instancias de Margherita? true

[Proxy]
[Proxy] Verificando permisos...
[RealService] Procesando pedido del cliente.
[Proxy] Verificando permisos...
[Proxy] Acceso denegado para el usuario: usuarioX

[Iterator] (demo cruzada)
Iterando: Pedido 1
Iterando: Pedido 2
Iterando: Pedido 3

[Observer] (demo cruzada)
[Cocina] recibió: Estado cambiado: Nuevo pedido recibido
[Caja] recibió: Estado cambiado: Nuevo pedido recibido
[Cocina] recibió: Estado cambiado: Pedido listo para entrega
[Caja] recibió: Estado cambiado: Pedido listo para entrega

... (menú) ...

Seleccione una opción: 3

=== PATRONES DE COMPORTAMIENTO ===

[Strategy]
Ejecutando estrategia A: aplicando descuento del 10%.
Ejecutando estrategia B: aplicando descuento del 5%.

[State]
Estado actual: A
Cambiando a estado B
Estado actual: B
Cambiando a estado A
Estado actual: A
Cambiando a estado B

[Chain of Responsibility]
Recepción: verificando pedido...
Cocina: preparando pizza.
Recepción: verificando pedido...
Cocina: preparando pizza.

[Command]
Preparando pedido: Pizza Napolitana

[Mediator]
[Cocina] envió mensaje: Pedido listo para entrega
[Caja] recibió: Pedido listo para entrega
[Caja] envió mensaje: Confirmación recibida
[Cocina] recibió: Confirmación recibida

[Memento]
Estado actual: Versión 1 - Pedido creado
Estado actual: Versión 2 - Pedido modificado
Estado restaurado: Versión 2 - Pedido modificado
Estado restaurado: Versión 1 - Pedido creado

[Template Method]
Iniciando proceso base...
Ejecutando paso 1 de A
Ejecutando paso 2 de A
Finalizando proceso base.
Iniciando proceso base...
Ejecutando paso 1 de B
Ejecutando paso 2 de B
Finalizando proceso base.

[Visitor]
Visitando A -> Elemento A procesado.
Visitando B -> Elemento B procesado.

... (menú) ...

Seleccione una opción: 0
Saliendo del programa...
```

---

## 🧠 Buenas prácticas de Git (para este repositorio)

### Commits

**Reglas**

* Commits **pequeños y claros**
* **Un patrón o mejora por commit**
* Mensaje corto y descriptivo

**Formato recomendado**

```
tipo(scope opcional): mensaje corto
```

**Tipos más usados**

* `feat:` nueva funcionalidad (ej: agregar un patrón)
* `fix:` corrección de errores
* `docs:` cambios solo de documentación
* `refactor:` reestructuración sin cambiar comportamiento
* `style:` formato/nombres (sin cambiar lógica)
* `test:` pruebas (si se agregan en el futuro)
* `chore:` tareas de mantenimiento

**Ejemplos recomendados**

* `feat(creacionales): add Singleton example`
* `feat(estructurales): add Facade and Flyweight patterns`
* `feat(main): orchestrate pattern runners`
* `docs(readme): add compilation and execution steps`
* `chore: add .gitignore for Java/IDEs`
* `refactor(comportamiento): rename classes for clarity`
* `fix(estructurales): correct Proxy interface usage`

**Evita commits genéricos como**

* ❌ `update`
* ❌ `changes`
* ❌ `final`

---

### Ramas secundarias

Para experimentar sin romper `main`:

```bash
git checkout -b feature/nombre-del-patron
```

Ejemplo:

```bash
git checkout -b feature/observer-pattern
```

Cuando esté listo:

```bash
git checkout main
git merge feature/observer-pattern
```

---


## Comando correcto para simular tree y ver árbol de archivos

Usa este comando cuando quieras mostrar solo código fuente:

find . \
  -path './.git' -prune -o \
  -path './out' -prune -o \
  -print


📌 Esto excluye:

.git

out

## 📌 Nota final

Este proyecto está pensado como:

* 📚 material de estudio
* 🧪 laboratorio de pruebas
* 🎓 apoyo académico

No busca ser un proyecto de producción, sino una **base sólida para comprender y practicar patrones de diseño en Java**.
