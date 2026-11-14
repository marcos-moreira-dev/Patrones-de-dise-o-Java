# Auditoría de andamiaje: qué es dominio, patrón y soporte técnico

**14 de noviembre de 2026. Primera pasada estructural, con segunda pasada semántica iniciada.** No se han eliminado archivos por esta auditoría. Una revisión semántica línea por línea de todos los ejemplos y microproyectos sigue **pendiente de completarse**.

## 1. Categorías para decidir

| Código | Categoría | Pregunta | Política |
| --- | --- | --- | --- |
| **D** | Modelo de dominio | ¿Define entidades, hechos, restricciones o transiciones de R-00…R-14? | Conservar, comprobar invariantes |
| **P** | Patrón GoF | ¿Cumple un rol real del patrón o de su variante? | Conservar si su colaboración tiene efecto observable |
| **E** | Andamio educativo | ¿Ayuda a seguir el cliente, visualizar UML o aprender por consola? | Conservar separado de las reglas del mundo |
| **T** | Andamio técnico | ¿Compila, prueba, genera archivos o ejecuta CI? | Conservar o simplificar sin perder verificabilidad |
| **S** | Sobreestructura candidata | ¿Su eliminación reduce pasos sin perder P, D, E o T? | Investigar consumidores y pruebas antes de quitar |

**Una clase extra no es necesariamente andamiaje innecesario.** `DirectorAmbientes` es el rol a estudiar en una variante de Builder; `CatalogoPrototipos` es precisamente la variante Registry. En cambio, un registro de IDs de mapa dentro del Builder básico sí era una distracción y se retiró anteriormente.

## 2. Inventario verificado

Se inspeccionó la estructura del repositorio de GitHub: **516 archivos** en la instantánea examinada. Conteos de esa instantánea:

| Área | Archivos |
| --- | ---: |
| Java de patrones técnicos originales | 91 |
| Java de producción de Expedición Liminal | 172 |
| Java de pruebas de Expedición Liminal | 34 |
| Archivos bajo `colecciones/liminal/docs/` | 60 |
| Archivos bajo `colecciones/liminal/assets/` | 5 |
| Microproyecto Arena Java | 39 |
| Microproyecto MiniSO | 30 |
| Workflows de CI | 1 |

Son **conteos estructurales**, no una certificación automática de que cada archivo sea necesario.

## 3. Clasificación de archivos representativos

| Archivo o responsabilidad | Tipo | Conclusión |
| --- | --- | --- |
| `DOMINIO.md`, contratos R-XX, operaciones y entidades de cada escenario | D | Necesarios; tienen autoridad normativa sobre patrones |
| Interfaces, implementaciones y colaboradores de los 23 GoF | P | Necesarios cuando representan una responsabilidad auténtica |
| `DirectorAmbientes`, `CatalogoPrototipos`, `HistorialComandos` | P | Son el objeto de estudio de sus respectivas variantes, no «infraestructura de backend» |
| `EscenaGrabada`, contadores de cámara real | E | Simulan un coste medible para Proxy sin red ni cronómetros; no son parte obligatoria del mundo |
| `ClienteX.java` y `DemoLiminal.java` | E | Trazabilidad humana: mostrar qué se crea, en qué orden y qué ocurre |
| `PruebaX.java`, `PruebasLiminal.java`, `VerificarDocumentacion.java` | T | Pruebas; no deben confundirse con clases del patrón |
| `colecciones/liminal/scripts/verificar.sh` y `verificar.ps1` | T | Compilan y verifican con Java 21 sin pedir pruebas al usuario |
| `scripts/verificar.sh` y `.github/workflows/java21.yml` | T | Verificación transversal; pueden recompilar componentes, pero eso no los hace lógica del dominio |
| `docs/uml/generar-ejemplos.mjs` | T editorial | Genera SVG de la guía general, no es dependencia de ejecución Java |
| `proyectos/*/pom.xml` | T educativo | Maven es opcional para ejecutar manualmente, útil para aprender construcción y empaquetado |
| Fichas MD, UML, escenarios WebP y secuencias SVG | E | Contexto, no nuevas reglas del dominio |
| `PLAN.md`, `PLAN_VARIANTES.md`, `Tree.md` | E | Orientación; revisar indicaciones históricas obsoletas |

## 4. Auditoría de record en la colección liminal

Se examinaron **132 archivos Java de la ruta original**, excluyendo las nuevas variantes y laboratorios. Había **20 tipos record**. Tras alinear el catálogo clásico, los participantes GoF que necesitaban mostrar identidad, métodos y contratos explícitos quedaron como clases: Builder, Prototype, Factory Method, Abstract Factory, Interpreter, Iterator, Visitor, Decorator y los objetos principales de Flyweight.

Se conservaron **cuatro records de datos/valor**, no roles polimórficos GoF:

- `chainofresponsibility.Ruta` y `chainofresponsibility.SolicitudCruce`: solicitud y descripción de enlaces, inmutables.
- `facade.Ruta`: descriptor inmutable de origen y destino.
- `flyweight.ClaveTextura`: clave de caché, con igualdad por valor y `hashCode()`.

El [laboratorio Java moderno](docs/laboratorios/java/class-vs-record.md) explica por qué una clave de textura puede beneficiarse de record. **Corrección realizada:** `facade.Salida` se convirtió a clase porque conserva una referencia a una puerta mutable. El nombre sigue siendo un descriptor local de la conexión y no reemplaza por sí solo la entidad completa de salida definida en R-01/R-02.

## 5. Corrección técnica detectada durante la auditoría

En `FabricaTexturas.obtener()` se construía una instancia nueva de `Textura` **antes** de preguntar a la caché, incluso cuando existía una textura equivalente. Se cambió a `computeIfAbsent(clave, k -> new Textura(...))` para que la nueva textura se cree **solo en el fallo de caché**. Esto preserva el comportamiento visible de Flyweight y elimina trabajo superfluo. No se añadió ninguna clase o contador adicional para justificarlo.

## 6. Candidatos de simplificación, NO eliminados

1. **Documentación antigua:** `PLAN.md` y `Tree.md` todavía contienen o podrían conservar nomenclatura de la fase «FUTURO». Actualizar la navegación sin borrar la historia de decisiones.
2. **Doble compilación:** la CI compila fuentes y luego `scripts/verificar.sh` ejecuta sus propias compilaciones. Revisar si se puede reducir trabajo sin perder pruebas, Javadoc ni compatibilidad con Windows.
3. **Verificador muy específico:** `VerificarDocumentacion.java` exige 23 fichas, siete variantes y cantidades fijas de imágenes. Es una protección razonable contra regresiones, aunque cada ampliación requiere actualizar inventarios.
4. **Instrumentación simulada:** el contador de `CamaraReal` es apoyo didáctico. Mantenerlo local al ejemplo; no promoverlo a un servicio general de cámaras.
5. **Dominio duplicado deliberadamente:** clases con el mismo nombre en paquetes distintos son ejercicios independientes. No extraer un framework común si eso impide leerlos a mano.
6. **Arte y builds:** `out/`, `target/` y `*.class` están en `.gitignore`; no son fuente que debamos conservar en Git.

## 7. Segunda pasada para declarar auditoría total

Para cada uno de los 23 patrones, siete variantes, dos laboratorios y ambos microproyectos:

- ¿La clase tiene un consumidor real y un rol D, P, E o T documentado?
- ¿Se puede quitar una clase intermedia sin perder un contrato GoF ni una regla R-XX?
- ¿La referencia, igualdad, copia, composición y multiplicidad UML representan lo que realmente hace Java?
- ¿Los fallos preservan invariantes sin transiciones parciales?
- ¿El cliente se entiende por métodos cortos, sin conocer backend, JavaFX o frameworks?
- ¿El código de prueba/infraestructura está separado del ejemplo y no se confunde con un patrón?

Registrar hallazgos con **ruta → rol → consumidor → coste → propuesta → prueba de regresión**. No borrar clases por nombre, cantidad de líneas o intuición. Toda poda requiere compilar Java 21, ejecutar pruebas, revisar UML y verificar enlaces.

**Alcance demostrado hasta ahora:** estructura, rutas de soporte, revisión completa de las declaraciones record del catálogo liminal y revisión dirigida de implementaciones. **No equivale todavía a una auditoría semántica exhaustiva de los 516 archivos.**

[Plan de variantes](PLAN_VARIANTES.md) · [Contrato del dominio](DOMINIO.md) · [Guía de lectura](GUIA_LECTURA_CODIGO.md).

## 8. Segunda pasada semántica — revisión dirigida de las siete variantes

Se examinaron las clases que contienen las responsabilidades principales de las siete demostraciones, junto con sus pruebas representativas. **Esta revisión dirigida no equivale a leer línea por línea los 516 archivos del inventario original.**

| Área y ruta | Rol / consumidor | Evidencia y criterio |
| --- | --- | --- |
| `prototype/variantes/copiaprofunda/` | P: plantilla y configuración; E: comparación de referencias | La copia superficial comparte las configuraciones mutables intencionalmente; la profunda copia cada configuración. La lista defensiva no equivale por sí misma a copia profunda |
| `prototype/variantes/registro/` | P: catálogo; D: habitación materializada | Los IDs se controlan dentro del catálogo, no mediante un supuesto registro global. La plantilla no recibe ID de entidad activa |
| `builder/variantes/director/` | P: director, contrato y builder concreto | El Director define la secuencia; no es infraestructura accidental. El rechazo de un ID inválido conserva el producto previo |
| `proxy/variantes/cache/` | P: proxy y cámara real; E: contador de generaciones | La versión de la escena invalida la vista en caché; el contador ilustra trabajo evitado, no una dependencia de producción |
| `command/variantes/deshacer/` | P: comando reversible e historial; D: puerta | La revisión de la puerta impide restaurar un estado obsoleto y el historial no mueve entradas después de un rechazo |
| `chainofresponsibility/variantes/pipeline/` | P: validadores enlazados | **Defecto corregido:** se podían crear ciclos indirectos que producirían recursión indefinida. `ValidadorCruce.enlazar` ahora verifica aciclicidad antes de alterar el enlace, con pruebas para ciclos de 2 y 3 nodos y autoenlace |
| `observer/variantes/pushpull/` | P: emisor y observadores | **Defecto corregido:** iterar directamente el mapa fallaba si un receptor se daba de baja durante el aviso. Se toma una instantánea previa de cada grupo y se prueba el auto-retiro. El orden push y luego pull es determinista, no orden global de suscripción |

**Decisión de poda:** ninguna clase de estas variantes se elimina en este lote: los tipos revisados conservan roles didácticos o de dominio identificables. Las clases cliente y de prueba siguen separadas. **La regresión de la cadena pasó GitHub Actions (commit `ceb2c044`) con Java 21.** La regresión de auto-retiro de Observer requiere la misma comprobación de CI.

**Siguiente alcance aún abierto:** auditoría semántica de las 23 implementaciones base, correspondencia de flechas y multiplicidades en todos los UML, laboratorios, utilidades del repositorio y los dos microproyectos. Mantener la distinción entre esta revisión dirigida y una certificación total.

## 9. Segunda pasada técnica — eliminar compilación y pruebas duplicadas en CI

**Hallazgo:** el job Ubuntu del workflow `.github/workflows/java21.yml` compilaba los Java con `find | javac`, ejecutaba `arena.PruebasArena` y `miniso.PruebasMiniSO`, y luego invocaba `scripts/verificar.sh`, que **ya** vuelve a compilar las colecciones y ejecuta esas pruebas desde Maven.

**Inventario de cobertura comprobado:** todos los **344 archivos `.java`** del árbol se encuentran dentro de las colecciones clásicas (incluidos `App.java` y `Main.java`), Expedición Liminal, Arena Java o MiniSO. Ninguna fuente queda fuera del verificador común: clásicos (javac y menú), liminal (javac, pruebas y consola) y microproyectos (Maven `clean verify` con el plugin exec y pruebas), más dos smoke tests de JAR.

**Decisión:** eliminar los tres pasos redundantes del job Ubuntu y ejecutar una sola vez el script de verificación; conservar la generación independiente de **Javadoc** y el job de **Windows/Git Bash**. Es poda de andamiaje técnico T, sin modificar código de dominio, UML ni número de pruebas. La comprobación del nuevo workflow debe observarse en GitHub Actions, no suponerse por lectura de YAML.

## 10. Segunda pasada semántica — Memento: aislar el originador

**Ruta → rol → consumidor:** `comportamiento/memento/Historial.java` (cuidador P) guarda instantáneas opacas de `Linterna` (originador D), utilizadas por `ClienteMemento` y `PruebaMemento`. También se inspeccionaron Facade, Iterator, State, Strategy, Composite, Mediator, Abstract Factory e Interpreter; se preservan sus colaboraciones actuales sin poda especulativa.

**Defecto reproducido de forma independiente en OpenJDK 21:** guardar una instantánea de la linterna A y llamar `historial.deshacer(B)` **cambiaba la linterna B** y consumía la instantánea de A. La configuración restaurada era válida (0..10), pero no pertenecía al objeto sobre el que se intentaba deshacer. Se ejecutó un harness aislado que imprimió `DEFECTO: historial de A restauro B a intensidad 4`.

**Corrección mínima:** el cuidador recuerda una referencia `origen` tras la primera captura. `guardar` y `deshacer` verifican identidad Java antes de modificar estado o historial; no se introduce una jerarquía ni una clase auxiliar. La referencia es una **asociación**, no propiedad UML. La ficha y el SVG de Memento representan esta decisión. `PruebaMemento` comprueba rechazo de guardado/restauración sobre otra linterna, invariantes sin mutación y que el recuerdo original sigue disponible. El mismo harness aislado, compilado con `javac --release 21`, pasa después del cambio. La verificación integral remota queda a cargo de GitHub Actions.

**Límite:** estos hallazgos no certifican todavía una inspección exhaustiva del resto de patrones, laboratorios ni microproyectos.

## 11. Segunda pasada semántica — Facade: mapa de procedencia

**Ruta → rol → consumidor:** `estructurales/facade/Explorador.java` (entidad D) y `FachadaCruce.java` (rol Facade P), consumidos por `ClienteFacade` y `PruebaFacade`.

**Defecto reproducido localmente con OpenJDK 21:** era posible crear un explorador en el mapa A (`H-01`) y una fachada con el mapa B (con `H-01` y `H-99`), cruzar hacia `H-99` y dejar al explorador **fuera de su mapa original**, vulnerando R-03. El programa aislado mostró `DEFECTO: explorador fuera de su mapa = true`.

**Corrección:** `Explorador` conserva una referencia al `MapaLocal` de origen y expone `perteneceA(mapa)` mediante comparación de identidad (`==`). El constructor de `FachadaCruce` rechaza un mapa ajeno antes de permitir cruces. La prueba crea expresamente dos mapas distintos con un ID de habitación en común, verifica rechazo y comprueba que no cambió la ubicación. La ficha y el diagrama UML reflejan la asociación **Explorador → MapaLocal** (sin composición).

**Validación:** el caso aislado de regresión se compiló con `javac --release 21` y pasó después del cambio, sin usar la computadora del estudiante. La comprobación integral de las colecciones y ambos microproyectos se realiza además con GitHub Actions.

## 12. Segunda pasada semántica — Factory Method: postcondiciones del creador

**Ruta → rol → consumidor:** `creacionales/factorymethod/GeneradorCriatura.java` (creador abstracto P), sus productos `Criatura` (P/D) y `RegistroApariciones` (D), utilizados por `ClienteFactoryMethod` y `PruebaFactoryMethod`.

**Defecto reproducido en OpenJDK 21:** `aparecer("M-01", "H-01", registro)` permitía que una subclase devolviera una criatura con ID `M-99` y ubicación `H-02`, siempre que esos valores también fueran válidos para el registro; el registro pasaba a contener la criatura distinta. La salida del harness aislado antes del cambio fue `SOLICITUD=M-01/H-01 RESULTADO=M-99/H-02 REGISTRADOS=1`. Se vulneraba la postcondición implícita de la solicitud y el control de identidad de R-01/R-08.

**Corrección mínima:** después de `crearCriatura` y antes de `registrar`, el creador común rechaza producto nulo o un producto cuya identidad/ubicación no coincida con los argumentos. No altera la jerarquía GoF ni añade clases al código de producción. Las pruebas incorporan tres fábricas defectuosas y confirman que los rechazos dejan libre el mismo ID para una aparición posterior válida.

**Andamiaje:** la comprobación pertenece al creador (contrato de producción), no al cliente de consola ni a infraestructura adicional. Actualizada la ficha del patrón. La validación integral depende de GitHub Actions con Java 21.

## 13. Segunda pasada semántica — Arena Java: estabilidad de Observer

**Ruta → rol → consumidor:** `proyectos/arena-java/src/main/java/arena/aplicacion/ServicioPartida.java` (servicio de aplicación y sujeto Observer P) publica `EventoCombate` a receptores `ObservadorPartida` utilizados por consola y `PruebasArena`.

**Defecto detectado:** la publicación recorría directamente un `ArrayList` mutable de observadores. Si un callback llamaba a `ServicioPartida.suscribir`, el iterador podía lanzar `ConcurrentModificationException` durante el aviso. Además, sin una política explícita no estaba definido si el alta debía participar en el segundo evento del mismo turno.

**Corrección:** después de resolver una acción válida, se captura una única `List.copyOf(observadores)` para **todos los eventos de esa acción**; así el alta durante el turno no modifica los destinatarios actuales y participa desde la acción siguiente. Se conserva el carácter síncrono y el orden de suscripción. `PruebasArena.observadores()` verifica que el receptor original ve los dos eventos, el nuevo no ve ninguno del primer turno y sí ve los dos del siguiente. La decisión se documenta en el README sin añadir un gestor de eventos.

**Límite consciente:** los callbacks con excepciones siguen siendo responsabilidad del consumidor; este cambio no promete revertir el combate si un observador falla. No se añaden hilos ni una infraestructura de eventos compleja. Verificación integral en GitHub Actions Java 21 Linux/Windows.
