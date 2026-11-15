# Matriz de alineación de los 23 GoF liminales

> **15 de noviembre de 2026 — pasada editorial selectiva completada.** Los 23 patrones permanecen aislados en su paquete. Se identificaron las escenas, las reglas normativas y las decisiones de ingeniería; se añadieron **17 sitios de comentarios breves** donde había un porqué técnico que merecía hacerse explícito. **En esa pasada no se cambió comportamiento Java ni se creó el integrador; tampoco se auditaron exhaustivamente todas las líneas.** El integrador se implementó **después**, en un bloque físicamente separado. [Estado actual de las tres rutas](../../docs/CIERRE_ACADEMICO_GOF.md).

**Fuentes:** [DOMINIO.md](DOMINIO.md) (R-00…R-14), [PLAN_INTEGRADOR.md](PLAN_INTEGRADOR.md) (tres bloques y sus límites), [GUIA_LECTURA_CODIGO.md](GUIA_LECTURA_CODIGO.md) (orden de lectura), [PLAN_VARIANTES.md](PLAN_VARIANTES.md) (siete variantes, independientes de estos 23 ejemplos).

## 1. Cómo estudiar esta matriz

Cada fila responde cuatro preguntas: **qué ocurre en el mundo**, **qué problema de diseño resuelve el patrón**, **qué restricciones impone el dominio** y **qué alternativa peligrosa o confusión conviene evitar**. «Comentado» indica que se justificó una decisión no evidente en un archivo Java; «ficha suficiente» significa que añadir otro comentario repetiría información ya visible.

Una posible mala práctica no es necesariamente un defecto actual. No llamar *God Object*, violación SOLID o composición UML a una clase solo por su nombre o por usar una colección. La clasificación solo se apoya en responsabilidades observables y reglas.

## 2. Creacionales — creación, identidad y coherencia

| Patrón / escena | Reglas | Decisión de ingeniería que se estudia | Confusión o riesgo que ayuda a evitar | Comentario |
| --- | --- | --- | --- | --- |
| [Singleton — panel de avisos](docs/patrones/creacionales/singleton.md) | R-11, R-14 | Una instancia compartida dentro del alcance de la demostración | Convertir una conveniencia local en regla universal o estado global obligatorio | [PanelAvisos](src/main/java/liminal/creacionales/singleton/PanelAvisos.java) |
| [Factory Method — aparición de criaturas](docs/patrones/creacionales/factorymethod.md) | R-01, R-08, R-12, R-14 | El creador concentra registro y validación; sus subclases eligen productos | Un producto inválido podría registrarse antes de comprobar identidad y habitación | [GeneradorCriatura](src/main/java/liminal/creacionales/factorymethod/GeneradorCriatura.java) |
| [Abstract Factory — pasillo y piscinas](docs/patrones/creacionales/abstractfactory.md) | R-05, R-13, R-14 | La escena recibe una fábrica abstracta y comprueba la coherencia de dos productos | Mezclar familias estéticas o duplicar verificaciones en varios clientes | [Escena](src/main/java/liminal/creacionales/abstractfactory/Escena.java) |
| [Builder — habitación configurable](docs/patrones/creacionales/builder.md) | R-01, R-12, R-13 | Separar construcción temporal mutable de producto inmutable | Introducir mapas, unicidad global o registro de IDs en un constructor por pasos | Ficha suficiente |
| [Prototype — plantilla de habitación](docs/patrones/creacionales/prototype.md) | R-01, R-10 (R-06 para artefactos físicos) | Copiar configuración con ID nuevo; preservar el tipo concreto usando `T` | Confundir plantilla de nombres con duplicación física o devolver `Object` sin necesidad | [Prototipo](src/main/java/liminal/creacionales/prototype/Prototipo.java) |

## 3. Estructurales — comunicación, recursos y referencias

| Patrón / escena | Reglas | Decisión de ingeniería que se estudia | Confusión o riesgo que ayuda a evitar | Comentario |
| --- | --- | --- | --- | --- |
| [Adapter — radio antigua](docs/patrones/estructurales/adapter.md) | R-07, R-14 | Traducir `enviar` a `emitirPorFrecuencia` por delegación | Cambiar una API heredada o fingir una integración de red real | Ficha suficiente |
| [Bridge — alertas por luz y radio](docs/patrones/estructurales/bridge.md) | R-07, R-11, R-14 | Alerta y canal evolucionan por separado mediante el contrato `CanalAviso` | Proliferación combinatoria de subclases por mensaje y medio | [AlertaLiminal](src/main/java/liminal/estructurales/bridge/AlertaLiminal.java) |
| [Composite — grupos de luces](docs/patrones/estructurales/composite.md) | R-07, R-12, R-14 | Una interfaz permite tratar luz y grupo uniformemente, con referencias compartibles | Confundir Composite GoF con composición UML, o aceptar ciclos | [GrupoLuces](src/main/java/liminal/estructurales/composite/GrupoLuces.java) |
| [Decorator — linterna ampliada](docs/patrones/estructurales/decorator.md) | R-06, R-12, R-14 | Envolver una linterna sin duplicar su objeto físico | Explosión de subclases para combinaciones de mejoras | Ficha suficiente |
| [Facade — cruce seguro](docs/patrones/estructurales/facade.md) | R-02, R-03, R-04, R-05, R-12 | Coordinar mapa, salida y explorador manteniendo sus responsabilidades | Fachada omnisciente, cambio parcial de ubicación o suponer puerta obligatoria | [FachadaCruce](src/main/java/liminal/estructurales/facade/FachadaCruce.java) |
| [Flyweight — texturas repetidas](docs/patrones/estructurales/flyweight.md) | R-01, R-10, R-13 | Clave-valor para recuperar textura inmutable compartida, separada de cada pared | Duplicar configuración o confundir textura con identidad de pared | [FabricaTexturas](src/main/java/liminal/estructurales/flyweight/FabricaTexturas.java) |
| [Proxy — cámara con pase](docs/patrones/estructurales/proxy.md) | R-07, R-11, R-12, R-14 | Autorizar antes de crear diferidamente la cámara real | Efectos secundarios y costo de instanciación ante acceso denegado | [ProxyCamara](src/main/java/liminal/estructurales/proxy/ProxyCamara.java) |

## 4. Comportamiento — decisiones, eventos y secuencias

| Patrón / escena | Reglas | Decisión de ingeniería que se estudia | Confusión o riesgo que ayuda a evitar | Comentario |
| --- | --- | --- | --- | --- |
| [Chain of Responsibility — validar un cruce](docs/patrones/comportamiento/chainofresponsibility.md) | R-02, R-04, R-05, R-12 | Cada validador atiende una condición y delega; la cadena no ejecuta el cruce | Mezclar verificación y mutación; creer que la variante *pipeline* es el base | [ValidadorCruce](src/main/java/liminal/comportamiento/chainofresponsibility/ValidadorCruce.java) |
| [Command — botones intercambiables](docs/patrones/comportamiento/command.md) | R-05, R-07, R-12 | El panel invoca `Comando` sin conocer receptores concretos | Acoplar la vista del panel al comportamiento de cada puerta o luz | [PanelBotones](src/main/java/liminal/comportamiento/command/PanelBotones.java) |
| [Interpreter — terminal de órdenes](docs/patrones/comportamiento/interpreter.md) | R-05, R-07, R-12, R-14 | Sintaxis local `ACCION ID` y expresiones terminales pequeñas | Confundir el lenguaje de demostración con una shell o parser general | Ficha suficiente |
| [Iterator — leer habitaciones](docs/patrones/comportamiento/iterator.md) | R-01, R-02 | Una instantánea de la colección separa el recorrido de nuevos registros | Confundir copia de lista con clonación de entidades | [RecorridoHabitaciones](src/main/java/liminal/comportamiento/iterator/RecorridoHabitaciones.java) |
| [Mediator — sensor de ruido](docs/patrones/comportamiento/mediator.md) | R-07, R-11, R-12 | El sensor notifica al mediador; el centro conoce luces y alarma | Acoplamiento del sensor a cada receptor y coordinación dispersa | [CentroControl](src/main/java/liminal/comportamiento/mediator/CentroControl.java) |
| [Memento — configuración de linterna](docs/patrones/comportamiento/memento.md) | R-06, R-12, R-14 | Guardar/restaurar intensidad y filtro sin cambiar la posesión física | Exponer y modificar libremente el estado interno | Ficha y Javadoc existentes suficientes |
| [Observer — interruptor y alarmas](docs/patrones/comportamiento/observer.md) | R-07 (R-11 para sincronía) | Congelar destinatarios durante notificación síncrona | Mutar la colección de receptores durante iteración; confundir observar con poseer | [Interruptor](src/main/java/liminal/comportamiento/observer/Interruptor.java) |
| [State — puerta bloqueada](docs/patrones/comportamiento/state.md) | R-05, R-12 | Cada estado define transiciones; fallo por estado actual lanza `IllegalStateException` | Condicionales dispersos y transiciones imposibles | [EstadoPuerta](src/main/java/liminal/comportamiento/state/EstadoPuerta.java) |
| [Strategy — monstruo oscuro](docs/patrones/comportamiento/strategy.md) | R-02, R-08, R-12 | Algoritmo intercambiable propone destino; entidad y mapa preservan invariantes | Dejar a una estrategia arbitraria teletransportar o clonar al monstruo | [MonstruoOscuro](src/main/java/liminal/comportamiento/strategy/MonstruoOscuro.java) |
| [Template Method — excursiones distintas](docs/patrones/comportamiento/templatemethod.md) | R-11, R-13, R-14 | Método `final` mantiene la secuencia; subclases solo especializan observación | Duplicar y desordenar pasos comunes en cada excursión | [RutaExploracion](src/main/java/liminal/comportamiento/templatemethod/RutaExploracion.java) |
| [Visitor — inspección de objetos](docs/patrones/comportamiento/visitor.md) | R-07, R-14 | Doble despacho para contar o describir puertas, luces y linternas | Cadena de `instanceof` para todas las operaciones | Ficha suficiente |

## 5. Criterios de arquitectura que acompañan la lectura

- **Cohesión:** reconocer qué información valida cada clase y por qué no delegar las invariantes del mapa en una vista o un cliente.
- **Acoplamiento / DIP:** depender de `Comando`, `EstrategiaMovimiento` o `CanalAviso` cuando realmente hay comportamientos intercambiables, no crear una interfaz por clase.
- **OCP:** nuevas estrategias o nuevos canales pueden añadirse sin reescribir el contexto; esto no significa que todo elemento sea extensible.
- **SRP:** registrar apariciones, crear criaturas, coordinar un cruce y conservar la ubicación son responsabilidades distintas aunque colaboren.
- **Males olores potenciales:** clase que lo hace todo, dependencia circular, herencia combinatoria, mutación parcial y abstracciones prematuras. Se describen como **riesgos que evita el diseño**, no como defectos inexistentes que ya se hayan observado.
- **Java/UML:** `record` para un valor real, `class` para una entidad/rol cuando corresponda; genéricos para preservar relaciones entre tipos; referencia compartida no equivale a propiedad compuesta.

## 6. Suficiencia y alcance de la alineación

- **23/23** fichas contrastadas con su escena, reglas y responsabilidad principal.
- **17** puntos de comentario en colaboradores seleccionados; **seis** casos deliberadamente sin comentario nuevo porque repetiría documentación o Javadoc.
- **7 variantes** y **2 laboratorios** conservados en sus ubicaciones actuales: no se fusionaron con los 23 patrones.
- **0** cambios de lógica de producción durante esta pasada editorial y **0** dependencias desde los 23 ejemplos aislados hacia el **integrador actual**, que se construyó posteriormente.
- La aprobación definitiva de compilación/pruebas corresponde a GitHub Actions para el commit final. Una revisión dirigida de responsabilidades y comentarios **no es** una inspección línea por línea de los 181 archivos Java de producción.
- **Regla de parada:** con CI aprobada y sin una contradicción de dominio demostrada, esta etapa es **suficiente para estudiar los patrones**. No expandir comentarios, UML, variantes o arquitectura por cumplir cuotas.

**Etapa posterior ya ejecutada:** el [proyecto integrador](../../proyectos/expedicion-liminal-integrada/README.md) concretó sus cuatro historias, paquetes e invariantes de mapa/posición **sin modificar estos 23 ejemplos ni obligar a integrar todos los GoF**. La matriz sigue siendo la guía de alineación de los ejemplos encapsulados.
