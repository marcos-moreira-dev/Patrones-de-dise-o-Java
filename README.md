# 🧩 Práctica de los Patrones de Diseño en Java

Este repositorio es un **laboratorio de práctica** para estudiar e implementar los **patrones de diseño en Java**, con un enfoque **didáctico y práctico**, no orientado a producción.

Los ejemplos están organizados por tipo de patrón:

* 🏗️ **Creacionales** → Controlan la creación de objetos.
* 🧱 **Estructurales** → Componen clases y objetos.
* ⚙️ **De comportamiento** → Gestionan la comunicación entre objetos.

El proyecto **no utiliza Maven ni Gradle intencionalmente**, para facilitar la compilación directa desde consola y concentrarse en los conceptos fundamentales.

---

## 📁 Estructura del proyecto

```
Patrones-de-dise-o-Java/
├── creacionales/
├── estructurales/
├── comportamiento/
├── App.java
├── Main.java
├── README.md
└── Tree.md
```

* Las carpetas se usan **solo como organización visual**.
* Las clases **no declaran `package`**.
* `Main.java` actúa como **orquestador** de los ejemplos.

---

## ⚙️ Compilación y ejecución desde consola

### 1️⃣ Compilar archivos dentro de una carpeta

```bash
javac *.java
```

Ejemplo para compilar los creacionales:

```bash
cd creacionales
javac *.java
```

---

### 2️⃣ Ejecutar una clase específica

```bash
java NombreDeLaClase
```

Ejemplo:

```bash
java SingletonInstance
```

⚠️ Asegúrate de estar **en la carpeta donde se encuentra el `.class`**.

---

### 3️⃣ Compilar todo el proyecto desde la raíz

Desde la carpeta raíz del proyecto:

#### Git Bash / PowerShell / Linux

```bash
javac App.java Main.java creacionales/**/*.java estructurales/**/*.java comportamiento/**/*.java
```

#### CMD (Windows clásico)

```cmd
javac App.java Main.java creacionales\*.java estructurales\*.java comportamiento\*.java
```

---

### ▶️ Ejecutar todo desde el orquestador

Una vez compilado:

```bash
java Main
```

`Main.java` se encarga de ejecutar y coordinar los ejemplos de los distintos patrones.

---

## 🧼 Compilación más ordenada (opcional)

Para evitar que los `.class` queden mezclados con el código fuente:

```bash
mkdir out
javac -d out App.java Main.java creacionales/**/*.java estructurales/**/*.java comportamiento/**/*.java
java -cp out Main
```

---

## 💡 Recomendaciones de diseño

* Cada patrón debe estar en un archivo con el **mismo nombre que la clase principal**.
* No es necesario usar `package` si se compila directamente desde consola.
* Usar un `Main` central facilita:

  * pruebas rápidas
  * demostraciones
  * enseñanza
* Si en el futuro se migra a Maven/Gradle, los paquetes serán obligatorios.

---

## ✅ Ejemplo rápido

Archivo `creacionales/SingletonInstance.java`:

```java
public class SingletonInstance {
    private static SingletonInstance instancia;

    private SingletonInstance() {}

    public static SingletonInstance getInstancia() {
        if (instancia == null)
            instancia = new SingletonInstance();
        return instancia;
    }

    public void ejecutarFuncionUnica() {
        System.out.println("Instancia única del Singleton creada.");
    }
}
```

Desde consola:

```bash
cd creacionales
javac SingletonInstance.java
java SingletonInstance
```

Salida esperada:

```
Instancia única del Singleton creada.
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

## 📌 Nota final

Este proyecto está pensado como:

* 📚 material de estudio
* 🧪 laboratorio de pruebas
* 🎓 apoyo académico

No busca ser un proyecto de producción, sino una **base sólida para comprender y practicar patrones de diseño en Java**.
