# Expedición Liminal — PLAN DE IMPLEMENTACIÓN

> **Estado:** 23 patrones GoF con cliente, prueba, UML y ficha. Tres ilustraciones originales (pasillo, piscinas, oficinas) integradas y enlazadas; faltan posibles láminas de objetos/criaturas y auditorías visuales ampliadas. **Versión de planificación:** 1.3 (2026-11-14).
> Fuente normativa: [DOMINIO.md](DOMINIO.md). Método de modelado: [Cómo definir un dominio](../../docs/modelado/COMO_DEFINIR_UN_DOMINIO.md).

## 1. Meta didáctica

Una **segunda colección** de los patrones GoF, independiente de los ejemplos técnicos actuales, sin construir un videojuego completo. Las situaciones ocurren en un mundo de exploración liminal; cada patrón se ejecuta en **Java 21 por consola** y puede estudiarse por separado.

**Principio:** las reglas del dominio deciden qué está permitido; el patrón decide cómo distribuir responsabilidades. No se enseñan las asociaciones, agregaciones o composiciones por la mera existencia de un campo o una lista.

**Audiencia:** alguien que ya conoce algo de Java, pero no necesariamente backend, API, infraestructura o el lore de internet.

**No metas:** JavaFX, motor de juego, gráficos en tiempo real, infraestructura web, frameworks, integración obligatoria entre los 23 ejemplos o Javadoc repetitivo en cada línea.

## 2. Organización propuesta (sin alterar los ejemplos existentes)

```text
Patrones-de-dise-o-Java/
├── README.md                         # Entrada general existente
├── UML_GUIDE.md                      # Guía compartida
├── docs/
│   └── modelado/
│       └── COMO_DEFINIR_UN_DOMINIO.md
├── creacionales/                     # Colección técnica EXISTENTE: se conserva
├── estructurales/                    # Colección técnica EXISTENTE: se conserva
├── comportamiento/                   # Colección técnica EXISTENTE: se conserva
├── proyectos/
│   ├── arena-java/                   # Microproyecto EXISTENTE
│   └── miniso/                       # Microproyecto EXISTENTE
└── colecciones/
    └── liminal/
        ├── README.md
        ├── DOMINIO.md                # Reglas normativas
        ├── PLAN.md                   # Este archivo
        ├── assets/                   # FUTURO: imágenes originales y sus créditos
        │   ├── ambientes/
        │   └── objetos/
        ├── docs/                     # FUTURO: UML SVG y fichas de patrones
        │   ├── uml/
        │   └── patrones/
        ├── src/main/java/liminal/    # FUTURO: Java 21
        │   ├── comun/                # Mínimo núcleo compartido, si aporta valor
        │   ├── creacionales/
        │   ├── estructurales/
        │   ├── comportamiento/
        │   └── DemoLiminal.java
        ├── src/test/java/liminal/    # FUTURO: pruebas sin dependencias externas
        └── scripts/                  # FUTURO: ejecutar/verificar en consola
```

**Regla de migración:** NO mover los directorios técnicos existentes a `colecciones/tecnica/` durante esta iniciativa: eso alteraría paquetes, imports, scripts y enlaces sin aportar al objetivo inicial. En la navegación los llamaremos «colección técnica existente», mientras el código nuevo vive en `colecciones/liminal/`.

**Aislamiento:** cada patrón tiene un paquete propio y una clase cliente explícita (`Ejemplo<Patron>` o equivalente). Los ejemplos pueden utilizar conceptos del glosario, pero **no deben depender entre sí**. El directorio `comun` contendrá solo tipos que simplifiquen realmente el aprendizaje; evitar un mini-framework.

## 3. Tabla de trazabilidad: los 23 patrones GoF

**Los escenarios son candidatos de diseño, no nuevas reglas globales.** Cada ejemplo deberá enlazar los ID del contrato que utiliza y definir su regla local cuando haga falta.

| Categoría | Patrón | Escena simple y reconocible | Colaboración principal / foco |
| --- | --- | --- | --- |
| Creacional | Singleton | Un panel central de avisos compartido en una demo | Acceso a instancia única *de esa demo*, no ley universal |
| Creacional | Factory Method | Un generador de apariciones decide qué monstruo crear | Método de fábrica redefinido por creadores |
| Creacional | Abstract Factory | Crear conjuntos coherentes de luces y puertas para ambiente «amarillo» o «piscinas» | Familias de productos compatibles, R-13 |
| Creacional | Builder | Preparar una habitación por pasos y validarla al terminar | Construcción gradual, R-01/R-02 |
| Creacional | Prototype | Duplicar la plantilla de una habitación conservando configuración, no ID activo | Copia de prototipo, R-10 |
| Estructural | Adapter | Un adaptador permite utilizar una radio antigua con una interfaz nueva | Traducir contratos incompatibles |
| Estructural | Bridge | Una alerta se comunica por radio o señal de luz | Separar tipo de alerta del canal de entrega |
| Estructural | Composite | Un panel permite encender luces individuales o grupos de luces | Interfaz uniforme para elemento y grupo; **no asumir composición UML** |
| Estructural | Decorator | Añadir filtros o mejoras a una linterna | Envoltura que añade comportamiento sin modificar la base |
| Estructural | Facade | Un asistente coordina «salir de una habitación» | Interfaz simplificada para varias operaciones, R-04/R-05 |
| Estructural | Flyweight | Varias paredes comparten un tipo de textura inmutable | Reutilizar estado compartido sin compartir identidad de entidad |
| Estructural | Proxy | Un acceso controlado a una cámara muestra una vista solo con permiso ficticio | Sustituto que controla acceso a un servicio del ejemplo |
| Comportamiento | Chain of Responsibility | Varias comprobaciones deciden si una salida puede atravesarse | Cadena con orden y resultado explícitos, R-04/R-12 |
| Comportamiento | Command | Un panel crea y ejecuta órdenes «abrir puerta» y «encender luz» | Acción encapsulada y ejecutor desacoplado |
| Comportamiento | Interpreter | Interpretar miniinstrucciones `ABRIR P1` y `ENCENDER L2` | Gramática acotada y expresiones interpretables |
| Comportamiento | Iterator | Recorrer habitaciones de un mapa sin exponer su colección interna | Iteración por contrato |
| Comportamiento | Mediator | Un centro de control coordina sensor, alarma y luz | Comunicación mediada sin enlaces todos-con-todos |
| Comportamiento | Memento | Guardar y restaurar un ajuste válido de linterna | Instantánea controlada sin romper R-06/R-12 |
| Comportamiento | Observer | Un interruptor notifica cambios a luces y alarmas suscritas | Notificación uno-a-muchos, R-07 |
| Comportamiento | State | Una puerta responde según ABIERTA, CERRADA o BLOQUEADA | Delegación de comportamiento por estado, R-05 |
| Comportamiento | Strategy | Un monstruo alterna entre patrullar, perseguir y esperar | Algoritmos intercambiables, R-08 |
| Comportamiento | Template Method | Explorar «oficina» o «piscinas» con fases comunes | Secuencia fija con pasos variables |
| Comportamiento | Visitor | Un inspector produce descripciones de puerta, luz y artefacto | Operación externa sobre tipos heterogéneos |

**Diferencia con el catálogo actual:** hoy existen 5 creacionales, 7 estructurales y 10 de comportamiento; falta `Interpreter` en la colección técnica original. La colección nueva contempla **23**, y el patrón ausente podrá incorporarse a la colección técnica en una tarea independiente.

## 4. Contrato por ejemplo (plantilla de ficha)

Cada patrón tendrá una ficha breve en `docs/patrones/<categoria>/<patron>.md`, que responderá:

1. **Imagen de escena:** una ilustración ambiental reutilizable, opcional hasta la fase visual.
2. **Qué pasa en el mundo:** 2–5 frases con nombres sencillos y sin exigir lore.
3. **Reglas aplicables:** IDs del `DOMINIO.md` y, si procede, `R-LOCAL-...`.
4. **Qué problema presenta el código sin patrón:** no un ejemplo artificial construido para «justificarlo».
5. **Qué solución aporta el patrón y cuál es su clase cliente:** nombres y colaboraciones.
6. **UML mínimo:** clases, interfaces, realización y asociaciones importantes; multiplicidad si está justificada.
7. **Qué sucede en Java:** quién crea, recibe, utiliza o conserva objetos; no confundir referencias con copias.
8. **Salida de consola esperada:** orden visible de las acciones.
9. **Pruebas:** caso positivo, negativo y, cuando aplique, protección de invariantes.
10. **Comparación:** cuándo no usaríamos ese patrón.

No rellenar por obligación «agregación» o «composición» en todas las fichas. Se dibujarán solo si el dominio establece una relación todo-parte que las justifique.

## 5. Convenciones de código Java 21

- **Paquetes** por categoría y patrón, con minúsculas sin tildes: `liminal.comportamiento.strategy`.
- **Clases de dominio** con español claro: `Puerta`, `Habitacion`, `MonstruoOscuro`, `Linterna`.
- **Patrones** en inglés en documentación y paquetes para poder buscarlos fácilmente.
- **Una clase cliente visible por patrón** que invoque la colaboración; ejemplos independientes.
- Ejemplos simples, funciones pequeñas, sin framework, sin JavaFX y sin acceso a Internet.
- Comentarios mínimos: explicar decisiones o restricciones no obvias; evitar narrar cada instrucción.
- JavaDoc donde haga falta documentar contratos, no una página por método.
- No depender de RNG no determinista, reloj real ni servicios externos; salida reproducible.
- No confundir `extends` / `implements` con asociación y no inferir composición desde `new` o `List`.

**Convención recomendada de ejecución futura:**

```bash
# Desde la raíz de la nueva colección, al existir el código:
javac --release 21 -encoding UTF-8 -d out $(find src/main/java -name '*.java')
java -cp out liminal.DemoLiminal
```

Este comando es una **referencia de diseño para Git Bash/Linux**; no es ejecutable todavía porque los archivos Java de la colección aún no existen. Se añadirá script Windows/PowerShell cuando exista el primer ejemplo.

## 6. Estrategia de UML

- UML **principal**: diagrama de clases específico del patrón, legible sin zoom excesivo.
- UML **de apoyo** cuando sea esencial: pequeño diagrama de objetos que muestre instancias compartidas o secuencia de estados.
- **Realización**: línea discontinua hacia interfaz.
- **Asociación**: vínculo estructural; se justifica navegabilidad y multiplicidad cuando aporte.
- **Dependencia**: uso de tipos/operaciones; no requiere `new`.
- **Agregación compartida**: evitar como adorno; semántica todo-parte declarada expresamente.
- **Composición**: propiedad exclusiva y ciclo de vida justificados (R-09 es ejemplo).
- **UML inverso automático**: solo sugiere relaciones desde el código; la semántica depende de `DOMINIO.md`.
- En todas las imágenes usar los mismos nombres Java y relaciones respaldadas por la implementación.

## 7. Diseño visual y licencias (fase posterior)

### Dirección artística

Estética liminal y analógica, inquietud tranquila, fotografía/videojuego de exploración: pasillos amarillos, alfombra envejecida, luz fluorescente, piscinas vacías, oficinas silenciosas, arquitectura repetitiva, sensación VHS, Dreamcore/Weirdcore.

**Evitar:** personajes identificables de otras obras, nombres crípticos, capturas reproducidas de videojuegos, logotipos ajenos y uso de imágenes de procedencia incierta.

### Inventario previsto, no generado todavía

1. **Portada del mundo**: pasillo amarillo casi vacío.
2. **Mapa de ambiente**: ejemplo de habitaciones y conexiones.
3. **Piscinas liminales**: estética diferente, mismas reglas.
4. **Objetos**: linterna, llave, radio, cámara, interruptor y puerta.
5. **Entidades**: explorador genérico y monstruo oscuro sin diseño registrado de una obra ajena.
6. **Lámina de estados**: una puerta abierta, cerrada y bloqueada.

Preferir **imágenes originales generadas** después de fijar escenas y actores. Las fotos en línea solo pueden usarse cuando se verifican condiciones de licencia, atribución y permiso; registrar fuente y autor. Para mantener el repositorio liviano, exportar imágenes web optimizadas y usar SVG para UML. Las ilustraciones no son autoridad de reglas.

## Catálogo entregado (23/23 implementaciones)

- `src/main/java/liminal/comportamiento/state/ClienteState.java`: puerta con objetos de estado y rechazo de transiciones inválidas (R-05, R-12).
- `src/main/java/liminal/comportamiento/strategy/ClienteStrategy.java`: monstruo oscuro con estrategias intercambiables y validación de salidas del mapa (R-02, R-08).
- `src/main/java/liminal/comportamiento/observer/ClienteObserver.java`: interruptor, luz y alarma mediante suscripciones (R-07).
- `src/main/java/liminal/DemoLiminal.java`: entrada por consola para las tres escenas.
- `src/test/java/`: pruebas de transiciones, referencias y casos inválidos.
- `docs/uml/`: tres diagramas SVG; `docs/patrones/comportamiento/`: tres fichas explicativas.
- `scripts/verificar.sh` y `scripts/verificar.ps1`: compilación Java 21 y ejecución reproducible.

**Decisión pedagógica:** cada patrón agrupa sus clases auxiliares de paquete en un único archivo Java junto a su clase cliente pública. Java permite varias clases no públicas por archivo; esa concentración facilita leer el patrón como una pequeña historia. Si la complejidad crece, separar archivos sin alterar el contrato.

**Limitación declarada:** el catálogo no modela multijugador, concurrencia, físicas ni un motor gráfico. El mapa de Strategy maneja identificadores de habitaciones y conexiones dirigidas. En Observer, la notificación síncrona no se presenta como una transacción tolerante a excepciones arbitrarias de receptores.

### Entregas posteriores al piloto

- **5 creacionales:** Singleton, Factory Method, Abstract Factory, Builder y Prototype.
- **7 estructurales:** Adapter, Bridge, Composite, Decorator, Facade, Flyweight y Proxy.
- **8 de comportamiento adicionales:** Chain of Responsibility, Command, Interpreter, Iterator, Mediator, Memento, Template Method y Visitor.
- Los 23 patrones tienen una ficha en `docs/patrones/<categoria>/`, un diagrama SVG en `docs/uml/`, una clase cliente en `src/main/java/` y un método `probar()` o clase de pruebas dedicada.
- `DemoLiminal todos` permite recorrer todos. `PruebasLiminal` verifica los 23 y el script de Bash está integrado a la verificación del repositorio completo.

**Arte entregado:** tres imágenes generadas por IA y optimizadas en `assets/ambientes/`, un [atlas ilustrado](GUIA_VISUAL.md) y fichas con fondos pertinentes. Los UML ya son imágenes técnicas de aprendizaje y siguen separados de la ambientación. **Pendiente opcional:** láminas de puertas, linternas, interruptores y monstruos.

## 8. Secuencia de desarrollo y compuertas de calidad

| Etapa | Entrega | Condición para avanzar |
| --- | --- | --- |
| A — Contrato (hecha) | `DOMINIO.md`, glosario, invariantes, tabla de transiciones | Sin contradicciones internas; casos válidos e inválidos |
| B — Diseño (hecha) | fichas de los 23 patrones, clases cliente, UML preliminar | Cada patrón resuelve una necesidad sin cambiar reglas |
| C — Piloto (hecha) | State, Strategy y Observer funcionales con pruebas | Java 21 compila, consola clara, invariantes verificadas |
| D — Catálogo (hecha) | restantes 20 patrones en lotes controlados | Cada ejemplo independiente y probado |
| E — Integración (hecha) | menú de demos y scripts de verificación | No altera scripts de la colección técnica existente |
| F — Arte (parcial) | 3 fondos originales, atlas, pies y procedencia | Pendientes láminas de objetos y criaturas |
| G — Auditoría | código, documentación, enlaces, UML, tildes y accesibilidad | Todas las demos verificadas y mapa de trazabilidad completo |

**Orden implementado:** primero `State`, `Strategy` y `Observer`; después los cinco creacionales, siete estructurales y ocho patrones de comportamiento restantes. La secuencia es pedagógica, no una jerarquía de patrones.

## 9. Matriz de pruebas mínima

- **Estado válido:** prueba la acción esperada y la identidad de los objetos.
- **Estado inválido:** comprueba que se rechaza sin mutación parcial.
- **Referencia compartida:** cuando corresponda, demuestra `a == b` sin duplicar entidades.
- **Pertenencia única:** cuando corresponda, una entidad no puede ser propietaria de dos compuestos ni estar físicamente en dos inventarios.
- **Creación de instancias:** Factory Method, Abstract Factory, Builder y Prototype verifican qué se crea y cómo.
- **Polimorfismo:** Strategy, State, Visitor y otros demuestran variantes sin `switch` artificial en la clase cliente.
- **Regresión:** comprobar que la colección técnica, Arena Java y MiniSO continúan funcionando.
- **Documentación:** enlaces a imágenes/UML existentes, salidas reproducibles, nombres concordantes.

### Definición de terminado por patrón

Un patrón se marca **Terminado** cuando están su ficha, reglas citadas, implementación compilable, clase cliente, salida reproducible, UML fiel a clases reales, prueba de comportamiento y prueba negativa si aplica. La imagen final es un hito de la fase F; puede añadirse después sin bloquear el avance de la lógica.

## 10. Decisiones deliberadamente aplazadas

- Detalles visuales finales (composición, encuadre, cromatismo, resolución).
- Herramienta exacta de renderizado de UML (siempre resultado final legible, preferiblemente SVG).
- Si se comparte una pequeñísima biblioteca de tipos entre los 23 patrones; decidir tras el piloto, evitando acoplamiento.
- Si el `Interpreter` también se agrega a la colección técnica: sí es deseable, pero hacerlo sin reorganizar paquetes existentes.

**Regla para cualquier cambio:** revisar primero si afecta `DOMINIO.md`. Si afecta, editar y versionar su regla, actualizar pruebas y UML; **no corregir solo el código**.
