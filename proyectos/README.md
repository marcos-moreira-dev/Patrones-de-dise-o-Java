# Tres proyectos Java independientes

Cada proyecto tiene su propio dominio, punto de entrada, pruebas y documentación.
Comparten objetivos educativos, **no clases Java ni una aplicación monolítica**.

| Proyecto | Propósito | Ejecución |
| --- | --- | --- |
| [Arena Java](arena-java/README.md) | Combate por turnos, reglas de escenarios y eventos | `arena.presentacion.ArenaJava` |
| [MiniSO](miniso/README.md) | Planificación de CPU, E/S, memoria y archivos virtuales | `miniso.presentacion.MiniSO` |
| [Expedición Liminal Integrada](expedicion-liminal-integrada/README.md) | Colaboración entre Strategy, Observer y Mediator en cuatro guiones | Cuatro clientes de presentación |

Arena y MiniSO incluyen `pom.xml` y guías de construcción opcionales con Maven.
El integrador usa Java 21 y [su propio script](expedicion-liminal-integrada/scripts/verificar.sh).

**Verificación global desde la raíz:**

```bash
bash scripts/verificar.sh
```

Los catálogos de patrones aislados se encuentran en [patrones/](../patrones/README.md).
