# Auditoría arquitectónica final — Expedición Liminal Integrada

> **15 de noviembre de 2026 · Cierre académico dirigido.** Alcance: `proyectos/expedicion-liminal-integrada/` y enlaces documentales. Se contrastaron las responsabilidades principales, los contratos, las relaciones UML, los patrones integrados y sus pruebas. **No** es una auditoría de seguridad, rendimiento, concurrencia o preparación para producción.

## 1. Hallazgos verificados

| Pregunta de ingeniería | Evidencia | Conclusión |
| --- | --- | --- |
| ¿Hay dominio real e invariantes? | [DOMINIO.md](../DOMINIO.md), R-INT-01…R-INT-12 | Sí: identidad, rutas dirigidas, sesiones, criatura y vigilancia |
| ¿La consola decide reglas del mundo? | [ClienteExpedicion](../src/main/java/liminal/integrado/presentacion/ClienteExpedicion.java) | No: prepara y relata un escenario determinista |
| ¿Un cliente puede mover al monstruo de la sesión fuera de turno? | [Expedicion](../src/main/java/liminal/integrado/dominio/exploracion/Expedicion.java) + [EstadoCriatura](../src/main/java/liminal/integrado/dominio/criaturas/EstadoCriatura.java) | No por API pública: la sesión crea su instancia privada y devuelve una vista inmutable |
| ¿Una Strategy puede teletransportar? | [MonstruoOscuro](../src/main/java/liminal/integrado/dominio/criaturas/MonstruoOscuro.java), [MapaLiminal](../src/main/java/liminal/integrado/dominio/mundo/MapaLiminal.java) | No: la entidad comprueba rutas antes de mutar |
| ¿El sensor conoce a luz y alarma? | [SensorRuido](../src/main/java/liminal/integrado/dominio/vigilancia/SensorRuido.java), [CentroVigilancia](../src/main/java/liminal/integrado/dominio/vigilancia/CentroVigilancia.java) | No: Observer distribuye el evento, Mediator coordina los receptores |
| ¿Se mezclaron los 23 GoF liminales aislados con el integrador? | Paquetes e imports del código; compilación independiente | No se importan sus clases ni se depende de ellos para compilar |
| ¿El ejemplo se puede verificar sin jugar? | [script](../scripts/verificar.sh) y pruebas | Sí: escenario predeterminado y pruebas autónomas Java 21 |

## 2. Arquitectura, SOLID y hábitos de ingeniería

**Cohesión / SRP.** `MapaLiminal` valida el grafo; `Explorador` mantiene ubicación; `Expedicion` controla turnos y criatura privada; `MonstruoOscuro` protege su invariante; `ProcesarTurno` adapta resultados y determina si se avisa; `CentroVigilancia` coordina receptores. No se creó un `MotorJuego` omnisciente.

**OCP.** `EstrategiaMovimiento` permite nuevas políticas sin cambiar la estructura de `MonstruoOscuro`. No significa que una política arbitraria sea válida: la entidad conserva la validación.

**DIP práctico.** `MonstruoOscuro` utiliza una interfaz Strategy y `SensorRuido` una interfaz Observer; no se añaden interfaces para cada clase o un contenedor DI por ritual. ISP aparece modestamente en contratos pequeños. No se afirma que el diseño pruebe formalmente todos los principios SOLID.

**Referencias y ciclo de vida.** La anterior fuga de mutabilidad (`Expedicion.criatura()`) se corrigió. La sesión crea internamente su monstruo y expone `EstadoCriatura` como `record`. Un `MonstruoOscuro` independiente sigue teniendo métodos públicos para demostrar y probar Strategy, pero **no es** la instancia privada de la expedición. Una referencia privada no equivale automáticamente a composición UML.

**Valores y entidades.** `Posicion`, `EstadoCriatura`, `AvanceExpedicion`, `EventoRuido` y `ResultadoTurno` son valores. `Habitacion`, `Explorador`, `MonstruoOscuro` y `Expedicion` conservan identidad. No se introduce `record` únicamente por concisión.

**Errores y atomicidad.** Un registro inválido se rechaza antes de mutar índices; una transición de movimiento imposible usa `IllegalStateException`. Si la Strategy falla **después** de un desplazamiento aceptado, la sesión informa ese rechazo secundario sin fingir rollback total. Las notificaciones de Observer son síncronas y su propagación de excepciones también es explícita.

**Observer frente a Mediator.** Observer determina quién recibe `EventoRuido` y permite modificar suscripciones sin afectar a la ronda actual. Mediator decide qué hacer con el aviso: enciende `LuzAlerta` y registra en `Alarma`. No es doble implementación del mismo comportamiento.

## 3. Evidencias de prueba

| Programa | Verifica |
| --- | --- |
| [PruebasExpedicion.java](../src/test/java/liminal/integrado/PruebasExpedicion.java) | IDs y celdas únicas, límites, salidas dirigidas, rechazos, turnos y finalización |
| [PruebasCriaturas.java](../src/test/java/liminal/integrado/PruebasCriaturas.java) | Strategy, identidad, rutas, vista inmutable, sesión protegida, rechazos secundarios |
| [PruebasVigilancia.java](../src/test/java/liminal/integrado/PruebasVigilancia.java) | Fuente autorizada, coordinación de luz/alarma, suscripciones durante callbacks, evento solo por entrada del monstruo |
| [scripts/verificar.sh](../scripts/verificar.sh) | Compilación Java 21, tres pruebas y fragmentos de consola del escenario |
| CI del integrador Linux/Windows | Compilación y pruebas en ambos entornos |
| CI general Java 21 | Verificación de ejemplos clásicos, liminales aislados, Arena Java y MiniSO |
| [PruebasGuiones.java](../src/test/java/liminal/integrado/PruebasGuiones.java) | Piscinas con igual apariencia, señales de radio según habitación/frecuencia y evento de luces una sola vez |

## 4. Tres patrones que sí se justifican

| Patrón GoF | Colaboradores | Problema específico | Regla |
| --- | --- | --- | --- |
| **Strategy** | `MonstruoOscuro`, `EstrategiaMovimiento`, `Patrullar`, `Perseguir`, `Esperar` | Una criatura cambia conducta sin violar mapa ni identidad | R-INT-07/08/09 |
| **Observer** | `SensorRuido`, `ObservadorRuido`, `EventoRuido`, `CentroVigilancia` | Difundir una detección sin acoplar sensor y receptores | R-INT-10/11 |
| **Mediator** | `CentroVigilancia`, `LuzAlerta`, `Alarma` | Coordinar dos reacciones ante un mismo evento | R-INT-11 |

Los otros **20 GoF** —Singleton, Factory Method, Abstract Factory, Builder, Prototype, Adapter, Bridge, Composite, Decorator, Facade, Flyweight, Proxy, Chain of Responsibility, Command, Interpreter, Iterator, Memento, State, Template Method y Visitor— se estudian en el [catálogo de ejemplos encapsulados](../../../patrones/liminales/MATRIZ_ALINEACION.md), **no son deuda** del integrador. Introducir un bus de eventos, fábricas, comandos, un controlador MVC o una cámara sin necesidad sería sobreingeniería.

## 5. Limitaciones conocidas, no bloqueantes para la meta académica

1. **Callbacks síncronos.** Si un observador lanza una excepción inesperada, el movimiento puede haberse confirmado antes de que la llamada falle. No existen transacciones, reintentos ni cola de eventos. Está expresado en [DOMINIO.md](../DOMINIO.md); no se disfraza con `catch` genérico.
2. **Mapa mutable.** El mapa permite registrar rutas válidas incluso tras construir la expedición. El cliente completa su construcción antes de los turnos. Una topología congelada requeriría otra regla explícita, no se supone implícita.
3. **Una criatura y un sensor en la escena.** Es una limitación didáctica, no una afirmación de escalabilidad. No justifica agregar servicios o un motor global.
4. **Autorización por ID.** `sensorAutorizado` es un filtro de demostración, no seguridad criptográfica ni red.
5. **Pruebas autónomas.** No hay benchmark, prueba de concurrencia ni medición exhaustiva de cobertura. Java 21 y CI bastan para este laboratorio.

La auditoría no promete ausencia absoluta de defectos. Identifica y documenta los límites conocidos en vez de crear nuevas capas por perfeccionismo.

## 6. Regla de cierre: tres rutas independientes

- **Bloque 1:** catálogo clásico GoF intacto.
- **Bloque 2:** 23 patrones liminales aislados, siete variantes y dos laboratorios; [alineación editorial](../../../patrones/liminales/MATRIZ_ALINEACION.md) conservada.
- **Bloque 3:** integrador Java 21, dominio R-INT-01…R-INT-12, **tres GoF colaborando**, diagramas y pruebas propias.

Se considera **suficiente** cuando el lector puede justificar dónde vive una regla, cómo colaboran clases de paquetes distintos, por qué se usan esos tres patrones, cómo se rechaza un estado inválido y qué complejidad deliberadamente se descartó. No hace falta ejecutar manualmente el cliente, construir una interfaz o insertar los restantes 20 GoF.

La CI del **último commit** debe constatarse en [GitHub Actions](https://github.com/marcos-moreira-dev/Patrones-de-dise-o-Java/actions) antes de afirmar cierre técnico definitivo; esta auditoría documental no la sustituye.

## 7. Auditoría de la ampliación lúdica (15 de noviembre de 2026)

Se añadieron [tres guiones](ESCENARIOS_02_04.md) a la expedición inicial: piscinas (identidad frente a apariencia y rutas de un solo sentido), radio (mensajes predefinidos, nueva regla **R-INT-12**) y luces (Strategy + Observer + Mediator en una sala distinta). Los tres clientes comparten **las clases de dominio** pero crean mapas independientes, sin un motor de guiones central. 

La radio se implementó como un dispositivo sencillo, **sin incorporar Adapter/Bridge por decoración**. La cuarta prueba, `PruebasGuiones`, verifica los relatos reproducibles y los rechazos. Los riesgos documentados en la auditoría original permanecen válidos: esta ampliación es lúdica y didáctica, no infraestructura de producción.

## 8. Auditoría dirigida de métodos y relatos

Se completó la [auditoría de comportamientos](AUDITORIA_COMPORTAMIENTOS.md): inspección dirigida de **21 archivos de dominio** y revisión de los cuatro clientes. No se encontraron impresiones `System.out` ni marcadores `TODO/FIXME` en esos archivos de dominio. Los relatos ahora distinguen transiciones verificadas (ubicación de criatura, luz apagada→encendida, frecuencia de radio, rechazo de salida) de ambientación estática. Se reforzó [PruebasGuiones](../src/test/java/liminal/integrado/PruebasGuiones.java) para evitar que los clientes narren cambios que no ocurrieron. No se agregaron métodos `apagar`, `parpadear` ni `aparecer` sin reglas concretas.

## 9. Refactorización de claridad y tratamiento de fallos (15 de noviembre de 2026)

La revisión posterior del integrador conservó el dominio y los cuatro guiones, pero separó **preparación del escenario** y **reproducción del relato** en los clientes de presentación. `ClienteExpedicion` también distingue inicio, cambios de Strategy por paso, narración de un turno y cierre. Los pequeños `record` privados de escena solo agrupan objetos de apoyo: no son entidades del dominio ni una nueva capa.

En `ProcesarTurno` se distinguen la **coordinación del turno**, la **notificación del sensor** y la **construcción de su descripción**. Para evitar que un fallo inesperado se disfrace de una dirección inválida, `MovimientoRechazadoException` (subtipo de `IllegalStateException`) identifica los rechazos previstos del dominio; `ProcesarTurno` captura únicamente ese tipo. Una Strategy que lance otra `IllegalStateException` deja propagar el error. `Expedicion` tampoco lo oculta: conserva la política documentada de no revertir un movimiento válido del explorador tras un fallo posterior de la criatura.

Las pruebas de criaturas separan encapsulamiento, finalización, incorporación tardía y fallo inesperado de Strategy. Las de vigilancia separan notificación por movimiento de ausencia de detección cuando la criatura no se mueve. **No se incorporaron controladores, repositorios, servicios adicionales ni patrones nuevos**. El script `scripts/verificar.sh` y el workflow para Java 21 siguen siendo la evidencia ejecutable de regresión; su resultado debe verificarse para el commit correspondiente en GitHub Actions.
