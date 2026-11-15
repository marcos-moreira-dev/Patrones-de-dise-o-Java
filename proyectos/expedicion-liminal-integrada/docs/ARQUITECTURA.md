# Arquitectura implementada (etapas 1 y 2) — Expedición Liminal Integrada

> **Etapas 1 y 2 implementadas:** **monolito modular Java 21 con capas dirigidas al dominio**, Strategy en criaturas, cliente determinista y pruebas específicas. Es suficiente para enseñar estructuras y responsabilidades; no se impone una arquitectura hexagonal completa, MVC ni un framework sin un motivo real.

## 1. Dependencias de paquetes

~~~mermaid
flowchart TD
    P["presentacion<br/>ClienteExpedicion"]
    A["aplicacion<br/>ProcesarTurno + ResultadoTurno"]
    E["dominio/exploracion<br/>Expedicion + Explorador"]
    M["dominio/mundo<br/>Mapa + Habitacion + Salida + Posicion + Direccion"]
    C["dominio/criaturas<br/>MonstruoOscuro + 3 estrategias"]
    P --> A
    P -. "Composición inicial de entidades" .-> E
    P -. "Carga de escenario fijo" .-> M
    P -. "Configura las estrategias" .-> C
    A --> E
    A --> C
    E --> M
    E --> C
    C --> M
~~~

- **La presentación depende de aplicación y dominio** solo porque construye el grafo inicial de objetos (*composition root*); no debe conocer cómo validar internamente una salida.
- **La aplicación depende del dominio**, coordina casos de uso y convierte rechazos esperados en resultados entendibles.
- **El dominio no depende de presentación ni aplicación**, tampoco de `Scanner`, red, archivos o framework.
- **No hay dependencia entre el proyecto integrado y las 23 demos encapsuladas.** La relación entre ambas es **documental/normativa**.
- Todavía no hay `infraestructura/`; crear un puerto de almacenamiento sería una abstracción prematura cuando todo está en memoria.

## 2. UML de relaciones principales (código de etapas 1 y 2)

~~~mermaid
classDiagram
    direction LR
    class Posicion {
        <<record>>
        +int x
        +int y
    }
    class Direccion {
        <<enumeration>>
        NORTE
        SUR
        ESTE
        OESTE
    }
    class Habitacion {
        -String id
        -String nombre
        -Posicion posicion
    }
    class Salida {
        -String id
        -Habitacion origen
        -Habitacion destino
        -Direccion direccion
    }
    class MapaLiminal {
        -String id
        -Map~String,Habitacion~ habitacionesPorId
        -Map~Posicion,Habitacion~ habitacionesPorPosicion
        -Map~String,Salida~ salidasPorId
        +agregarHabitacion()
        +agregarSalida()
        +habitacion()
        +salidaDesde()
        +destinosDesde()
    }
    class Explorador {
        -String id
        -MapaLiminal mapa
        -Habitacion ubicacion
        ~mover(Direccion) Habitacion
    }
    class Expedicion {
        -String id
        -Explorador explorador
        -Habitacion meta
        -MapaLiminal mapa
        -MonstruoOscuro criatura
        -int turnos
        -boolean finalizada
        +incorporarCriatura(MonstruoOscuro)
        +avanzar(Direccion) Habitacion
    }
    class ProcesarTurno {
        +procesar(Direccion) ResultadoTurno
    }
    class ResultadoTurno {
        <<record>>
        +boolean aceptado
        +String habitacionId
        +int turnos
        +boolean finalizada
        +String mensaje
    }
    class MonstruoOscuro {
        -String id
        -MapaLiminal mapa
        -Habitacion ubicacion
        -EstrategiaMovimiento estrategia
        +cambiarEstrategia(EstrategiaMovimiento)
        +avanzar(Habitacion) Habitacion
    }
    class EstrategiaMovimiento {
        <<interface>>
        +elegirDestino(Habitacion, Habitacion, List~Habitacion~) Habitacion
    }
    class Patrullar
    class Perseguir
    class Esperar
    EstrategiaMovimiento <|.. Patrullar
    EstrategiaMovimiento <|.. Perseguir
    EstrategiaMovimiento <|.. Esperar
    MonstruoOscuro --> EstrategiaMovimiento : comportamiento activo
    MonstruoOscuro --> MapaLiminal : valida rutas
    MonstruoOscuro --> "1" Habitacion : ubicación actual
    Expedicion --> "0..1" MonstruoOscuro : registra
    ProcesarTurno ..> MonstruoOscuro : solicita acción posterior

    MapaLiminal --> "0..*" Habitacion : registra
    MapaLiminal --> "0..*" Salida : registra
    Habitacion --> "1" Posicion : coordenada
    Salida --> Direccion : dirección
    Salida --> "1" Habitacion : origen
    Salida --> "1" Habitacion : destino
    Explorador --> "1" MapaLiminal : conoce
    Explorador --> "1" Habitacion : ubicación actual
    Expedicion --> "1" Explorador : participante
    Expedicion --> "1" Habitacion : meta
    ProcesarTurno ..> Expedicion : ejecuta caso de uso
    ProcesarTurno ..> ResultadoTurno : devuelve valor
~~~

**Precaución UML:** la flecha indica navegación/uso de referencias según el modelo, **no composición**. El proyecto no necesita rombos negros para resultar profesional. `ResultadoTurno` y `Posicion` son datos/valores y pueden ser records; `Explorador`, `MapaLiminal`, `Habitacion`, `Expedicion` y `MonstruoOscuro` son entidades. La asociación de la sesión hacia la criatura **no implica propiedad UML ni borrado automático de su ciclo de vida**.

## 3. Paquetes concretos de las dos etapas

~~~text
src/main/java/liminal/integrado/
├── dominio/
│   ├── mundo/
│   │   ├── Posicion.java               // valor
│   │   ├── Direccion.java              // enum
│   │   ├── Habitacion.java             // entidad
│   │   ├── Salida.java                 // conexión dirigida
│   │   └── MapaLiminal.java            // valida índices y topología
│   ├── exploracion/
│   │   ├── Explorador.java             // ubicación e identidad
│   │   └── Expedicion.java             // meta, turnos y criatura
│   └── criaturas/
│       ├── MonstruoOscuro.java         // entidad y validación de propuestas
│       ├── EstrategiaMovimiento.java   // contrato Strategy
│       ├── Patrullar.java              // primera ruta disponible
│       ├── Perseguir.java              // objetivo vecino
│       └── Esperar.java                // permanecer inmóvil
├── aplicacion/
│   ├── ProcesarTurno.java              // orquestación/transformación del resultado
│   └── ResultadoTurno.java             // dato de salida
└── presentacion/
    └── ClienteExpedicion.java          // escena narrativa determinista

src/test/java/liminal/integrado/
├── PruebasExpedicion.java              // núcleo y regresiones
└── PruebasCriaturas.java               // Strategy y coordinación de turnos
~~~

**Implementación actual:** estos archivos existen. `Explorador.mover()` tiene **acceso de paquete**: la aplicación y el cliente no pueden invocarlo directamente para evitar saltarse los turnos de `Expedicion`. Los índices por ID y por coordenadas son de `MapaLiminal`, no copias de las habitaciones.

## 4. Flujo de responsabilidades de un turno con criatura

~~~mermaid
sequenceDiagram
    participant Cliente as ClienteExpedicion
    participant Caso as ProcesarTurno
    participant Sesion as Expedicion
    participant Exp as Explorador
    participant Mapa as MapaLiminal
    participant Monstruo as MonstruoOscuro
    participant Estrategia as EstrategiaMovimiento
    Cliente->>Caso: procesar(ESTE)
    Caso->>Sesion: avanzar(ESTE)
    Sesion->>Exp: mover(ESTE)
    Exp->>Mapa: salidaDesde(ubicacion, ESTE)
    Mapa-->>Exp: Salida con destino válido
    Exp-->>Sesion: nueva Habitacion
    Sesion->>Sesion: turnos++, evaluar meta
    Sesion-->>Caso: estado confirmado
    Caso->>Sesion: criatura()
    Sesion-->>Caso: M-01 (si hay criatura)
    Caso->>Monstruo: avanzar(nuevaHabitacion)
    Monstruo->>Mapa: destinosDesde(ubicacion)
    Mapa-->>Monstruo: destinos válidos e inmodificables
    Monstruo->>Estrategia: elegirDestino(actual, objetivo, destinos)
    Estrategia-->>Monstruo: destino propuesto
    Monstruo->>Monstruo: validar antes de mutar
    Monstruo-->>Caso: ubicación del monstruo
    Caso-->>Cliente: ResultadoTurno aceptado + indicio de criatura
~~~

Si no hay salida para el explorador, el dominio rechaza **antes** de cambiar ubicación: no se incrementa el turno ni se consulta la estrategia. Si el explorador avanza correctamente, la aplicación solicita una acción posterior de criatura, incluso cuando se alcanza la meta. Si la estrategia propone un destino imposible, la criatura no se mueve y **solo su acción** se rechaza: el paso anterior del explorador sigue aceptado. El flujo es síncrono y **no afirma atomicidad global de ambas acciones**.

## 5. Decisiones arquitectónicas y sus alternativas

| Decisión | Justificación | Qué sería un mal olor aquí |
| --- | --- | --- |
| Paquetes por capacidad del dominio | Permiten ubicar reglas y colaborar sin organizar todo por nombres de patrones | 23 paquetes artificiales `strategy`, `observer`, etc. |
| `Posicion` como `record` | Igualdad de valores y coordenadas inmutables | Convertir entidades con ID y comportamiento en records por ahorrar código |
| `MapaLiminal` valida salidas | La integridad del grafo pertenece al mapa, no al cliente | Vista o cliente manipulando directamente colecciones internas |
| `Explorador` controla ubicación | La entidad no deja su posición modificable desde cualquier parte | Setter público que salta el mapa |
| `Expedicion` conserva turno/meta y referencia a criatura | La sesión valida una criatura del mismo mapa sin convertirse en un motor gráfico | Un único `MotorJuego` con mapa, terminal y estrategias |
| `ProcesarTurno` coordina dos acciones independientes | Conserva los rechazos locales y no inventa una transacción de rollback | Capturar todo en un `catch` que marque un paso ya ejecutado como fallido |
| `ClienteExpedicion` crea el escenario | Deja explícitas las dependencias para aprender | DI container o factoría general de todo el universo innecesarios |
| `EstrategiaMovimiento` + tres políticas | Strategy/OCP permite cambiar el algoritmo sin cambiar la identidad de la criatura; `MonstruoOscuro` protege su invariante | Un `switch` creciente en la entidad, movimiento calculado en la consola o teletransporte silencioso |
| Introducir patrones **después** del problema | OCP/SRP/DIP donde realmente aportan, no como ritual | Una interfaz por objeto o Singleton global sin necesidad |

## 6. Estrategia de pruebas e integración CI

**Primero el dominio**, luego el caso de uso y por último el relato de consola:

1. Mapa: límites, IDs/celdas únicas, salidas existentes y dirigidas, rechazo de diagonal y duplicados.
2. Explorador: identidad estable, ubicación inicial y movimiento válido; rechazo no mueve.
3. Expedición: cuatro éxitos, una solicitud inválida con cero costo, finalización, rechazo posterior.
4. Caso de uso: estados/resultados útiles sin depender de captura de consola.
5. Reproducibilidad: mismo escenario produce los mismos resultados.
6. Compilación Java 21, pruebas en Linux y Windows **sin usar el computador del estudiante**.

Se creó `scripts/verificar.sh`: compila solo este proyecto con Java 21, ejecuta `PruebasExpedicion` **y `PruebasCriaturas`** y verifica el resultado de consola. El [workflow dedicado](../../../.github/workflows/expedicion-liminal-integrada.yml) ejecuta ese script en Ubuntu y Windows. El workflow general conserva sus tareas anteriores. **Compilar no equivale a probar**, por eso hay ambas verificaciones.

## 7. Definición de suficiente

**Etapa 1 ya implementada:** las reglas R-INT-01…R-INT-06 y el guion de [ESCENARIO_01.md](ESCENARIO_01.md) están representados en Java y en pruebas. Esta base es **suficiente como laboratorio de capas y dominio**.

**Etapa 2 completada:** el integrador usa Strategy con tres políticas, mantiene la identidad y las rutas, narra encuentros sin combate y verifica la expedición completa. Esto es **suficiente para cerrar el proyecto integrador académico básico**. Los eventos, cámaras, inventarios y otros GoF son extensiones **opcionales**, no deuda técnica por llenar cuotas.

**No se requieren** interacción manual, gráficos, persistencia, física, batalla, Spring Boot, JavaFX, Maven ni todos los 23 GoF integrados.
