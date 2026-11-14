# Auditoría pedagógica y semántica — Expedición Liminal

**Fecha:** 14 de noviembre de 2026.  
**Alcance:** los 23 patrones GoF de `colecciones/liminal/`, sus clases cliente, las reglas del dominio, los diagramas UML, las fichas Markdown y la verificación Java 21.

## 1. Criterios utilizados

1. **Dominio antes que patrón:** ningún ejemplo debe contradecir las reglas `R-01` a `R-14` de [DOMINIO.md](DOMINIO.md) para conseguir una demostración vistosa.
2. **Cliente visible:** el ejemplo debe mostrar qué instancia se crea, qué otra se recibe o referencia y quién invoca el comportamiento.
3. **Patrón auténtico:** la colaboración corresponde al patrón GoF y no solamente a clases renombradas con una temática de Backrooms.
4. **UML fiel:** las relaciones dibujadas deben reflejar interfaces, herencia, asociación o dependencia reales; `List<T>` y `new` no justifican por sí solos agregación o composición.
5. **Pruebas:** demostrar al menos un comportamiento relevante y el rechazo de situaciones inválidas cuando corresponda.
6. **Baja fricción:** vocabulario sencillo, código Java 21 por consola, explicaciones largas en la ficha y no en comentarios repetitivos.
7. **Material visual:** las ilustraciones son originales y accesibles desde Markdown; **no** sustituyen las reglas ni el diagrama UML.

## 2. Hallazgos corregidos en esta auditoría

| Prioridad | Hallazgo | Corrección realizada | Reglas |
| --- | --- | --- | --- |
| Alta | Chain of Responsibility exigía una puerta abierta para **todas** las salidas | La solicitud distingue salidas, salidas con puerta y puertas abiertas; las salidas sin puerta son válidas. Se añadieron pruebas positivas y negativas | R-02, **R-04**, R-12 |
| Alta | Facade confundía «no existe salida» con «salida sin puerta» | La salida existe independientemente de su puerta opcional; cruzar sin puerta se permite. Se prueba el rechazo sin mutación | **R-04**, R-12 |
| Media | Ciertos objetos aceptaban identificadores vacíos | Se endurecieron validaciones y pruebas en Command, Mediator, Visitor, Composite, Factory Method y la puerta de Facade | **R-01** |
| Media | UML de Composite omitía la colección hacia la interfaz y dibujaba relación directa con la hoja | Se representa `GrupoLuces → Encendible` con `hijos: 0..*`, sin inventar composición UML | UML |
| Media | UML de Decorator no mostraba la asociación de envoltura | Se agregó `envuelta: 1` desde `DecoradorLinterna` hacia `Linterna` | UML |
| Media | UML de Chain no representaba al siguiente validador | Se agregó la autoasociación `siguiente: 0..1` de `ValidadorCruce` | UML |
| Media | UML de Iterator no mostraba `Iterable`, que `RegistroHabitaciones` implementa | Se dibujaron separadamente `Iterable<Habitacion>` e `Iterator<Habitacion>` y sus realizaciones | UML |
| Media | UML de Abstract Factory no dejaba claros los tipos de producto concreto | Se representan `LuzAmbiental`, `PuertaAmbiental`, sus contratos y las dos fábricas | UML |
| Baja | UML de Facade mencionaba `puerta(...)` tras la corrección del modelo | Se actualizó a `salida(origen, destino)` y se corrigió la ficha narrativa | UML, R-04 |

**Lección principal:** compilar correctamente no basta para validar la semántica. Las restricciones del dominio deben traducirse en pruebas, y los diagramas deben representar las colaboraciones concretas.

## 3. Verificación automática

La colección incluye `src/test/java/liminal/PruebasLiminal.java` y `VerificarDocumentacion.java`.

Se comprueba:

- Compilación con **JDK 21** y ejecución de las demostraciones sin JavaFX ni framework.
- **23 patrones**, incluidos comportamientos y casos de rechazo.
- **23 fichas** con enlace a clase Java y **23 diagramas SVG** bien formados.
- **3 archivos WebP** de ambientación y sus rutas de inserción.
- Comprobaciones de regresión en seis UML importantes (`Composite`, `Decorator`, `Chain of Responsibility`, `Iterator`, `Abstract Factory` y `Facade`).
- Integración al script general del repositorio y a GitHub Actions en Linux y Windows.

> **Límite de estas pruebas:** detectar que el XML es válido y que aparecen ciertos símbolos **no equivale** a comprobar automáticamente la calidad de la disposición gráfica, ni demuestra todas las propiedades posibles del dominio.

Desde la raíz del repositorio, la verificación se ejecuta con:

```bash
bash colecciones/liminal/scripts/verificar.sh
```

La auditoría no requiere que el lector ejecute nada para aceptar los cambios revisados en GitHub; las ejecuciones automáticas del repositorio proporcionan la evidencia técnica.

## 4. Revisión de intención por categoría

| Categoría | Patrón y clave pedagógica |
| --- | --- |
| Creacionales (5) | Singleton: panel compartido; Factory Method: decisión del creador; Abstract Factory: familias de productos; Builder: pasos de construcción; Prototype: configuración copiada e identidad nueva |
| Estructurales (7) | Adapter: traducción de contrato; Bridge: canal separado de alerta; Composite: hoja y grupo bajo una interfaz; Decorator: envoltura; Facade: coordinación de cruce; Flyweight: textura inmutable compartida; Proxy: acceso controlado |
| Comportamiento (11) | Chain: validadores encadenados; Command: órdenes como objetos; Interpreter: gramática mínima; Iterator: recorrido sin exponer lista; Mediator: coordinación central; Memento: restauración; Observer: notificaciones; State: transiciones; Strategy: algoritmos intercambiables; Template Method: orden fijo; Visitor: doble despacho |

Los ejemplos pertenecen a **un solo mundo de referencia**, pero tienen **paquetes y clientes autónomos** para evitar que el lector necesite entender un videojuego completo.

## 5. Pendientes no bloqueantes y decisiones de alcance

| Pendiente | Motivo | Prioridad sugerida |
| --- | --- | --- |
| **Revisión visual manual de SVG** tras las correcciones | La compilación y el parser XML no detectan superposición de flechas o texto | Media |
| **Ilustraciones de objetos y criatura** | Existen tres ambientes originales; faltarían láminas específicas de linterna, puerta, interruptor y monstruo, si queremos un catálogo más ilustrado | Baja |
| **Laboratorio independiente de composición UML** | R-09 define `RegistroExpedicion` / `EntradaRegistro`, pero no hay un ejercicio exclusivo que muestre su propiedad y ciclo de vida. No se fuerza un rombo negro dentro de patrones que no lo necesitan | Media opcional |
| **Completar `Interpreter` en la colección técnica original** | Esa colección sigue teniendo 22 patrones; la liminal sí tiene los 23 | Media, trabajo separado |
| **Abstract Factory con clases de producto concretas diferentes** | Las dos fábricas actuales comparten los tipos `LuzAmbiental` y `PuertaAmbiental` y varían `Tema`; es una aplicación funcional y sencilla, aunque una segunda variante podría enseñar también polimorfismo entre productos | Baja opcional |
| **Notificaciones con fallo de observador** | El Observer de consola es sincrónico y no ofrece transaccionalidad ante excepciones arbitrarias de callbacks. Ya se declara su alcance en la ficha | Baja; sería un diseño más avanzado |

Los pendientes **no invalidan** el catálogo para estudiar los patrones por consola. Ninguna relación de composición o agregación UML debe introducirse para llenar una cuota de símbolos.

## 6. Recomendación de cierre

Considerar la colección **funcional y apta para estudio**. Antes de anunciar un cierre estrictamente editorial, inspeccionar visualmente los SVG corregidos; después, si se busca más atractivo, ampliar imágenes y añadir una breve demostración aparte de R-09. Conservar las **reglas del dominio como fuente normativa**: si cambian, actualizar documentación, Java, UML y pruebas en ese orden.

[Volver al README](README.md) · [Ver atlas visual](GUIA_VISUAL.md) · [Consultar dominio](DOMINIO.md).
