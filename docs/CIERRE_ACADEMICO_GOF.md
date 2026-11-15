# Cierre académico 1.0 — Tres rutas para estudiar GoF y arquitectura Java

> **15 de noviembre de 2026 · Dictamen editorial del alcance acordado.** «1.0» es la **versión académica del material**, no una etiqueta Git ni una publicación de software comercial. La fuente de verdad es el código de la rama `main` y las verificaciones de [GitHub Actions](https://github.com/marcos-moreira-dev/Patrones-de-dise-o-Java/actions).

## 1. Qué queda entregado

| Ruta | Código y material | Papel pedagógico | Estado |
| --- | --- | --- | --- |
| **1 — 23 GoF clásicos** | [Carpetas técnicas](../README.md#-estructura-del-proyecto), por ejemplo [State](../comportamiento/State/Context.java) | Aprender el contrato y la estructura GoF tradicional con clases Java | **Listo para estudiar** |
| **2 — 23 GoF liminales aislados** | [Colección](../colecciones/liminal/README.md), [matriz 23/23](../colecciones/liminal/MATRIZ_ALINEACION.md), [7 variantes](../colecciones/liminal/PLAN_VARIANTES.md), [2 laboratorios](../colecciones/liminal/AUDITORIA_PEDAGOGICA.md) | Estudiar cada patrón sin depender de otros, con dominio, UML, cliente, prueba y variantes justificadas | **Listo para estudiar** |
| **3 — Integrador liminal** | [Proyecto](../proyectos/expedicion-liminal-integrada/README.md) y [cuatro guiones](../proyectos/expedicion-liminal-integrada/docs/ESCENARIOS_02_04.md) | Estudiar la colaboración real de Strategy, Observer y Mediator, capas, invariantes y SOLID sin motor de juego | **Listo para estudiar** |

La colección 2 y la ruta 3 comparten **ideas y reglas normativas**, no las mismas clases Java; sus compilaciones y clientes permanecen independientes. No se modificaron las carpetas técnicas, Arena Java ni MiniSO en esta pasada de cierre.

## 2. Ruta corta para revisar el código sin perderse

**Primera lectura — State técnico tradicional.** Abre [`State.java`](../comportamiento/State/State.java) y [`Context.java`](../comportamiento/State/Context.java). Identifica el contrato de estados, la delegación del contexto y por qué el ejemplo utiliza dos estados genéricos. Su objetivo es **aislar el mecanismo**, no representar una puerta ni un juego.

**Segunda lectura — State liminal aislado.** Abre [la ficha State](../colecciones/liminal/docs/patrones/comportamiento/state.md), [`ClienteState.java`](../colecciones/liminal/src/main/java/liminal/comportamiento/state/ClienteState.java), [`Puerta.java`](../colecciones/liminal/src/main/java/liminal/comportamiento/state/Puerta.java) y el diagrama UML de la ficha. Compara `abrir`, `cerrar`, `bloquear` y `desbloquear` con las transiciones permitidas. Las pruebas no viven dentro del cliente. **Una puerta concreta** sirve para aprender la misma estructura del patrón sin forzarla en todos los ejercicios.

**Tercera lectura — colaboración integrada.** Abre el [escenario 01](../proyectos/expedicion-liminal-integrada/docs/ESCENARIO_01.md), [`ClienteExpedicion.java`](../proyectos/expedicion-liminal-integrada/src/main/java/liminal/integrado/presentacion/ClienteExpedicion.java), [`ProcesarTurno.java`](../proyectos/expedicion-liminal-integrada/src/main/java/liminal/integrado/aplicacion/ProcesarTurno.java) y [`Expedicion.java`](../proyectos/expedicion-liminal-integrada/src/main/java/liminal/integrado/dominio/exploracion/Expedicion.java). Después sigue el camino del evento desde [`SensorRuido`](../proyectos/expedicion-liminal-integrada/src/main/java/liminal/integrado/dominio/vigilancia/SensorRuido.java) a la [central](../proyectos/expedicion-liminal-integrada/src/main/java/liminal/integrado/dominio/vigilancia/CentroVigilancia.java). Al final, compara las [otras tres excursiones](../proyectos/expedicion-liminal-integrada/docs/ESCENARIOS_02_04.md): piscinas, radio y luces. **No están obligadas a incorporar otros 20 patrones GoF**.

**Preguntas de revisión:** ¿qué clase valida una salida? ¿quién conserva la identidad del monstruo? ¿una Strategy puede teletransportarlo? ¿el sensor sabe qué receptores están encendidos? ¿qué operación altera estado real y qué frase solo lo relata? Las respuestas están en el dominio, el caso de uso y la [auditoría de comportamientos](../proyectos/expedicion-liminal-integrada/docs/AUDITORIA_COMPORTAMIENTOS.md), no en `println` usados como sustitutos de lógica.

## 3. Evidencia de cierre y límite de confianza

| Control | Qué se revisó | Resultado |
| --- | --- | --- |
| **Java 21** | Workflow general y workflow específico del integrador, ambos en Ubuntu y Windows | **Aprobados** en la rama principal al comenzar la pasada editorial; comprobar también la ejecución del último commit en [Actions](https://github.com/marcos-moreira-dev/Patrones-de-dise-o-Java/actions) |
| **Navegación** | Los 50 archivos Markdown de las rutas liminal e integradora y el README raíz; destinos relativos en el árbol Git | **Sin destinos relativos faltantes** en la comprobación realizada |
| **UML de la colección** | 23 diagramas base, 2 diagramas de laboratorio y 3 secuencias: tamaños de lienzos, posiciones de textos y recuadros, cruces aparentes de paneles | **Sin desbordamientos geométricos principales detectados**; los recuadros anidados del diagrama de objetos son intencionales |
| **Correspondencia pedagógica** | Ejemplo clásico State, State liminal con reglas de puerta e integración de turno/sensor | **Roles y ruta de lectura trazables** |
| **Dominio integrado** | [Reglas](../proyectos/expedicion-liminal-integrada/DOMINIO.md), [arquitectura](../proyectos/expedicion-liminal-integrada/docs/ARQUITECTURA.md), [auditoría](../proyectos/expedicion-liminal-integrada/docs/AUDITORIA_FINAL.md) y [comportamientos](../proyectos/expedicion-liminal-integrada/docs/AUDITORIA_COMPORTAMIENTOS.md) | **Sin bloqueo conocido** para estudiar |

**Límites declarados:** la comprobación geométrica/XML de SVG **no sustituye mirar los 28 diagramas rasterizados en un visor**. No se hizo una auditoría manual exhaustiva línea por línea de todas las clases del catálogo. Los enlaces externos y las anclas internas de GitHub no se han validado exhaustivamente. Nada de eso bloquea **la versión académica lista para lectura**, y no se presenta como trabajo ya realizado.

La CI verifica compilación y pruebas, **no prueba por sí misma** todos los argumentos semánticos ni la calidad tipográfica. Si el último commit documental rompiera una comprobación automática, el cierre definitivo dependería de corregir ese fallo.

## 4. Qué no falta

No faltan 20 patrones adicionales en el integrador, un sistema de puertas completo en todas las excursiones, gráficos 3D, JavaFX, interfaces HTTP, base de datos, juego interactivo, persistencia, combate o nuevas capas vacías. Los métodos actuales **alteran estados, consultan valores o publican eventos reales** donde el guion los necesita. Su narración es independiente del dominio.

Tampoco se necesita borrar «andamiaje técnico» que mantiene pruebas, navegación y demostraciones reproducibles. El informe [`AUDITORIA_ANDAMIAJE.md`](../colecciones/liminal/AUDITORIA_ANDAMIAJE.md) explica su clasificación y los límites de auditoría.

## 5. Dictamen y regla de parada

**Listo para entregar y estudiar**: tres rutas claramente diferenciadas, dos catálogos de 23 GoF, variantes y laboratorios aislados, cuatro guiones con tres GoF colaborando, documentación de dominio y arquitectura, UML, pruebas Java 21 y límites de verificación expresos.

La revisión visual rasterizada completa y la lectura manual exhaustiva son **ampliaciones de calidad opcionales**. No iniciar refactorizaciones nuevas ni crear patrones de relleno sin un defecto demostrable o un objetivo pedagógico distinto. La revisión personal del estudiante puede comenzar con la ruta de la sección 2, sin necesidad de programar otro módulo.
