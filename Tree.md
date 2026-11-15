# Mapa de carpetas del laboratorio

Este mapa muestra las responsabilidades de cada bloque. Para el listado real
de archivos utiliza el explorador de GitHub o `git ls-files` desde la raíz.

```text
Patrones-de-dise-o-Java/
├── patrones/
│   ├── clasicos/                          23 ejemplos GoF técnicos tradicionales
│   │   ├── creacionales/                  Incluye ProbarCreacionales.java
│   │   ├── estructurales/                 Incluye ProbarEstructurales.java
│   │   ├── comportamiento/                Incluye ProbarComportamiento.java
│   │   ├── App.java                       Menú de las tres familias GoF
│   │   └── Main.java                      Punto de entrada del menú
│   └── liminales/                         23 ejemplos GoF narrativos y 7 variantes
│       ├── DOMINIO.md                     Reglas e invariantes liminales
│       ├── GUIA_LECTURA_CODIGO.md         Recorrido: cliente, roles, UML y pruebas
│       ├── docs/patrones/                 23 fichas con UML
│       ├── docs/variantes/                Siete variantes opcionales
│       ├── docs/laboratorios/             Relaciones UML y class vs record
│       ├── src/main/java/liminal/         Clientes y roles GoF por paquete
│       ├── src/test/java/liminal/         Pruebas por patrón y laboratorio
│       ├── assets/                        Imágenes originales
│       └── scripts/verificar.sh           Prueba independiente de la colección
├── proyectos/
│   ├── arena-java/                        Combate por turnos
│   │   ├── src/main/java/arena/           Dominio, aplicación, infraestructura, presentación
│   │   └── src/test/java/arena/           Pruebas independientes
│   ├── miniso/                            Simulador educativo de CPU, E/S y archivos
│   │   ├── src/main/java/miniso/          Dominio, aplicación, estructuras, presentación
│   │   └── src/test/java/miniso/          Pruebas y métricas deterministas
│   └── expedicion-liminal-integrada/      Integrador independiente Strategy/Observer/Mediator
│       ├── src/main/java/liminal/integrado/
│       ├── src/test/java/liminal/integrado/
│       └── scripts/verificar.sh
├── docs/                                  Cierre académico, modelado y UML transversal
├── scripts/verificar.sh                   Verificación global de las dos colecciones y tres proyectos
├── .github/workflows/                     Java 21, Javadoc y pruebas en Ubuntu/Windows
├── UML_GUIDE.md                           Guía transversal de relaciones UML
├── CONTRIBUTING.md                        Reglas de contribución y pruebas
└── README.md                              Índice principal
```

Los dos catálogos GoF y el proyecto integrador tienen compilaciones autónomas.
Los tres proyectos permanecen juntos en `proyectos/` sin acoplar sus paquetes Java.
Arena y MiniSO tienen `pom.xml`, `BUILD.md`, `README.md` y JAR opcionales;
el integrador utiliza su propio script Java 21 sin requerir Maven.

Desde la raíz del repositorio, ejecuta `bash scripts/verificar.sh` para validar
los tres proyectos y las dos colecciones GoF.
