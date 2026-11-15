# Auditoría de andamiaje: qué es dominio, patrón y soporte técnico

**14 de noviembre de 2026. Auditoría estructural y revisión semántica parcial, exclusivamente de Expedición Liminal.** No se han eliminado archivos por esta auditoría. Falta comprobar por completo todos los ejemplos de esta colección. **Fuera de alcance:** patrones técnicos originales, Arena Java, MiniSO, scripts generales, workflow compartido y otros proyectos.

## 1. Categorías para decidir

| Código | Categoría | Pregunta | Política |
| --- | --- | --- | --- |
| **D** | Modelo de dominio | ¿Define entidades, hechos, restricciones o transiciones de R-00…R-14? | Conservar, comprobar invariantes |
| **P** | Patrón GoF | ¿Cumple un rol real del patrón o de su variante? | Conservar si su colaboración tiene efecto observable |
| **E** | Andamio educativo | ¿Ayuda a seguir el cliente, visualizar UML o aprender por consola? | Conservar separado de las reglas del mundo |
| **T** | Andamio técnico | ¿Compila, prueba, genera archivos o ejecuta CI? | Conservar o simplificar sin perder verificabilidad |
| **S** | Sobreestructura candidata | ¿Su eliminación reduce pasos sin perder P, D, E o T? | Investigar consumidores y pruebas antes de quitar |

**Una clase extra no es necesariamente andamiaje innecesario.** `DirectorAmbientes` es el rol a estudiar en una variante de Builder; `CatalogoPrototipos` es precisamente la variante Registry. En cambio, un registro de IDs de mapa dentro del Builder básico sí era una distracción y se retiró anteriormente.

## 2. Inventario de la colección auditada

Se verificó el árbol de GitHub bajo **`colecciones/liminal/`**: **290 archivos** en la instantánea revisada. No se incluye ningún archivo de las colecciones técnicas ni de los microproyectos.

| Área dentro de `colecciones/liminal/` | Archivos |
| --- | ---: |
| `src/main/java/` — Java de producción | 181 |
| `src/test/java/` — pruebas de la colección | 34 |
| `docs/` — fichas, UML y laboratorios | 60 |
| `assets/` — arte de apoyo | 5 |
| Documentos Markdown en la raíz liminal | 8 |
| `scripts/` — verificación específica de Liminal | 2 |
| **Total** | **290** |

El conteo es un inventario de estructura, **no una certificación semántica**. La auditoría sigue el recorrido de las clases de los **23 patrones GoF liminales, siete variantes, dos laboratorios y su documentación asociada**, sin intervenir en los demás proyectos.

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
| Fichas MD, UML, escenarios WebP y secuencias SVG | E | Contexto, no nuevas reglas del dominio |
| `PLAN.md` y `PLAN_VARIANTES.md` | E | Orientación exclusiva de la colección liminal; revisar indicaciones históricas obsoletas |

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

1. **Documentación antigua:** `PLAN.md` podría conservar nomenclatura histórica «FUTURO». Actualizar la navegación liminal sin borrar las decisiones antiguas.
2. **Verificador muy específico:** `VerificarDocumentacion.java` exige 23 fichas, siete variantes y cantidades fijas de imágenes. Es una protección razonable contra regresiones, aunque cada ampliación requiere actualizar inventarios.
3. **Instrumentación simulada:** el contador de `CamaraReal` es apoyo didáctico. Mantenerlo local al ejemplo; no promoverlo a un servicio general de cámaras.
4. **Dominio duplicado deliberadamente:** clases con el mismo nombre en paquetes distintos son ejercicios independientes. No extraer un framework común si eso impide leerlos a mano.
5. **Arte y builds:** `out/`, `target/` y `*.class` están en `.gitignore`; no son fuente que debamos conservar en Git.

## 7. Segunda pasada para declarar auditoría total

Para cada uno de los **23 patrones de Expedición Liminal**, siete variantes y dos laboratorios liminales:

- ¿La clase tiene un consumidor real y un rol D, P, E o T documentado?
- ¿Se puede quitar una clase intermedia sin perder un contrato GoF ni una regla R-XX?
- ¿La referencia, igualdad, copia, composición y multiplicidad UML representan lo que realmente hace Java?
- ¿Los fallos preservan invariantes sin transiciones parciales?
- ¿El cliente se entiende por métodos cortos, sin conocer backend, JavaFX o frameworks?
- ¿El código de prueba/infraestructura está separado del ejemplo y no se confunde con un patrón?

Registrar hallazgos con **ruta → rol → consumidor → coste → propuesta → prueba de regresión**. No borrar clases por nombre, cantidad de líneas o intuición. Toda poda requiere compilar Java 21, ejecutar pruebas, revisar UML y verificar enlaces.

**Alcance demostrado hasta ahora:** estructura de Liminal, revisión de declaraciones `record` del catálogo y revisión dirigida de implementaciones. **No equivale todavía a una auditoría semántica exhaustiva de los 290 archivos liminales.**

[Plan de variantes](PLAN_VARIANTES.md) · [Contrato del dominio](DOMINIO.md) · [Guía de lectura](GUIA_LECTURA_CODIGO.md).

## 8. Segunda pasada semántica — revisión dirigida de las siete variantes

Se examinaron las clases que contienen las responsabilidades principales de las siete demostraciones, junto con sus pruebas representativas. **Esta revisión dirigida no equivale a leer línea por línea los 290 archivos liminales.**

| Área y ruta | Rol / consumidor | Evidencia y criterio |
| --- | --- | --- |
| `prototype/variantes/copiaprofunda/` | P: plantilla y configuración; E: comparación de referencias | La copia superficial comparte las configuraciones mutables intencionalmente; la profunda copia cada configuración. La lista defensiva no equivale por sí misma a copia profunda |
| `prototype/variantes/registro/` | P: catálogo; D: habitación materializada | Los IDs se controlan dentro del catálogo, no mediante un supuesto registro global. La plantilla no recibe ID de entidad activa |
| `builder/variantes/director/` | P: director, contrato y builder concreto | El Director define la secuencia; no es infraestructura accidental. El rechazo de un ID inválido conserva el producto previo |
| `proxy/variantes/cache/` | P: proxy y cámara real; E: contador de generaciones | La versión de la escena invalida la vista en caché; el contador ilustra trabajo evitado, no una dependencia de producción |
| `command/variantes/deshacer/` | P: comando reversible e historial; D: puerta | La revisión de la puerta impide restaurar un estado obsoleto y el historial no mueve entradas después de un rechazo |
| `chainofresponsibility/variantes/pipeline/` | P: validadores enlazados | **Defecto corregido:** se podían crear ciclos indirectos que producirían recursión indefinida. `ValidadorCruce.enlazar` ahora verifica aciclicidad antes de alterar el enlace, con pruebas para ciclos de 2 y 3 nodos y autoenlace |
| `observer/variantes/pushpull/` | P: emisor y observadores | **Defecto corregido:** iterar directamente el mapa fallaba si un receptor se daba de baja durante el aviso. Se toma una instantánea previa de cada grupo y se prueba el auto-retiro. El orden push y luego pull es determinista, no orden global de suscripción |

**Decisión de poda:** ninguna clase de estas variantes se elimina en este lote: los tipos revisados conservan roles didácticos o de dominio identificables. Las clases cliente y de prueba siguen separadas. **La regresión de la cadena pasó GitHub Actions (commit `ceb2c044`) con Java 21.** La regresión de auto-retiro de Observer también pasó GitHub Actions con Java 21 (commit `4c7e708c`).

**Siguiente alcance aún abierto:** auditoría semántica de las 23 implementaciones base, correspondencia de flechas y multiplicidades en todos los UML liminales, laboratorios y utilidades de esta colección. Mantener la distinción entre esta revisión dirigida y una certificación total.



## 9. Segunda pasada semántica — Memento: aislar el originador

**Ruta → rol → consumidor:** `comportamiento/memento/Historial.java` (cuidador P) guarda instantáneas opacas de `Linterna` (originador D), utilizadas por `ClienteMemento` y `PruebaMemento`. También se inspeccionaron Facade, Iterator, State, Strategy, Composite, Mediator, Abstract Factory e Interpreter; se preservan sus colaboraciones actuales sin poda especulativa.

**Defecto reproducido de forma independiente en OpenJDK 21:** guardar una instantánea de la linterna A y llamar `historial.deshacer(B)` **cambiaba la linterna B** y consumía la instantánea de A. La configuración restaurada era válida (0..10), pero no pertenecía al objeto sobre el que se intentaba deshacer. Se ejecutó un harness aislado que imprimió `DEFECTO: historial de A restauro B a intensidad 4`.

**Corrección mínima:** el cuidador recuerda una referencia `origen` tras la primera captura. `guardar` y `deshacer` verifican identidad Java antes de modificar estado o historial; no se introduce una jerarquía ni una clase auxiliar. La referencia es una **asociación**, no propiedad UML. La ficha y el SVG de Memento representan esta decisión. `PruebaMemento` comprueba rechazo de guardado/restauración sobre otra linterna, invariantes sin mutación y que el recuerdo original sigue disponible. El mismo harness aislado, compilado con `javac --release 21`, pasa después del cambio. La verificación integral remota queda a cargo de GitHub Actions.

**Límite:** estos hallazgos no certifican todavía una inspección exhaustiva del resto de patrones y laboratorios de Expedición Liminal.

## 10. Segunda pasada semántica — Facade: mapa de procedencia

**Ruta → rol → consumidor:** `estructurales/facade/Explorador.java` (entidad D) y `FachadaCruce.java` (rol Facade P), consumidos por `ClienteFacade` y `PruebaFacade`.

**Defecto reproducido localmente con OpenJDK 21:** era posible crear un explorador en el mapa A (`H-01`) y una fachada con el mapa B (con `H-01` y `H-99`), cruzar hacia `H-99` y dejar al explorador **fuera de su mapa original**, vulnerando R-03. El programa aislado mostró `DEFECTO: explorador fuera de su mapa = true`.

**Corrección:** `Explorador` conserva una referencia al `MapaLocal` de origen y expone `perteneceA(mapa)` mediante comparación de identidad (`==`). El constructor de `FachadaCruce` rechaza un mapa ajeno antes de permitir cruces. La prueba crea expresamente dos mapas distintos con un ID de habitación en común, verifica rechazo y comprueba que no cambió la ubicación. La ficha y el diagrama UML reflejan la asociación **Explorador → MapaLocal** (sin composición).

**Validación:** el caso aislado de regresión se compiló con `javac --release 21` y pasó después del cambio, sin usar la computadora del estudiante. La prueba pertenece a la colección Liminal; GitHub Actions comprueba también su compilación, sin que esta auditoría inspeccione los demás proyectos.

## 11. Segunda pasada semántica — Factory Method: postcondiciones del creador

**Ruta → rol → consumidor:** `creacionales/factorymethod/GeneradorCriatura.java` (creador abstracto P), sus productos `Criatura` (P/D) y `RegistroApariciones` (D), utilizados por `ClienteFactoryMethod` y `PruebaFactoryMethod`.

**Defecto reproducido en OpenJDK 21:** `aparecer("M-01", "H-01", registro)` permitía que una subclase devolviera una criatura con ID `M-99` y ubicación `H-02`, siempre que esos valores también fueran válidos para el registro; el registro pasaba a contener la criatura distinta. La salida del harness aislado antes del cambio fue `SOLICITUD=M-01/H-01 RESULTADO=M-99/H-02 REGISTRADOS=1`. Se vulneraba la postcondición implícita de la solicitud y el control de identidad de R-01/R-08.

**Corrección mínima:** después de `crearCriatura` y antes de `registrar`, el creador común rechaza producto nulo o un producto cuya identidad/ubicación no coincida con los argumentos. No altera la jerarquía GoF ni añade clases al código de producción. Las pruebas incorporan tres fábricas defectuosas y confirman que los rechazos dejan libre el mismo ID para una aparición posterior válida.

**Andamiaje:** la comprobación pertenece al creador (contrato de producción), no al cliente de consola ni a infraestructura adicional. Actualizada la ficha del patrón. La validación integral depende de GitHub Actions con Java 21.

## 12. Criterio editorial ratificado: claridad humana

Esta auditoría **no busca maximizar defensas, capas o clases auxiliares**. En cada uno de los 23 ejemplos se debe poder abrir `ClienteX.java`, seguir sus métodos con nombres del dominio, reconocer los participantes GoF, contrastar su UML y ejecutar una prueba separada. Una clase adicional solo se justifica si representa un rol real, una entidad necesaria o una decisión de ingeniería demostrable. Un `record` como valor puede permanecer si se justifica; no se introduce en roles GoF por ahorrar sintaxis. **No se modifica el código de proyectos ajenos a `colecciones/liminal/`.**

## 13. Revisión transversal de las 23 rutas de lectura y los 23 UML principales

Se leyeron las **23 fichas Markdown**, los **23 archivos `ClienteX.java`** y los textos de los **23 SVG principales**. Todas las fichas enlazan su SVG homónimo y todos los clientes mantienen demostraciones separadas de pruebas (sin `AssertionError` en la clase cliente). El patrón complejo puede tener métodos pedagógicos intermedios; los cortos no se fragmentan artificialmente.

**Hallazgo Flyweight:** `docs/uml/flyweight.svg` declaraba `Map<Clave, Textura>`, aunque la fábrica Java utiliza `Map<ClaveTextura, Textura>`. Se corrigió el nombre de tipo; `ClaveTextura` sigue siendo un `record` objeto-valor, no una entidad ni un nuevo patrón. `VerificarDocumentacion` comprueba el nombre exacto en el SVG.

**Simplificación Iterator:** `RegistroHabitaciones.iterator()` hacía `List.copyOf(habitaciones)` antes de pasarla a `RecorridoHabitaciones`, cuyo constructor **ya** aplica `List.copyOf`. Se elimina la copia redundante en el registro, manteniendo la única copia defensiva en el iterador. `PruebaIterator` ya verifica que un iterador abierto no ve las habitaciones añadidas después y que los IDs repetidos se rechazan. No se cambia su contrato.

**Comprobado en este lote:** existencia, legibilidad del recorrido principal, separación cliente/prueba y correspondencia textual entre documentación y roles UML para los 23 patrones. **Pendiente todavía:** inspección pormenorizada de todos los atributos, multiplicidades, referencias y flechas de los SVG con su semántica Java, más la auditoría integral de las clases colaboradoras y de los laboratorios. La validación automatizada no equivale a inspección visual del trazado.

## 14. UML — modificadores y puntos de extensión GoF

**Hallazgo concreto:** las subclases de `Factory Method`, los tres validadores de `Chain of Responsibility` y las dos rutas de `Template Method` mostraban con `+` sus métodos redefinidos, mientras que Java declara `protected` (`#` en UML). En la base de Chain, `verificar` es de acceso de paquete (`~`), no público; su operación abstracta protegida `detectarRechazo` no estaba dibujada.

**Corrección editorial, sin cambios de lógica:** se corrigieron los tres SVG; ahora distinguen `public`, `protected` y acceso de paquete en las operaciones afectadas. `VerificarDocumentacion` incorpora verificaciones de los símbolos. La distinción es central: el punto de extensión accesible a una subclase no necesariamente forma parte de la API pública. Se evita añadir infraestructura o clases para representar un mero modificador.

**Aún pendiente:** la auditoría de accesibilidad del resto de operaciones UML y el cotejo visual de líneas/multiplicidades de la colección. No se atribuye revisión detallada completa a esta corrección puntual.

## 15. UML — visibilidad de colaboradores estructurales y creacionales

La revisión transversal identificó llamadas de **acceso de paquete** representadas erróneamente como `+` (públicas) en siete diagramas. Se cambió a `~` en los métodos correspondientes de `PanelAvisos` (Singleton), `HabitacionPrototipo` (lectores de identidad y lista), `RadioAntigua` (Adapter), `GrupoLuces.agregar` (Composite), `FachadaCruce`/`MapaLocal`/`PuertaPaso` (Facade) y `FabricaTexturas.obtener` (Flyweight). En Bridge, las operaciones `mensaje()` de `AlertaPuerta` y `AlertaRuido` son `protected` y ahora figuran con `#`, no `+`.

**Razonamiento:** enseñar `+` como público cuando Java no lo es desorienta al estudiante sobre el contrato real. Se dejan intactos el código de las clases y su estructura de roles GoF. `VerificarDocumentacion` incorpora aserciones de cadenas para estas visibilidades. Los tests estructurales no sustituyen la inspección visual de flechas ni de solapamientos.

**Avance UML:** revisiones de visibilidad aplicadas a Factory Method, Chain, Template Method y a estos siete diagramas adicionales; quedan por inspeccionar las visibilidades del resto de diagramas de comportamiento y los laboratorios.

## 16. UML — visibilidad de los patrones de comportamiento

Se contrastaron las **firmas Java** con los elementos textuales de los SVG de **Abstract Factory, Command, Interpreter, Iterator, Mediator, Memento, Observer, State y Strategy**. Los diagramas marcaban como públicos (`+`) algunos métodos **sin modificador** que en Java tienen acceso **de paquete** (`~`). Se corrigieron: `Escena.descripcion`; `PanelBotones.pulsar` y los receptores de Command; el analizador y el contexto de Interpreter; `Habitacion.id` en Iterator; receptores de Mediator; operaciones del originador/cuidador Memento; suscripción y pulsación Observer; operaciones de la puerta de State; y cambios de estrategia y consultas a mapa en Strategy.

**Sin cambios de código Java:** los diagramas representan el acceso real, no prescriben que se vuelva `public` toda la implementación. No se agregaron tipos ni capas. `VerificarDocumentacion` comprueba los símbolos principales. Para los demás cuatro diagramas sin modificación en este lote (Builder, Decorator, Proxy y Visitor), las operaciones dibujadas coinciden con su visibilidad de interfaz o método público en la revisión dirigida.

**Límite:** esta es la revisión textual de visibilidad de operaciones representadas; queda pendiente cotejar exhaustivamente las relaciones gráficas (sentido, multiplicidades, intersecciones y cobertura de tipos), variantes y laboratorios.

## 17. Laboratorio de relaciones UML — clases navegables sin alterar el modelo

**Hallazgo editorial:** `ClienteRelacionesUml.java` incluía **diez tipos superiores** (el cliente y nueve colaboradores) en un solo archivo. El código era válido, pero al abrir la carpeta en GitHub no se veían `MapaLiminal`, `Explorador`, `EquipoExploracion`, `RegistroExpedicion`, etc. Esto contrariaba el criterio de navegación del catálogo liminal: reconocer cada responsabilidad por nombre antes de abrir el código.

**Corrección:** se trasladaron los nueve tipos superiores a nueve archivos `.java` con sus nombres, manteniendo paquetes, nombres, visibilidad y comportamiento. `ClienteRelacionesUml.java` queda dedicado al recorrido visible del ejemplo. **No hay clases nuevas.** `RegistroExpedicion.EntradaRegistro` sigue anidada y privada, porque restringir quién puede crearla es una parte pedagógica de la composición R-09; no se separa artificialmente.

**Pruebas y documentación:** `VerificarDocumentacion` comprueba que cada colaborador tiene su propio archivo con un solo tipo superior; las pruebas `PruebaRelacionesUml` y la demostración de consola siguen validando referencias, equipos compartidos y cierre lógico. La ficha proporciona enlaces directos al código de cada participante. CI valida compilación y ejecución con Java 21, sin requerir instalaciones del estudiante.

## 18. Estado de cierre y siguientes pasos (inventario actualizado)

**Instantánea del árbol: 290 archivos en `colecciones/liminal/`**: 181 fuentes Java principales, 34 Java de prueba, 60 documentos/diagramas bajo `docs/`, cinco activos, ocho documentos raíz y dos scripts. El incremento de nueve archivos principales proviene de **separar los mismos nueve tipos existentes** en el laboratorio UML; no se agregaron nueve clases.

| Frente exclusivo de Expedición Liminal | Evidencia de esta etapa | Estado |
| --- | --- | --- |
| 23 clientes pedagógicos y 23 fichas | Lectura y contraste de la ruta principal | Revisado editorialmente |
| 23 UML de clases principales | XML/enlaces por CI, lectura textual de etiquetas, correcciones de visibilidad `public/protected/package` | Revisión textual terminada; inspección visual completa pendiente |
| 23 patrones: clases colaboradoras | Revisión dirigida y pruebas positivas/negativas; hallazgos y correcciones puntuales | Pendiente lectura semántica exhaustiva de todas las clases |
| Siete variantes | Roles de diseño, pruebas de cada variante y salidas de consola contrastados; CI | Revisadas; no se recomienda añadir variantes por cantidad |
| Laboratorio `class` frente a `record` | Clase y valor separados; mapa y equivalencia probados | Revisado |
| Laboratorio de relaciones UML | Tipos navegables, composición local y referencias independientes; CI tras división de archivos | Revisado |
| Andamiaje técnico **dentro de Liminal** | Verificadores específicos examinados; separación de los clientes y pruebas | Pendiente cierre de posibles redundancias locales |
| CI Java 21 | Trabajo en GitHub Actions, sin tocar la PC del estudiante | Aprobado en Linux y Windows para la división del laboratorio |

**Próximos pasos hasta la auditoría cerrada:** (1) comparar cada relación UML significativa con las referencias, multiplicidades y restricciones del código, incluyendo inspección gráfica; (2) terminar la lectura semántica de las clases colaboradoras de los 23 patrones y documentar solo hallazgos reproducibles; (3) revisar la relación coste-beneficio de los verificadores/scripts locales; (4) ejecutar la comprobación integral final y emitir un dictamen de cierre sin prometer corrección de cuestiones fuera del alcance.

**Restricción de alcance:** no se auditan ni modifican Arena Java, MiniSO, la colección técnica original ni el workflow global. La CI compartida puede ejecutarlos automáticamente, pero eso no los incorpora a este encargo.

## 19. Auditoría semántica dirigida — Facade, Observer y Composite

**Facade (S, simplificación local):** `EstadoPaso` contenía `BLOQUEADA`, pero `PuertaPaso` solo iniciaba en `CERRADA` y permitía pasar a `ABIERTA`, sin `bloquear` ni `desbloquear`. Era un estado **inalcanzable** que sugería mecánicas inexistentes para el ejemplo. Se retiró exclusivamente de este enum; `State` conserva los **tres estados** y enseña el bloqueo autorizado de R-05. Se añadieron comprobaciones explícitas de cierre/apertura y la ficha distingue los dos alcances. No se añadió infraestructura ni se cambió la ruta de cruce.

**Observer (P/D, prueba de robustez existente):** `Interruptor.pulsar` ya utilizaba `List.copyOf(observadores.values())`. La prueba no ejercitaba la modificación de suscripciones desde un callback. Ahora verifica un receptor que se da de baja y suscribe otro durante el aviso: los receptores ya presentes se notifican, el nuevo no recibe el aviso actual y entra en los posteriores. La ficha define explícitamente este comportamiento de instantánea.

**Composite (P/UML, propiedad por referencia):** `GrupoLuces` ya permitía que **dos grupos distintos referencien la misma luz**, porque carece deliberadamente de propiedad exclusiva. La prueba ahora muestra que apagar la luz mediante un grupo afecta a esa misma instancia desde el otro. También prueba el rechazo sin mutación de un duplicado **en el mismo grupo**. La ficha conecta ambos comportamientos con asociación UML en vez de asumir composición.

**Evaluación de andamiaje:** se eliminó **una constante de enum** inalcanzable, sin retirar clases, roles GoF ni colaboradores. Las dos ampliaciones de prueba documentan políticas existentes, no agregan defensas ni funciones al código de producción. Se conserva el estilo sencillo de consola. Las pruebas virtuales con Java 21 deben ratificar el cambio.

## 20. UML — Facade: cadena de asociaciones y puerta opcional

**Hallazgo:** el diagrama anterior mostraba cinco clases y trazaba una relación directa desde `MapaLocal` hasta `PuertaPaso`, pero Java realmente declara `MapaLocal.salidas: Map<Ruta, Salida>`, mientras `Salida.puerta` es la referencia opcional a `PuertaPaso`. Omitir `Salida` podía hacer pensar que no existe una entidad intermedia y volver a confundir «sin salida» con «salida sin puerta».

**Corrección:** el UML ahora incluye una caja `Salida`, el atributo `MapaLocal.salidas`, una asociación navegable `MapaLocal → Salida` y otra `Salida → PuertaPaso` con multiplicidad `0..1` del lado de la puerta. No se dibujaron rombos: el código conserva referencias, sin demostrar propiedad compuesta. Se amplió la altura del SVG para evitar comprimir las seis cajas y se ajustó la ficha. La prueba documental verifica los campos y la multiplicidad; las pruebas de Facade ya cubren ausencia de salida, salida sin puerta, puerta cerrada y mapa ajeno. **No cambia código de dominio.**

**Resto de auditoría UML:** cotejar relaciones y trazado del resto de los diagramas significativos; la verificación del texto y XML no sustituye la revisión visual completa.

## 21. UML — Flyweight: asociación de la fábrica a la caché

**Hallazgo:** el SVG de Flyweight dibujaba `FabricaTexturas → Textura` como una **dependencia discontinua**, pese a que `FabricaTexturas.texturas` es un `Map<ClaveTextura, Textura>` que **retiene referencias** a las instancias compartidas. `Pared → Textura` ya era una asociación continua.

**Corrección:** se sustituyó la dependencia fábrica-textura por una **asociación navegable** y se rotuló con `0..*` del lado de las texturas, sin rombos de propiedad. Se mantuvo `ClaveTextura` como `record` de clave valor en el atributo Java, sin inventar una entidad adicional. La ficha explica la diferencia y la verificación documental comprueba el nombre de la clave y la multiplicidad. **Sin cambios en implementación ni clientes.**

La auditoría semántica distingue entre la dependencia del cliente a la fábrica (uso) y las referencias almacenadas por la fábrica (estructura). Falta comprobar visualmente los SVG finales en los visores que utilizará el lector.

## 22. Matriz de revisión dirigida — 23 patrones base

Esta tabla registra la **responsabilidad mínima que se ha contrastado entre Java, cliente y pruebas**. No clasifica por tamaño: una clase solo se considera andamio descartable si eliminarla no borra un rol, regla o demostración. Las demostraciones se mantienen autónomas, sin extraer un dominio monolítico compartido.

| Patrón | Colaboración y decisión | Clasificación / resultado |
| --- | --- | --- |
| **Singleton** | `PanelAvisos`: instancia única para la demo; limpieza para aislar pruebas | P/E; conservar, no regla universal del mundo |
| **Factory Method** | Creador abstracto, dos creadores concretos y productos `Criatura`; registro de IDs | P/D; contrato de identidad y habitación corregido |
| **Abstract Factory** | Dos familias de luces/puertas con interfaces y coherencia de `Tema` | P/D; conservar productos concretos distintos |
| **Builder** | `ConstructorHabitacion` configura y entrega `Habitacion` | P; conservar versión base sin Director ni mapa |
| **Prototype** | `HabitacionPrototipo` conserva configuración y exige nueva identidad | P/D; conservar copia de nombres inmutables |
| **Adapter** | `AdaptadorRadioAntigua` traduce `Comunicador` a la radio existente | P; conservar delegación a objeto |
| **Bridge** | `AlertaLiminal` separa mensaje y `CanalAviso` | P; conservar dos dimensiones intercambiables |
| **Composite** | `GrupoLuces` y `LuzIndividual` realizan `Encendible` | P/D; prueba de ciclos y referencias compartidas; sin propiedad exclusiva |
| **Decorator** | `DecoradorLinterna` envuelve la base y suma mejoras | P; conservar referencia, no copiar base |
| **Facade** | `FachadaCruce` consulta `MapaLocal → Salida → PuertaPaso` antes de mover | P/D; conservar descriptor; mapa ajeno y estado inalcanzable corregidos |
| **Flyweight** | `FabricaTexturas` reutiliza `Textura` por clave valor | P/D; caché y asociación `0..*` documentadas |
| **Proxy** | `ProxyCamara` verifica pase y difiere creación de `CamaraReal` | P; contador de creación como evidencia local E |
| **Chain of Responsibility** | Tres validadores sucesivos, primer rechazo, salida sin puerta | P/D; conservar cadena y `SolicitudCruce` inmutable |
| **Command** | `PanelBotones` invoca órdenes que referencian `Puerta` o `Luz` | P/D; invocador y receptores separados |
| **Interpreter** | `InterpreteOrdenes` analiza dos instrucciones y produce expresiones | P/D; gramática mínima; no inventar lenguaje complejo |
| **Iterator** | `RegistroHabitaciones` y `RecorridoHabitaciones` implementan contratos Java | P/D; instantánea defensiva única |
| **Mediator** | `SensorRuido` informa a `CentroControl`, que coordina luz y alarma | P/D; conservar mediación sin mensajería adicional |
| **Memento** | `Linterna` crea `Recuerdo` opaco y `Historial` lo custodia | P/D; historial restringido al mismo originador |
| **Observer** | `Interruptor` notifica receptores sin poseerlos | P/D; prueba de cambio de suscripciones durante aviso |
| **State** | `Puerta` delega en tres clases de estado y verifica transiciones | P/D; único ejemplo base con bloqueo completo de R-05 |
| **Strategy** | `MonstruoOscuro` intercambia comportamientos dentro de un mapa dirigido | P/D; valida destino incluso frente a estrategia defectuosa |
| **Template Method** | `RutaExploracion.recorrer` fija el orden; subclases observan | P; conservar método `final` y punto de extensión protegido |
| **Visitor** | `Elemento.aceptar` delega a visitantes por doble despacho | P; conservar interfaces y visitantes independientes |

**Conclusión de esta pasada de responsabilidades:** no se identificó una clase participante GoF entera cuya eliminación sea segura y mejore la enseñanza. La única poda de producción de este bloque fue la constante de estado inalcanzable en Facade. `Salida`, `RegistroApariciones`, `Historial`, `Tema` y los contratos polimórficos son colaboradores con un propósito rastreable, **no capas técnicas decorativas**.

**Fuentes de validación:** pruebas de `PruebasLiminal` (23 casos base, siete variantes, dos laboratorios), verificación de fichas y SVG por `VerificarDocumentacion` y GitHub Actions con Java 21. Los checks de texto de un SVG no garantizan que sus flechas y etiquetas queden bien dispuestas visualmente.

**Para el cierre final aún falta:** (1) inspección gráfica de relaciones, etiquetas y posibles superposiciones de los 23 SVG y de los laboratorios; (2) última revisión de navegación, enlaces y comentarios/editorial entre siete variantes y los dos laboratorios; (3) confirmar en una ejecución final que las pruebas y ejemplos completos pasan en Linux y Windows, y emitir un dictamen de cierre. No se incluyen otros proyectos.

## 23. Revisión geométrica y semántica de UML y diagramas del laboratorio

Se inspeccionó el contenido SVG de los **23 diagramas principales** y los **dos diagramas del laboratorio UML**, comprobando tamaños de cajas, posiciones de texto, etiquetas, tipos y relaciones relevantes. La comprobación geométrica es aproximada; **no equivale a una inspección visual píxel a píxel**.

Hallazgos corregidos en el laboratorio: (1) se acortó el título de `EntradaRegistro` y se declaró su condición de clase anidada privada en un subtítulo; (2) se representó el atributo correcto `Map<String, EntradaRegistro>` en `RegistroExpedicion`; (3) se ajustó a `~` la visibilidad de métodos que no son públicos en Java; (4) se repartieron en dos líneas atributos largos para que sean legibles; (5) el diagrama de objetos muestra los nombres reales de tipos `EquipoExploracion`, `RegistroExpedicion`, `InformeExpedicion` en lugar de alias de tipos inexistentes; (6) se acortó una nota extensa que se aproximaba al borde derecho. Los enlaces y tests documentales ahora comprueban varios de estos detalles.

No se cambió lógica Java ni el número de tipos o participantes de patrones. Queda pendiente la **revisión final editorial de enlaces/variantes** y la emisión del dictamen de cierre, dejando constancia de las limitaciones de la inspección visual rasterizada.
