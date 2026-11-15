# Cómo leer a mano el código de Expedición Liminal

**Objetivo:** abrir una carpeta en GitHub y reconocer inmediatamente qué archivo es el cliente, cuál es el contrato del patrón, cuáles son sus implementaciones y dónde se prueban sus reglas. No necesitas aprender un framework ni recorrer un videojuego completo.

## 1. Comparación directa con la colección técnica

En el Builder técnico original aparecen [`Response.java`](../../creacionales/Builder/Response.java) y [`ResponseBuilder.java`](../../creacionales/Builder/ResponseBuilder.java). Son los dos roles esenciales: producto y constructor por pasos.

En el Builder liminal tienen sus equivalentes:

| Ruta de lectura | Archivo | Papel |
| --- | --- | --- |
| 1 | [`ClienteBuilder.java`](src/main/java/liminal/creacionales/builder/ClienteBuilder.java) | **Cliente:** muestra qué sucede y en qué orden |
| 2 | [`ConstructorHabitacion.java`](src/main/java/liminal/creacionales/builder/ConstructorHabitacion.java) | **Builder:** reúne los argumentos y crea el producto |
| 3 | [`Habitacion.java`](src/main/java/liminal/creacionales/builder/Habitacion.java) | **Producto:** representa la habitación ya construida |
| 4 | [`PruebaBuilder.java`](src/test/java/liminal/creacionales/builder/PruebaBuilder.java) | **Pruebas:** verifica casos válidos e inválidos, fuera del cliente |

[Ver las tres clases de producción juntas en la carpeta Builder](src/main/java/liminal/creacionales/builder/).

### Recorrido del cliente

Abre `ClienteBuilder.java`, localiza `main` y sigue las llamadas en este orden:

```java
public static void ejecutar(PrintStream out) {
    Habitacion habitacion = construirHabitacion();
    mostrarHabitacion(habitacion, out);
}
```

Primero `construirHabitacion()` configura `ConstructorHabitacion`; su `construir()` devuelve una `Habitacion` validada. Después `mostrarHabitacion()` imprime sus datos. No hay un servicio de backend, controlador HTTP, base de datos, registro de mapas ni mecánicas adicionales que oculten Builder.

**Importante:** Builder no exige que exista una clase denominada exactamente `Builder`, ni una clase `Director`. El nombre de los roles y su responsabilidad son lo importante. Tampoco sería correcto dibujar una composición UML solo porque un método llame a `new`.

## 2. Estructura común de los 23 patrones

Cada patrón tiene su propia carpeta y **un tipo superior Java por archivo**:

```text
colecciones/liminal/
├── src/main/java/liminal/
│   ├── DemoLiminal.java               ← lanzador con métodos por categoría
│   ├── creacionales/
│   │   └── builder/
│   │       ├── ClienteBuilder.java    ← ejecución visible
│   │       ├── ConstructorHabitacion.java
│   │       └── Habitacion.java
│   ├── estructurales/
│   │   └── decorator/                 ← Cliente, interfaz y envolturas separados
│   └── comportamiento/
│       └── strategy/                  ← Cliente, estrategias y monstruo separados
└── src/test/java/liminal/
    └── creacionales/builder/
        └── PruebaBuilder.java         ← pruebas fuera del cliente
```

Los tipos auxiliares pueden conservar visibilidad de **paquete** porque cada ejemplo se ejecuta dentro de su propio paquete. Eso no los hace objetos «ocultos»: todos están disponibles como archivos independientes en GitHub. No se convierten automáticamente en clases públicas si no necesitan una API externa.

### Un cliente, un recorrido corto

1. **Lee la escena** en la [ficha Markdown del patrón](README.md#catálogo-completo-y-enlaces-de-estudio).
2. **Abre `ClienteNombre.java`** para entender instancias y orden de las invocaciones; en ejemplos más largos, el cliente delega a métodos con nombres descriptivos.
3. **Sigue las clases mencionadas**, ahora con un archivo por tipo: interfaz, producto, decorador, receptor, estado, etc.
4. **Compara con el UML** y comprueba si cada línea representa una referencia, implementación, dependencia o propiedad justificada.
5. **Abre `PruebaNombre.java` solo al final** para observar qué comportamiento y qué restricciones se verifican.

No todos los patrones necesitan la misma cantidad de clases. **Facade** requiere colaboradores; **Singleton** necesita mucho menos. Agregar clases solo por decoración sería perjudicial.

## 3. Lanzador que respeta el orden

[DemoLiminal.java](src/main/java/liminal/DemoLiminal.java) ahora sigue el estilo de los orquestadores `ProbarCreacionales`, `ProbarEstructurales` y `ProbarComportamiento` de la colección técnica:

```java
public static void ejecutarTodos() {
    ejecutarCreacionales();
    ejecutarEstructurales();
    ejecutarComportamiento();
}
```

Además, dispone de `probarBuilder()`, `probarState()`, `probarObserver()` y los demás métodos por patrón. Se puede ejecutar todo o elegir un solo tema.

Una vez compilado con `bash colecciones/liminal/scripts/verificar.sh`, desde la raíz del repositorio:

```bash
java -cp colecciones/liminal/out liminal.DemoLiminal builder
java -cp colecciones/liminal/out liminal.DemoLiminal creacionales
java -cp colecciones/liminal/out liminal.creacionales.builder.ClienteBuilder
```

## 4. Mismo dominio conceptual, ejemplos independientes

Los 23 ejemplos comparten las reglas de [DOMINIO.md](DOMINIO.md), **pero no son un videojuego monolítico**. Una `Habitacion` en Builder no tiene que ser la **misma clase Java** que una `Habitacion` en Iterator: están en paquetes distintos y demuestran responsabilidades diferentes. Lo que sí comparten es el vocabulario y las restricciones del mundo.

El [integrador liminal implementado](../../proyectos/expedicion-liminal-integrada/README.md) tiene **su propio núcleo de dominio compartido**, sin importar clases de estas 23 demos aisladas. **No hace falta conectar los ejemplos para aprender GoF**: la arquitectura integrada es otra ruta de estudio, con casos de uso y dependencias propios. El [plan histórico](PLAN_INTEGRADOR.md) conserva las decisiones del proceso.

Los comentarios de código deben ser breves: explicar una decisión no evidente, no reescribir la ficha del patrón dentro de cada método. Las explicaciones más extensas pertenecen a los Markdown.

[Volver al índice de patrones](README.md) · [Contrato del dominio](DOMINIO.md) · [Atlas visual](GUIA_VISUAL.md).

## 5. Cómo explorar las variantes sin confundirlas con los 23 patrones

Las siete implementaciones alternativas están en subcarpetas `variantes/` del **mismo patrón**. El recorrido principal no ha cambiado: ficha base → UML base → `ClientePatron` → colaboradores → pruebas. Solo después, si interesa profundizar, abre [el plan y enlaces de variantes](PLAN_VARIANTES.md), su cliente independiente y su prueba.

[Laboratorio de class frente a record](docs/laboratorios/java/class-vs-record.md) · [Auditoría del andamiaje](AUDITORIA_ANDAMIAJE.md). No interpretes scripts, tests, generadores SVG o ficheros Maven como participantes del patrón.

## 6. Leer decisiones de ingeniería, no solo reconocer clases

Después de leer el cliente, pregúntate: **(1)** ¿qué invariante corresponde al dominio y qué parte coordina el caso de uso? **(2)** ¿cuál es la dependencia importante y por qué su dirección tiene sentido? **(3)** ¿estamos compartiendo una referencia, copiando una colección o transfiriendo propiedad? **(4)** ¿la excepción expresa argumento inválido, transición de estado inválida u otro fallo? **(5)** ¿el patrón evita acoplamiento, duplicación, herencia combinatoria o una clase omnisciente, o sería más simple prescindir de él?

Consulta la [matriz de los 23 escenarios y decisiones](MATRIZ_ALINEACION.md): se incorporaron **17 comentarios breves** en puntos significativos; no se agregaron comentarios de relleno para cubrir cada clase. La **arquitectura modular del integrador** es una [ruta independiente ya implementada](../../proyectos/expedicion-liminal-integrada/docs/ARQUITECTURA.md), no la interpretación de estas demostraciones autónomas.
