# Mapa de carpetas del laboratorio

Este mapa muestra **las responsabilidades**, no una copia rígida de la
salida de `tree` de una computadora. Para ver cada archivo real, consulta
el explorador de GitHub o ejecuta `git ls-files` desde la raíz.

```text
Patrones-de-dise-o-Java/
├── creacionales/                 Patrones de creación y ProbarCreacionales
├── estructurales/                Patrones estructurales y ProbarEstructurales
├── comportamiento/               Patrones de comportamiento y ProbarComportamiento
├── colecciones/liminal/           23 patrones narrativos y 7 variantes
│   ├── DOMINIO.md                 Invariantes, reglas y glosario
│   ├── PLAN_VARIANTES.md          Estado de ampliación y criterios
│   ├── AUDITORIA_ANDAMIAJE.md     Dominio, patrón y soporte técnico
│   ├── docs/patrones/             23 fichas y sus UML SVG
│   ├── docs/variantes/            7 fichas opcionales con UML Mermaid
│   ├── docs/laboratorios/         UML relacional y class vs record
│   ├── src/main/java/liminal/     Clientes, patrones y variantes por paquete
│   ├── src/test/java/liminal/     Pruebas por patrón y de laboratorio
│   └── assets/                    Tres ambientes y lámina original de objetos
├── docs/uml/                     Diagramas de la guía UML y su generador
├── proyectos/
│   ├── arena-java/               Videojuego de combate por turnos
│   │   ├── src/main/java/arena/   Dominio, aplicación, infraestructura, presentación
│   │   ├── src/test/java/arena/   Pruebas sin frameworks
│   │   └── docs/uml/             Diagramas SVG y fuentes PlantUML
│   └── miniso/                   Simulador de planificación de CPU y archivos
│       ├── src/main/java/miniso/ Dominio, aplicación, estructuras, presentación
│       ├── src/test/java/miniso/ Pruebas deterministas
│       └── docs/uml/             Diagramas SVG y fuentes PlantUML
├── scripts/verificar.sh          Comando único de validación
├── .github/workflows/java21.yml Integración continua
├── UML_GUIDE.md                  Símbolos, multiplicidades y Java aplicado
├── CONTRIBUTING.md              Rutina profesional de trabajo
├── App.java y Main.java          Menú de los patrones clásicos
└── README.md                     Punto de entrada
```

Los dos microproyectos tienen su propio `pom.xml`, `README.md` y `BUILD.md`.
Las salidas regenerables `target/`, `out/` y `*.class` están ignoradas por Git.

En los ejemplos clásicos, los paquetes Java siguen la jerarquía
`creacionales.*`, `estructurales.*` y `comportamiento.*`. En los microproyectos
se practican paquetes organizados por responsabilidades reales.
