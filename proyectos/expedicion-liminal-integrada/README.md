# Expedición Liminal Integrada — laboratorio de arquitectura

> **Estado (15 de noviembre de 2026): diseño del primer caso de uso completado; código Java todavía no implementado.**
> Este es el **tercer bloque de estudio GoF** del repositorio: integración de patrones en un único dominio. No sustituye los [23 ejemplos liminales independientes](../../colecciones/liminal/README.md) ni la colección técnica clásica.

## Qué se estudia aquí

No se busca construir un videojuego comercial ni una interfaz atractiva. El objeto de estudio será **la estructura del código Java 21**: límites de responsabilidad, dominio e invariantes, dependencias, paquetes, objetos-valor, entidades, casos de uso, SOLID, buen criterio de excepciones y patrones GoF que resuelvan problemas reales.

Al principio, una clase `ClienteExpedicion` narrará automáticamente cuatro desplazamientos válidos y un intento rechazado. **No se necesita teclado, menú interactivo, motor de juego, servidor ni persistencia**. Cuando exista código, la compilación y las pruebas deben poder ejecutarse sin intervención del estudiante.

## Documentos de diseño, en orden de lectura

1. [DOMINIO.md](DOMINIO.md): reglas del mundo heredadas, restricciones locales del mapa, transiciones y condiciones de cierre.
2. [Primera expedición — escenario concreto](docs/ESCENARIO_01.md): mapa de seis habitaciones, salidas, eventos y recorrido esperado.
3. [Arquitectura y UML propuesto](docs/ARQUITECTURA.md): entidades, capas, asociaciones, dependencias permitidas, malos olores a evitar y decisiones de implementación.

**Autoridad del dominio:** [DOMINIO de los ejemplos liminales](../../colecciones/liminal/DOMINIO.md). Este integrador **hereda R-00…R-14** y agrega reglas `R-INT-01…R-INT-08` únicamente para su caso de estudio. Si se descubre una contradicción, se resuelve primero en documentación, nunca haciendo que un patrón imponga reglas narrativas.

## Límites del primer módulo

- **Entrega mínima (etapa 1):** dominio de habitaciones, posiciones, salidas dirigidas, explorador, expedición, caso de uso `ProcesarTurno`, un cliente reproducible y pruebas. Sin código todavía.
- **Siguiente ampliación (etapa 2):** el mismo mapa admite un `MonstruoOscuro`, una estrategia activa intercambiable y un encuentro sin combate, con validación por el dominio.
- **Opcional:** dispositivos de oficinas, cámaras, linterna y registro. Añadir solo si ofrecen una colaboración útil; nunca para completar artificialmente los 23 patrones.

**Regla de parada:** con una expedición completa, verificable por pruebas, un dominio consistente y una arquitectura comprensible, el integrador será **suficiente para estudiar**. Más funcionalidades son opcionales. El [plan común](../../colecciones/liminal/PLAN_INTEGRADOR.md) conserva la ruta de los tres bloques.

## Separación física

Las clases del nuevo proyecto deberán vivir bajo `src/main/java/liminal/integrado/`, sin importar ninguna implementación de `colecciones/liminal/src/main/java`. Se reutiliza el **conocimiento y las reglas**, no los tipos Java de las demos.
