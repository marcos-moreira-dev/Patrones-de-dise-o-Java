# Arquitectura propuesta — Expedición Liminal Integrada

> **Diseño preliminar sin clases implementadas.** Primera entrega: **monolito modular Java 21 con capas dirigidas al dominio**. Es suficiente para enseñar estructuras y responsabilidades; no se impone una arquitectura hexagonal completa, MVC ni un framework sin un motivo real.

## 1. Dependencias de paquetes

~~~mermaid
flowchart TD
    P["presentacion<br/>ClienteExpedicion"]
    A["aplicacion<br/>ProcesarTurno + ResultadoTurno"]
    E["dominio/exploracion<br/>Expedicion + Explorador"]
    M["dominio/mundo<br/>Mapa + Habitacion + Salida + Posicion + Direccion"]
    C["dominio/criaturas<br/>Etapa 2"]
    P --> A
    P -. "Composición inicial de entidades" .-> E
    P -. "Carga de escenario fijo" .-> M
    A --> E
    E --> M
    C --> M
~~~

- **La presentación depende de aplicación y dominio** solo porque construye el grafo inicial de objetos (*composition root*); no debe conocer cómo validar internamente una salida.
- **La aplicación depende del dominio**, coordina casos de uso y convierte rechazos esperados en resultados entendibles.
- **El dominio no depende de presentación ni aplicación**, tampoco de `Scanner`, red, archivos o framework.
- **No hay dependencia entre el proyecto integrado y las 23 demos encapsuladas.** La relación entre ambas es **documental/normativa**.
- Todavía no hay `infraestructura/`; crear un puerto de almacenamiento sería una abstracción prematura cuando todo está en memoria.

## 2. UML de relaciones principales (propuesta, no código existente)

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
        -String origenId
        -String destinoId
        -Direccion direccion
    }
    class MapaLiminal {
        -String id
        -Map~String,Habitacion~ habitaciones
        -Map~Posicion,Habitacion~ celdas
        -Map~String,Salida~ salidas
        +agregarHabitacion()
        +agregarSalida()
        +salidaDesde()
    }
    class Explorador {
        -String id
        -MapaLiminal mapa
        -Habitacion ubicacion
        +mover(Direccion) Habitacion
    }
    class Expedicion {
        -String id
        -Explorador explorador
        -Habitacion meta
        -int turnos
        -boolean finalizada
        +avanzar(Direccion) Habitacion
    }
    class ProcesarTurno {
        +procesar(Direccion) ResultadoTurno
    }
    class ResultadoTurno {
        <<record>>
        +boolean aceptado
        +int turno
        +String ubicacion
        +boolean finalizada
    }
    MapaLiminal --> "0..*" Habitacion : registra
    MapaLiminal --> "0..*" Salida : registra
    Habitacion --> "1" Posicion : coordenada
    Salida --> Direccion : dirección
    Explorador --> "1" MapaLiminal : conoce
    Explorador --> "1" Habitacion : ubicación actual
    Expedicion --> "1" Explorador : participante
    Expedicion --> "1" Habitacion : meta
    ProcesarTurno ..> Expedicion : ejecuta caso de uso
    ProcesarTurno ..> ResultadoTurno : devuelve valor
~~~

**Precaución UML:** la flecha indica navegación/uso de referencias según el modelo, **no composición**. El proyecto no necesita rombos negros para resultar profesional. `ResultadoTurno` y `Posicion` son datos/valores y pueden ser records; `Explorador`, `MapaLiminal`, `Habitacion` y `Expedicion` son entidades.

## 3. Paquetes concretos de la etapa 1

~~~text
src/main/java/liminal/integrado/
├── dominio/
│   ├── mundo/
│   │   ├── Posicion.java               // valor
│   │   ├── Direccion.java              // enum
│   │   ├── Habitacion.java             // entidad
│   │   ├── Salida.java                 // conexión dirigida
│   │   └── MapaLiminal.java            // valida índices y topología
│   └── exploracion/
│       ├── Explorador.java             // ubicación e identidad
│       └── Expedicion.java             // meta, turnos y cierre
├── aplicacion/
│   ├── ProcesarTurno.java              // orquestación/transformación del resultado
│   └── ResultadoTurno.java             // dato de salida
└── presentacion/
    └── ClienteExpedicion.java          // escena narrativa determinista

src/test/java/liminal/integrado/
└── PruebasExpedicion.java              // aceptación y regresiones
~~~

**Importante:** estos nombres son un contrato orientativo para la primera implementación, no una instrucción para generar clases de relleno. Si una operación o participante carece de responsabilidad observable, debe simplificarse antes de codificarlo.

## 4. Flujo de responsabilidades de un turno

~~~mermaid
sequenceDiagram
    participant Cliente as ClienteExpedicion
    participant Caso as ProcesarTurno
    participant Sesion as Expedicion
    participant Exp as Explorador
    participant Mapa as MapaLiminal
    Cliente->>Caso: procesar(ESTE)
    Caso->>Sesion: avanzar(ESTE)
    Sesion->>Exp: mover(ESTE)
    Exp->>Mapa: salidaDesde(ubicacion, ESTE)
    Mapa-->>Exp: Salida con destino válido
    Exp-->>Sesion: nueva Habitacion
    Sesion->>Sesion: turnos++, evaluar meta
    Sesion-->>Caso: estado confirmado
    Caso-->>Cliente: ResultadoTurno aceptado
~~~

Si el mapa no dispone de salida, el dominio rechaza **antes** de cambiar ubicación. El caso de uso puede capturar ese `IllegalStateException` esperado y producir un resultado de rechazo; no oculta excepciones por programación defectuosa. La sesión no incrementa su contador.

## 5. Decisiones arquitectónicas y sus alternativas

| Decisión | Justificación | Qué sería un mal olor aquí |
| --- | --- | --- |
| Paquetes por capacidad del dominio | Permiten ubicar reglas y colaborar sin organizar todo por nombres de patrones | 23 paquetes artificiales `strategy`, `observer`, etc. |
| `Posicion` como `record` | Igualdad de valores y coordenadas inmutables | Convertir entidades con ID y comportamiento en records por ahorrar código |
| `MapaLiminal` valida salidas | La integridad del grafo pertenece al mapa, no al cliente | Vista o cliente manipulando directamente colecciones internas |
| `Explorador` controla ubicación | La entidad no deja su posición modificable desde cualquier parte | Setter público que salta el mapa |
| `Expedicion` conserva turno/meta | Unifica el estado de la sesión sin convertirse en motor gráfico | Un único `MotorJuego` con mapa, render, terminal y criatura |
| `ProcesarTurno` coordina | El caso de uso traduce resultados sin duplicar reglas del dominio | Validación dispersa en cada capa |
| `ClienteExpedicion` crea el escenario | Deja explícitas las dependencias para aprender | DI container o factoría general de todo el universo innecesarios |
| Introducir patrones **después** del problema | OCP/SRP/DIP donde realmente aportan, no como ritual | Un interfaz por objeto o Singleton global sin necesidad |

## 6. Estrategia de pruebas y futura integración CI

**Primero el dominio**, luego el caso de uso y por último el relato de consola:

1. Mapa: límites, IDs/celdas únicas, salidas existentes y dirigidas, rechazo de diagonal y duplicados.
2. Explorador: identidad estable, ubicación inicial y movimiento válido; rechazo no mueve.
3. Expedición: cuatro éxitos, una solicitud inválida con cero costo, finalización, rechazo posterior.
4. Caso de uso: estados/resultados útiles sin depender de captura de consola.
5. Reproducibilidad: mismo escenario produce los mismos resultados.
6. Compilación Java 21, pruebas en Linux y Windows **sin usar el computador del estudiante**.

El workflow existente del repositorio compila archivos `*.java`, pero para certificar el nuevo proyecto habrá que asegurar también que **sus pruebas se ejecutan**, no declarar «probado» algo que solo compiló. Preferir un comando local del proyecto y una verificación explícita en CI cuando ya exista Java.

## 7. Definición de suficiente

**Etapa 1 es suficiente** cuando las reglas R-INT-01…R-INT-06 y el guion de [ESCENARIO_01.md](ESCENARIO_01.md) tienen código y pruebas aprobadas; el lector puede seguir las dependencias sin estudiar otro proyecto.

**Etapa 2 es suficiente para cerrar el proyecto integrador básico** cuando incorpora el monstruo con una Strategy justificada, mantiene todos los invariantes y ejecuta una expedición completa con un suceso narrativo. Los eventos, cámaras, inventarios y demás GoF son **opcionales**.

**No se requieren** interacción manual, gráficos, persistencia, física, batalla, Spring Boot, JavaFX, Maven ni todos los 23 GoF integrados.
