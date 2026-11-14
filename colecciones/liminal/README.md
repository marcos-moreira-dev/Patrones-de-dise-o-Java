# Expedición Liminal — ruta visual de patrones GoF

**Estado: piloto implementado (3/23 patrones).** Ya existen demostraciones de **State, Strategy y Observer** con clases cliente, UML SVG, pruebas y ejecución por consola Java 21. Los otros 20 patrones y las imágenes atmosféricas están pendientes. Los ejemplos originales del repositorio siguen intactos.

## ¿Qué aprenderás aquí?

Los mismos patrones de diseño que se estudian con clases técnicas, pero usando escenas que puedes imaginar sin conocer backend: habitaciones, puertas, interruptores, linternas, cámaras, radios y monstruos oscuros.

**Java 21 por consola.** No se necesita JavaFX, interfaz gráfica ni un juego completo. Cada patrón tendrá una clase cliente visible, código corto, salida de consola, UML y una ficha ilustrada.

## Orden de lectura

1. [Cómo se define un dominio](../../docs/modelado/COMO_DEFINIR_UN_DOMINIO.md) — supuestos, invariantes, restricciones, precondiciones, postcondiciones y ejemplos comprobables.
2. [DOMINIO.md](DOMINIO.md) — las **reglas concretas** de Expedición Liminal, que no deben inventarse según convenga a cada patrón.
3. [PLAN.md](PLAN.md) — estructura de archivos, tabla de los 23 GoF, escenas propuestas, UML, pruebas y futura generación de imágenes.
4. [Fichas y UML del piloto](docs/patrones/comportamiento/) — [State](docs/patrones/comportamiento/state.md), [Strategy](docs/patrones/comportamiento/strategy.md) y [Observer](docs/patrones/comportamiento/observer.md).
5. [Guía general UML](../../UML_GUIDE.md) — clases, objetos, dependencias, asociaciones, agregación, composición y multiplicidades.

## Norma editorial

Primero una escena fácil de visualizar, luego una explicación de lo que ocurre con **instancias y referencias**, después las relaciones UML, el código Java y una prueba. Los comentarios del código serán breves; las explicaciones largas vivirán en Markdown. No se dibujarán rombos de UML solo por encontrar una lista o una instancia creada con `new`.

## Ejecutar con Java 21

Desde la raíz del repositorio en Git Bash o Linux:

~~~bash
bash colecciones/liminal/scripts/verificar.sh
java -cp colecciones/liminal/out liminal.DemoLiminal todos
java -cp colecciones/liminal/out liminal.DemoLiminal state
java -cp colecciones/liminal/out liminal.DemoLiminal strategy
java -cp colecciones/liminal/out liminal.DemoLiminal observer
~~~

En Windows PowerShell, desde la raíz:

~~~powershell
powershell -ExecutionPolicy Bypass -File .\colecciones\liminal\scripts\verificar.ps1
java -cp colecciones/liminal/out liminal.DemoLiminal todos
~~~

El script compila `src/main/java` y `src/test/java` a `colecciones/liminal/out` y ejecuta tres pruebas autocontenidas. No necesita JavaFX, Maven ni conexión a Internet.

## Las imágenes atmosféricas llegarán al final

El estilo tomará inspiración de Backrooms, Dreamcore/Weirdcore y *The Complex: Expedition*, sin copiar sus mapas, personajes concretos ni capturas. Se crearán imágenes originales de pasillos amarillos, piscinas vacías, luces y artefactos, según el inventario del [plan](PLAN.md). Las imágenes sirven para aprender y no cambian las reglas del dominio.

## Colección técnica existente

Los directorios de la raíz `creacionales/`, `estructurales/` y `comportamiento/` **no se moverán** en esta fase. Esta ruta liminal los complementará, no los reemplazará. Arena Java y MiniSO permanecen como microproyectos independientes.

**Siguiente etapa:** verificar el piloto con CI, auditar la fidelidad del UML y ampliar la colección por lotes, sin modificar las reglas del dominio solo para acomodar un patrón.
