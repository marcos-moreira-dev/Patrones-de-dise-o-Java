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
| Media | UML de Abstract Factory no dejaba claros los tipos de producto concreto | Se distinguen los cuatro productos concretos (`LuzAmarilla`, `LuzPiscinas`, `PuertaAmarilla`, `PuertaPiscinas`), los contratos y las dos fábricas | UML |
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
| **Revisión visual de SVG** | Se revisó la estructura y la distribución textual de los 23 UML mediante análisis de cajas/etiquetas. Ya se había corregido una superposición real en Observer y reorganizado Abstract Factory. La inspección visual píxel a píxel de cada visor continúa siendo opcional | Parcial |
| **Ilustraciones de objetos y criatura** | **Completado como lámina única:** puerta beige, linterna, interruptor y monstruo oscuro en [galería visual](assets/README.md). Láminas individuales o transiciones animadas serían una mejora adicional | Resuelto para esta etapa |
| **Laboratorio independiente de composición UML** | **Completado** en [Relaciones UML](docs/laboratorios/relaciones-uml.md): composición R-09, agregación local, asociaciones, referencias y baja lógica, con dos diagramas y pruebas | Resuelto |
| **Completar `Interpreter` en la colección técnica original** | **Completado:** [Interpreter técnico](../../comportamiento/Interpreter/README.md) con expresiones anidadas, `BigDecimal` y pruebas; las dos colecciones ahora tienen 23 patrones | Resuelto |
| **Abstract Factory con clases de producto concretas diferentes** | **Completado:** las dos fábricas producen cuatro clases concretas de luz/puerta, con pruebas de tipos, rechazo de combinaciones y UML actualizado | Resuelto |
| **Notificaciones con fallo de observador** | El Observer de consola es sincrónico y no ofrece transaccionalidad ante excepciones arbitrarias de callbacks. Ya se declara su alcance en la ficha | Baja; sería un diseño más avanzado |

Los pendientes **no invalidan** el catálogo para estudiar los patrones por consola. Ninguna relación de composición o agregación UML debe introducirse para llenar una cuota de símbolos.

## 6. Actualización: laboratorio UML completado

Se implementó [un laboratorio adicional de relaciones UML](docs/laboratorios/relaciones-uml.md), separado de los 23 GoF. Prueba que `RegistroExpedicion.cerrar()` invalida sus partes **en el modelo**, aunque un informe conserve una referencia Java. También diferencia agregación compartida de asociación y dependencia, con pruebas locales y CI en Linux/Windows.

## 7. Actualización: visuales y familias concretas (14 de noviembre de 2026)

- Se revisaron estructuralmente los **23 SVG GoF**; el renderizado de [Observer](docs/uml/observer.svg) reveló desbordamiento del atributo de observadores sobre su asociación y multiplicidad. Se dibujó un esquema más espacioso.
- [Abstract Factory](docs/uml/abstractfactory.svg) pasó de dos tipos de producto reutilizados con configuraciones distintas a **cuatro clases concretas**: `LuzAmarilla`, `PuertaAmarilla`, `LuzPiscinas` y `PuertaPiscinas`. Se actualizaron sus pruebas y diagrama; el cliente `Escena` sigue trabajando con interfaces.
- Se incorporó una lámina ilustrada de **puerta, linterna, interruptor y criatura oscura** en [la galería](assets/README.md) y se relacionó con R-05 a R-08 en el atlas. Una imagen no constituye evidencia de una regla.
- Los chequeos automáticos validan estructura SVG, enlaces y archivos WebP, pero **no** garantizan por sí solos la legibilidad de todas las posiciones de texto y flechas en diferentes visores.

## 8. Actualización: secuencias de comportamiento y verificación

Se añadieron tres SVG técnicos adicionales, **separados de los 23 UML de clases**:

- [State: transiciones de puerta](docs/secuencias/transiciones-state.svg), conforme a R-05.
- [Strategy: cuatro pasos de M-01](docs/secuencias/recorrido-strategy.svg), conforme a R-08 y a las salidas dirigidas.
- [Observer: dos pulsaciones](docs/secuencias/notificaciones-observer.svg), conforme a R-07 y al contador de alarma.

Cada secuencia se enlaza desde la ficha del patrón y desde el [atlas visual](GUIA_VISUAL.md), y el verificador Java comprueba que el SVG sea XML válido, contenga referentes semánticos y tenga enlaces existentes. Las pruebas de los **23 patrones** son independientes del número de ilustraciones. La comprobación de desbordamiento de etiquetas en los 23 diagramas de clases fue una estimación geométrica, no una validación visual completa.

## 9. Recomendación de cierre

Considerar la colección **funcional y apta para estudio**. Antes de anunciar un cierre estrictamente editorial, inspeccionar visualmente los otros SVG si se busca perfección editorial. La galería de objetos y el laboratorio aparte de R-09 ya están incorporados. Conservar las **reglas del dominio como fuente normativa**: si cambian, actualizar documentación, Java, UML y pruebas en ese orden.

[Volver al README](README.md) · [Ver atlas visual](GUIA_VISUAL.md) · [Consultar dominio](DOMINIO.md).


## 10. Auditoría de navegabilidad del código (14 de noviembre de 2026)

A partir de una revisión de las carpetas del repositorio en GitHub, se detectó una **dificultad de lectura**: cada `ClienteX.java` contenía el cliente y varias clases, interfaces o records auxiliares. Aunque Java permite estas declaraciones, GitHub mostraba solo el nombre del cliente en la carpeta; eso ocultaba visualmente la estructura de cada patrón, a diferencia de `Response.java` y `ResponseBuilder.java` en el ejemplo técnico.

**Correcciones completadas:**

- Los **23 patrones liminales** están reorganizados con **un tipo superior Java por archivo**; cada nombre aparece ahora en la carpeta correspondiente. No se fusionaron dominios distintos ni se añadieron frameworks.
- Las pruebas se separaron en `PruebaNombre.java`, bajo `src/test/java`, para todos los patrones (State, Strategy y Observer ya las tenían; Builder fue el primer cambio). Los clientes dejaron de mezclar demostraciones y aserciones.
- **Builder** se redujo a los dos roles principales: `ConstructorHabitacion` y `Habitacion`. Su cliente tiene pasos `construirHabitacion()` y `mostrarHabitacion()`. Se retiró el registro de habitaciones de este ejemplo porque la unicidad de un mapa **no es una responsabilidad de Builder**.
- Los clientes de Factory Method, State, Strategy, Observer y Facade muestran ahora el recorrido mediante métodos con nombre; los ejemplos cortos permanecen cortos, sin trocear artificialmente cada instrucción.
- `DemoLiminal.java` ofrece `ejecutarCreacionales()`, `ejecutarEstructurales()`, `ejecutarComportamiento()` y `probarX()` por patrón, como los orquestadores de la colección técnica.
- Se retiraron imports sin uso y espacios redundantes de clases de producción y pruebas, y se corrigieron etiquetas obsoletas en UML de Singleton y Prototype.
- El verificador de documentación comprueba la existencia de las carpetas, el **archivo por tipo superior**, los enlaces de clientes, la clase de prueba independiente y la ausencia de `probar()` dentro del cliente.

**Resultado para el lector:** seguir la [guía de lectura manual](GUIA_LECTURA_CODIGO.md), abrir el cliente, ir a cada tipo mencionado por nombre y consultar la prueba solo al final. No hace falta comprender un juego integrado para estudiar Builder o cualquier otro patrón.

**Alcance y limitación:** no se cambiaron las reglas R-01…R-14. Se mantuvieron las colaboraciones GoF existentes; una mejora de navegación no sustituye una auditoría profunda del diseño de cada patrón ni el examen visual de cada flecha UML.
