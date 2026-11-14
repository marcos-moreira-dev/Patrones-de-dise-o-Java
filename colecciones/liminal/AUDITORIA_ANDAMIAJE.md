# Auditoría de andamiaje: qué es dominio, patrón y soporte técnico

**14 de noviembre de 2026. Primera pasada estructural.** No se han eliminado archivos por esta auditoría. Una revisión semántica línea por línea de todos los ejemplos y microproyectos queda diferenciada como **segunda pasada**.

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
