# Arquitectura final — Expedición Liminal Integrada

> **15 de noviembre de 2026.** Proyecto de estudio Java 21: mapa y expedición, criatura con **Strategy**, sensor con **Observer**, central con **Mediator**. UML ajustado al código implementado; no se promete un motor gráfico, una arquitectura hexagonal completa ni características de producción.

**Lecturas relacionadas:** [oficinas](ESCENARIO_01.md), [tres guiones complementarios](ESCENARIOS_02_04.md), [reglas del dominio](../DOMINIO.md), [auditoría final](AUDITORIA_FINAL.md).

## 1. Capas y dirección de dependencias

~~~mermaid
flowchart TD
    P["presentacion: cuatro clientes de guion"]
    A["aplicacion: ProcesarTurno y ResultadoTurno"]
    E["dominio/exploracion: Expedicion, Explorador y AvanceExpedicion"]
    M["dominio/mundo: mapa, habitaciones, salidas y posiciones"]
    C["dominio/criaturas: MonstruoOscuro, Strategy, EstadoCriatura"]
    V["dominio/vigilancia: SensorRuido, Observer, CentroVigilancia, receptores"]
    R["dominio/comunicaciones: RadioLiminal"]
    P --> A
    P -. "construye escenario" .-> E
    P -. "construye mapa" .-> M
    P -. "configura Strategy" .-> C
    P -. "conecta vigilancia" .-> V
    P -. "sintoniza la radio" .-> R
    R --> M
    A --> E
    A --> M
    A --> V
    E --> M
    E --> C
    C --> M
    V --> M
~~~

El dominio **no importa** clases de aplicación ni de presentación. La presentación depende de varias capas porque crea el grafo inicial de objetos (*composition root*); no gobierna sus invariantes. La aplicación adapta resultados y conecta el dispositivo de vigilancia, sin decidir las salidas del mapa.

Esto es un monolito Java 21 **organizado por paquetes y responsabilidades**, no módulos JPMS. No hay contenedores de inyección, MVC, repositorios ni infraestructura vacía. Tampoco hay imports desde los 23 ejemplos GoF aislados.

## 2. UML principal — identidades y Strategy

~~~mermaid
classDiagram
    direction LR
    class Posicion {
        <<record>>
        +int x
        +int y
    }
    class Habitacion {
        -String id
        -Posicion posicion
    }
    class Salida {
        -String id
        -Habitacion origen
        -Habitacion destino
        -Direccion direccion
    }
    class Direccion {
        <<enumeration>>
        NORTE
        SUR
        ESTE
        OESTE
    }
    class MapaLiminal {
        -Map~String,Habitacion~ habitacionesPorId
        -Map~Posicion,Habitacion~ habitacionesPorPosicion
        -Map~String,Salida~ salidasPorId
        +agregarHabitacion()
        +agregarSalida()
        +destinosDesde()
        +salidaDesde()
    }
    class Explorador {
        -MapaLiminal mapa
        -Habitacion ubicacion
        ~mover(Direccion) Habitacion
    }
    class Expedicion {
        -MapaLiminal mapa
        -Explorador explorador
        -Habitacion meta
        -MonstruoOscuro criatura
        -int turnos
        +incorporarCriatura(String, Habitacion, EstrategiaMovimiento)
        +cambiarEstrategiaCriatura(EstrategiaMovimiento)
        +estadoCriatura() Optional~EstadoCriatura~
        +avanzar(Direccion) AvanceExpedicion
    }
    class AvanceExpedicion {
        <<record>>
        +Habitacion habitacion
        +EstadoCriatura criatura
        +boolean criaturaSeMovio
        +boolean criaturaRechazada
    }
    class EstadoCriatura {
        <<record>>
        +String id
        +Habitacion ubicacion
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
    MapaLiminal --> "0..*" Habitacion : registra
    MapaLiminal --> "0..*" Salida : registra
    Habitacion --> "1" Posicion : usa valor
    Salida --> "2" Habitacion : conecta
    Salida --> Direccion
    Explorador --> MapaLiminal : valida
    Explorador --> "1" Habitacion : ubicación
    Expedicion --> "1" Explorador : participante
    Expedicion --> "0..1" MonstruoOscuro : instancia privada
    Expedicion ..> EstadoCriatura : publica vista
    Expedicion ..> AvanceExpedicion : devuelve resultado
    MonstruoOscuro --> EstrategiaMovimiento : conducta
    MonstruoOscuro --> MapaLiminal : comprueba salida
    EstrategiaMovimiento <|.. Patrullar
    EstrategiaMovimiento <|.. Perseguir
    EstrategiaMovimiento <|.. Esperar
~~~

**Encapsulamiento:** `Expedicion` construye internamente a `MonstruoOscuro`, controla las acciones del turno y solo devuelve un `Optional<EstadoCriatura>` con una fotografía inmutable; no entrega su referencia mutable. El cliente pide cambios de Strategy mediante la sesión. Un `MonstruoOscuro` creado por separado puede seguir probándose públicamente, pero **no es el mismo objeto** que conserva la sesión.

**UML:** una asociación Java privada **no prueba composición UML** ni implica destrucción física de la criatura. `Posicion`, `AvanceExpedicion` y `EstadoCriatura` son valores/records; `Habitacion` y `MonstruoOscuro` son entidades. El método `Explorador.mover` tiene acceso de paquete (`~`).

## 3. UML de vigilancia — Observer y Mediator

~~~mermaid
classDiagram
    direction LR
    class SensorRuido {
        -String id
        -Habitacion habitacionVigilada
        -Map~String,ObservadorRuido~ observadores
        +suscribir(ObservadorRuido)
        +desuscribir(String)
        +detectarMovimiento(Habitacion)
    }
    class ObservadorRuido {
        <<interface>>
        +id() String
        +alDetectar(EventoRuido)
    }
    class EventoRuido {
        <<record>>
        +String sensorId
        +String habitacionId
    }
    class CentroVigilancia {
        -String id
        -String sensorAutorizado
        -LuzAlerta luz
        -Alarma alarma
        +alDetectar(EventoRuido)
    }
    class LuzAlerta {
        -boolean encendida
        +encender()
    }
    class Alarma {
        -int avisos
        -EventoRuido ultimo
        +avisar(EventoRuido)
    }
    SensorRuido --> "0..*" ObservadorRuido : notifica
    CentroVigilancia ..|> ObservadorRuido
    SensorRuido --> Habitacion : vigila
    SensorRuido ..> EventoRuido : publica
    CentroVigilancia --> LuzAlerta : coordina
    CentroVigilancia --> Alarma : coordina
    Alarma --> EventoRuido : último aviso
~~~

Observer describe cómo el sensor avisa sin conocer receptores concretos. Mediator describe cómo `CentroVigilancia` coordina `LuzAlerta` y `Alarma`. No son el mismo patrón ni se solapan sus responsabilidades. `sensorAutorizado` es una regla local por ID, **no autenticación ni seguridad de red**.

## 4. Secuencia real de un turno válido con vigilancia

~~~mermaid
sequenceDiagram
    participant Cliente as ClienteExpedicion
    participant Caso as ProcesarTurno
    participant Sesion as Expedicion
    participant Exp as Explorador
    participant M as MonstruoOscuro
    participant Estrategia as EstrategiaMovimiento
    participant Mapa as MapaLiminal
    participant Sensor as SensorRuido
    participant Centro as CentroVigilancia
    participant Luz as LuzAlerta
    participant Alarma
    Cliente->>Caso: procesar(ESTE)
    Caso->>Sesion: avanzar(ESTE)
    Sesion->>Exp: mover(ESTE)
    Exp->>Mapa: salidaDesde(...)
    Mapa-->>Exp: salida válida
    Exp-->>Sesion: nueva habitación
    Sesion->>Sesion: turnos++, evaluar meta
    opt existe criatura
        Sesion->>M: avanzar(nuevaHabitacion)
        M->>Mapa: destinosDesde(...)
        M->>Estrategia: elegirDestino(...)
        Estrategia-->>M: propuesta
        M->>M: validar antes de cambiar ubicación
        M-->>Sesion: ubicación actual
    end
    Sesion-->>Caso: AvanceExpedicion
    opt criaturaSeMovio y sensor vigila destino
        Caso->>Sensor: detectarMovimiento(habitacion)
        Sensor->>Centro: alDetectar(EventoRuido)
        Centro->>Luz: encender()
        Centro->>Alarma: avisar(EventoRuido)
    end
    Caso-->>Cliente: ResultadoTurno
~~~

**Rechazos:** sin salida válida, no hay turno, criatura ni aviso. Una propuesta inválida de Strategy rechaza solo el movimiento del monstruo y conserva la acción confirmada del explorador. Si un observador falla **después** del turno, la excepción inesperada puede propagarse; no existe rollback del evento ni reintentos automáticos. Es un límite explícito, no un defecto ocultado con un `catch` genérico.

## 5. Paquetes y archivos de las tres etapas

~~~text
src/main/java/liminal/integrado/
├── dominio/
│   ├── mundo/
│   │   ├── Posicion.java, Direccion.java, Habitacion.java
│   │   └── Salida.java, MapaLiminal.java
│   ├── exploracion/
│   │   ├── Explorador.java, Expedicion.java
│   │   └── AvanceExpedicion.java
│   ├── criaturas/
│   │   ├── MonstruoOscuro.java, EstadoCriatura.java
│   │   ├── EstrategiaMovimiento.java
│   │   └── Patrullar.java, Perseguir.java, Esperar.java
│   ├── vigilancia/
│   │   ├── SensorRuido.java, ObservadorRuido.java, EventoRuido.java
│   │   └── CentroVigilancia.java, LuzAlerta.java, Alarma.java
│   └── comunicaciones/
│       └── RadioLiminal.java
├── aplicacion/
│   └── ProcesarTurno.java, ResultadoTurno.java
└── presentacion/
    ├── ClienteExpedicion.java
    ├── ClientePiscinas.java
    ├── ClienteRadio.java
    └── ClienteLuces.java

src/test/java/liminal/integrado/
├── PruebasExpedicion.java
├── PruebasCriaturas.java
├── PruebasVigilancia.java
└── PruebasGuiones.java
~~~

## 6. Decisiones arquitectónicas y malos olores evitados

| Decisión | Principio / beneficio | Evita |
| --- | --- | --- |
| Sesión construye su propia criatura y publica `EstadoCriatura` | Encapsulamiento de invariantes | Referencia mutable que permite saltar turnos |
| Mapa valida conexiones dirigidas | Cohesión y SRP | Cliente manipulando posiciones y rutas |
| `Posicion` como `record`, `Habitacion` como `class` | Valor vs. identidad | Confundir una coordenada con una entidad |
| Strategy intercambiable con validación de destino | OCP y comportamiento polimórfico | Condicionales crecientes, teletransporte |
| Sensor depende de `ObservadorRuido` | Desacoplamiento / DIP justificado | Sensor dependiente de la central o de la alarma |
| Mediador coordina luz y alarma | Responsabilidad de colaboración concreta | Todos los dispositivos dependiendo de todos |
| `ProcesarTurno` conecta dominio y sensor | Separación aplicación/dominio | Monstruo con referencias a la vista y los receptores |
| Cliente prepara dependencias directamente | Composition root sencilla | Contenedor DI, puertos o servicios sin necesidad |
| Radio como dispositivo de dominio pequeño | Mensajes por habitación/frecuencia y validación local | Forzar Adapter o Bridge sin necesidad |

No se reclama una demostración formal de los cinco SOLID. SRP/OCP y dependencia hacia contratos resultan observables; ISP está representado modestamente por interfaces pequeñas. Ningún principio obliga a inventar interfaces para todos los objetos.

## 7. Patrones integrados, pruebas y suficiencia

| GoF | Problema que resuelve | Prueba |
| --- | --- | --- |
| **Strategy** | Variar patrulla, persecución y espera de una criatura | [PruebasCriaturas.java](../src/test/java/liminal/integrado/PruebasCriaturas.java) |
| **Observer** | Entregar eventos del sensor a sus observadores | [PruebasVigilancia.java](../src/test/java/liminal/integrado/PruebasVigilancia.java) |
| **Mediator** | Activar luz y alarma por coordinación central | [PruebasVigilancia.java](../src/test/java/liminal/integrado/PruebasVigilancia.java) |

Los otros **20 GoF** se conservan en los [23 ejemplos liminales autónomos](../../../colecciones/liminal/MATRIZ_ALINEACION.md). No son requisitos pendientes de este simulador.

**Verificación:** `bash proyectos/expedicion-liminal-integrada/scripts/verificar.sh` compila Java 21, ejecuta cuatro programas de pruebas y comprueba los cuatro clientes por consola. El [workflow propio](../../../.github/workflows/expedicion-liminal-integrada.yml) corre en Ubuntu y Windows; el workflow general preserva los otros proyectos.

**Definición de suficiente:** cuatro expediciones narrativas contrastantes, reglas coherentes, tres GoF justificados que colaboran, código y UML correspondientes, pruebas aprobadas y límites documentados. No se exige interacción manual, gráficos, persistencia ni todos los GoF integrados.
