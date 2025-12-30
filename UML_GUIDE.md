# UML Guide (recordatorio para estudiantes)

Este documento explica, paso a paso y con lenguaje sencillo, como construir y leer diagramas UML de clases. La idea es que puedas entenderlos desde cero y mantenerlos claros.

## Para que sirve un diagrama de clases
Un diagrama de clases muestra la estructura del sistema:
- Que clases existen.
- Que datos tienen (atributos).
- Que hacen (metodos).
- Como se relacionan entre si.

No describe el flujo de ejecucion, sino la estructura.

## Elementos basicos
### Clase
Representa un concepto del sistema (ej. Pedido, Usuario, Factura).
Dentro de la clase se muestran:
- Atributos: datos que tiene la clase.
- Metodos: acciones que la clase puede hacer.

### Interfaz
Define un conjunto de metodos que otras clases deben implementar.
No tiene atributos ni implementaciones, solo el "contrato".

### Clase abstracta
Es una clase que no se instancia directamente. Sirve como base para otras clases.
Puede tener metodos abstractos y metodos concretos.

## Visibilidad (modificadores)
- Publico (+): se puede usar desde cualquier parte.
- Privado (-): solo dentro de la clase.
- Protegido (#): accesible en la clase y sus hijas.

Para recordar: el signo te dice quien puede acceder a un atributo o metodo.

## Relaciones mas comunes (y como pensarlas)
### Herencia (es-un)
Una clase hija hereda de una clase padre.
Ejemplo mental: "Perro es un Animal".

### Implementacion (cumple-un-contrato)
Una clase cumple una interfaz.
Ejemplo mental: "RepositorioSQL implementa Repositorio".

### Asociacion (se comunica con)
Una clase usa o conoce a otra.
Ejemplo mental: "Pedido usa Cliente".

### Agregacion (tiene-una, pero puede vivir aparte)
Una clase contiene a otra, pero el contenido puede existir sin ella.
Ejemplo mental: "Equipo tiene Jugadores, y los Jugadores pueden existir sin el Equipo".

### Composicion (parte-de)
Una clase contiene a otra y su ciclo de vida depende de ella.
Ejemplo mental: "Casa tiene Habitaciones, si la Casa desaparece, las Habitaciones tambien".

### Dependencia (uso temporal)
Una clase usa otra solo en un metodo o parametro.
Ejemplo mental: "Factura usa Impresora solo para imprimir".

## Simbologia de relaciones (flechas y lineas)
Aqui tienes la forma de reconocer cada relacion solo por la flecha, con una version didactica en texto:

- Herencia: linea continua con triangulo hueco apuntando a la clase padre. (H) ClaseHija ---|> ClasePadre
- Implementacion: linea discontinua con triangulo hueco apuntando a la interfaz. (I) Clase ---|> Interfaz
- Asociacion: linea continua con flecha simple apuntando a la clase conocida. (A) ClaseA ---> ClaseB
- Agregacion: linea continua con rombo blanco en el lado del "todo". (G) Todo o--- Parte
- Composicion: linea continua con rombo negro en el lado del "todo". (C) Todo *--- Parte
- Dependencia: linea discontinua con flecha simple apuntando a la clase usada. (D) ClaseA ..> ClaseB

Pistas rapidas para recordar:
- Triangulo hueco = herencia o implementacion (la diferencia es la linea).
- Rombo = relacion de "contiene".
- Flecha simple = uso o conocimiento.

## Cardinalidad (cuantos hay)
Ayuda a indicar cantidades en una relacion:
- 1..1: exactamente uno.
- 0..1: opcional (puede no existir).
- 1..*: uno o muchos.
- 0..*: cero o muchos.

Piensa en preguntas simples:
- "Un pedido tiene cuantos productos?"
- "Un usuario tiene cuantos perfiles?"

## Ejemplos concretos por relacion (con cardinalidad)
Cada ejemplo es inventado y muy simple, solo para entender la relacion.

### Herencia
Cardinalidad: no aplica (es una relacion de tipo).
```java
package ejemplo.herencia;

class Persona {
    protected String nombre;

    public Persona(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }
}

class Estudiante extends Persona {
    private String carrera;

    public Estudiante(String nombre, String carrera) {
        super(nombre);
        this.carrera = carrera;
    }

    public String getCarrera() {
        return carrera;
    }
}
```

### Implementacion
Cardinalidad: no aplica (es una relacion de contrato).
```java
package ejemplo.implementacion;

interface Repositorio {
    void guardar(String dato);
    String buscarPorId(int id);
}

class RepositorioSql implements Repositorio {
    // Implementa el contrato definido por la interfaz
    public void guardar(String dato) {
        // guardar en SQL (ejemplo)
    }

    public String buscarPorId(int id) {
        return "dato-" + id;
    }
}
```

### Asociacion
Cardinalidad sugerida: Profesor 0..* Cursos, Curso 1 Profesor.
```java
package ejemplo.asociacion;

class Profesor {
    private String nombre;

    public Profesor(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }
}

class Curso {
    private String titulo;
    private Profesor profesor; // asociacion: Curso conoce a Profesor

    public Curso(String titulo, Profesor profesor) {
        this.titulo = titulo;
        this.profesor = profesor;
    }

    public Profesor getProfesor() {
        return profesor;
    }
}
```

### Agregacion
Cardinalidad sugerida: Equipo 0..* Jugadores, Jugador 0..1 Equipo.
```java
package ejemplo.agregacion;

import java.util.ArrayList;
import java.util.List;

class Jugador {
    private String nombre;

    public Jugador(String nombre) {
        this.nombre = nombre;
    }
}

class Equipo {
    private List<Jugador> jugadores; // agregacion: jugadores pueden existir sin equipo

    public Equipo() {
        this.jugadores = new ArrayList<>();
    }

    public void agregar(Jugador jugador) {
        jugadores.add(jugador);
    }
}
```

### Composicion
Cardinalidad sugerida: Orden 1..* Lineas, Linea 1 Orden.
```java
package ejemplo.composicion;

import java.util.ArrayList;
import java.util.List;

class LineaPedido {
    private String producto;
    private int cantidad;

    public LineaPedido(String producto, int cantidad) {
        this.producto = producto;
        this.cantidad = cantidad;
    }
}

class Orden {
    private List<LineaPedido> lineas; // composicion: lineas viven con la orden

    public Orden() {
        this.lineas = new ArrayList<>();
    }

    public void agregarLinea(String producto, int cantidad) {
        lineas.add(new LineaPedido(producto, cantidad));
    }
}
```

### Dependencia
Cardinalidad: uso temporal, no estructura fija.
```java
package ejemplo.dependencia;

class Impresora {
    public void imprimir(String texto) { }
}

class Reporte {
    public void generar(Impresora impresora) { // dependencia: uso temporal
        impresora.imprimir("OK");
    }

    public void generarEnPantalla() {
        // No depende de Impresora en este metodo
    }
}
```

## Como construir un diagrama desde cero
1) Lee el problema y subraya sustantivos: suelen ser clases.
2) Identifica responsabilidades: se vuelven metodos.
3) Identifica datos importantes: se vuelven atributos.
4) Define relaciones: herencia, composicion, asociacion, etc.
5) Revisa si faltan interfaces o abstracciones.
6) Simplifica: solo lo necesario para explicar el patron o el caso.

## Errores comunes de principiantes
- Meter demasiados detalles: el diagrama se vuelve dificil de leer.
- Confundir herencia con asociacion.
- Olvidar cardinalidades cuando son importantes.
- Dibujar flechas sin significado claro.

## Consejos para estudiar patrones de diseno
- En cada patron, destaca la relacion principal que lo define.
- Usa nombres consistentes con las clases del codigo.
- Si dudas, pregunta: "Que clase depende de cual?"
- Prioriza claridad sobre cantidad de detalles.

## Convenciones de este proyecto
- Un diagrama por patron.
- Los nombres del diagrama coinciden con el patron.
- El UML se usa para explicar estructura, no ejecucion.
