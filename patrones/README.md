# Patrones de diseño GoF: dos rutas de estudio

Este directorio contiene **dos catálogos independientes** de los mismos 23 patrones GoF.
Los patrones no son exclusivos del backend: también se aplican a interfaces,
simuladores y juegos.

| Ruta | Qué enseña | Cómo empezar |
| --- | --- | --- |
| [Clásicos](clasicos/README.md) | Contratos y roles técnicos tradicionales de los 23 GoF | Abrir `clasicos/Main.java` o un patrón concreto |
| [Liminales](liminales/README.md) | Los 23 GoF aplicados a un dominio de habitaciones, luces y criaturas | Abrir `liminales/GUIA_LECTURA_CODIGO.md` |

Cada colección conserva sus clientes y pruebas. No se comparten clases Java
entre ambas. Para la colaboración de varios patrones en un mismo sistema,
consulta el [integrador](../proyectos/expedicion-liminal-integrada/README.md).

Desde la raíz del repositorio: `bash scripts/verificar.sh`.
