# 🧩 Práctica de los Patrones de Diseño en Java
Este proyecto contiene ejemplos de patrones de diseño organizados por tipo:
•	🏗️ Creacionales → Controlan la creación de objetos.
•	🧱 Estructurales → Componen clases y objetos.
•	⚙️ De comportamiento → Gestionan la comunicación entre objetos.

## ⚙️ Compilación y ejecución desde consola

1️⃣ Compilar todos los archivos .java dentro de una carpeta
javac *.java
Ejemplo para compilar los creacionales:
cd creacionales
javac *.java

2️⃣ Ejecutar un archivo específico
java NombreDeLaClase
Por ejemplo:
java Singleton
⚠️ Asegúrate de estar dentro de la carpeta donde está el .class.

3️⃣ Compilar todos los patrones a la vez (desde la raíz del proyecto)
PowerShell o Linux:
javac .\creacionales\*.java .\estructurales\*.java .\comportamiento\*.java
CMD (Windows clásico):
javac creacionales\*.java estructurales\*.java comportamiento\*.java

📝 Nota: carpeta raíz
Debes estar ubicado en la carpeta principal antes de ejecutar los comandos:
cd "D:\Carrera Profesional\Práctica de habilidades profesionales\Programación\Patrones de diseño Java"

💡 Recomendación
•	Guarda cada patrón en un archivo con el mismo nombre que la clase.
•	No es necesario crear package si compilas desde la consola directamente.
•	Si en el futuro agregas paquetes, recuerda usar javac -d . para mantener las rutas de compilación.

## ✅ Ejemplo rápido
Supón que en creacionales/Singleton.java tienes:
public class Singleton {
    private static Singleton instance;

    private Singleton() {}

    public static Singleton getInstance() {
        if (instance == null)
            instance = new Singleton();
        return instance;
    }

    public static void main(String[] args) {
        Singleton s = Singleton.getInstance();
        System.out.println("Instancia creada: " + s);
    }
}

Entonces, desde consola:

cd creacionales
javac Singleton.java
java Singleton

Salida:
Instancia creada: Singleton@6d06d69c
