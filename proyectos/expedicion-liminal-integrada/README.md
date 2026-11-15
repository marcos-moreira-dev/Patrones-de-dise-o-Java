# Expedición Liminal Integrada — laboratorio de arquitectura

> **Estado (15 de noviembre de 2026): etapa 1 IMPLEMENTADA en Java 21, con pruebas y cliente determinista.** El monstruo oscuro queda reservado para la etapa 2.
> Este es el **tercer bloque de estudio GoF** del repositorio: integración de patrones en un único dominio. No sustituye los [23 ejemplos liminales independientes](../../colecciones/liminal/README.md) ni la colección técnica clásica.

## Qué se estudia aquí

No se busca construir un videojuego comercial ni una interfaz atractiva. El objeto de estudio será **la estructura del código Java 21**: límites de responsabilidad, dominio e invariantes, dependencias, paquetes, objetos-valor, entidades, casos de uso, SOLID, buen criterio de excepciones y patrones GoF que resuelvan problemas reales.

La clase [ClienteExpedicion](src/main/java/liminal/integrado/presentacion/ClienteExpedicion.java) **narra automáticamente cuatro desplazamientos válidos y dos intentos rechazados** (uno por ruta inexistente y otro después de alcanzar la meta). **No se necesita teclado, menú interactivo, motor de juego, servidor ni persistencia**. La [prueba autónoma](src/test/java/liminal/integrado/PruebasExpedicion.java) verifica las invariantes sin intervención del estudiante.

## Documentos de diseño, en orden de lectura

1. [DOMINIO.md](DOMINIO.md): reglas del mundo heredadas, restricciones locales del mapa, transiciones y condiciones de cierre.
2. [Primera expedición — escenario concreto](docs/ESCENARIO_01.md): mapa de seis habitaciones, salidas, eventos y recorrido esperado.
3. [Arquitectura y UML](docs/ARQUITECTURA.md): entidades implementadas, capas, asociaciones, dependencias permitidas, malos olores a evitar y decisiones de implementación.

**Autoridad del dominio:** [DOMINIO de los ejemplos liminales](../../colecciones/liminal/DOMINIO.md). Este integrador **hereda R-00…R-14** y agrega reglas `R-INT-01…R-INT-08` únicamente para su caso de estudio. Si se descubre una contradicción, se resuelve primero en documentación, nunca haciendo que un patrón imponga reglas narrativas.

## Límites del primer módulo

- **Entrega mínima (etapa 1, completada):** dominio de habitaciones, posiciones, salidas dirigidas, explorador, expedición, caso de uso `ProcesarTurno`, cliente reproducible y pruebas ejecutables en Java 21.
- **Siguiente ampliación (etapa 2):** el mismo mapa admite un `MonstruoOscuro`, una estrategia activa intercambiable y un encuentro sin combate, con validación por el dominio.
- **Opcional:** dispositivos de oficinas, cámaras, linterna y registro. Añadir solo si ofrecen una colaboración útil; nunca para completar artificialmente los 23 patrones.

**Regla de parada:** con una expedición completa, verificable por pruebas, un dominio consistente y una arquitectura comprensible, el integrador será **suficiente para estudiar**. Más funcionalidades son opcionales. El [plan común](../../colecciones/liminal/PLAN_INTEGRADOR.md) conserva la ruta de los tres bloques.

## Separación física

Las clases del proyecto viven bajo `src/main/java/liminal/integrado/` y **no importan implementaciones** de `colecciones/liminal/src/main/java`. Se reutiliza el **conocimiento y las reglas**, no los tipos Java de las demos.

## Verificación técnica independiente

Desde la raíz del repositorio, el comando `bash proyectos/expedicion-liminal-integrada/scripts/verificar.sh` compila con `--release 21`, ejecuta `PruebasExpedicion` y comprueba el relato del cliente. Existe además un [workflow específico de GitHub Actions](../../.github/workflows/expedicion-liminal-integrada.yml) para Linux y Windows, sin alterar el workflow general.

**El proyecto todavía no tiene patrones GoF integrados de forma explícita**: la etapa 1 enseña la arquitectura y el dominio; la etapa 2 incorporará Strategy para el monstruo oscuro. No añadir más patrones por cuotas.
