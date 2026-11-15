# Expedición Liminal Integrada — laboratorio de arquitectura

> **Estado (15 de noviembre de 2026): proyecto integrador académico cerrado en Java 21.** Etapas 1 y 2, refuerzo de encapsulamiento y escena transversal con **Strategy, Observer y Mediator**, cliente determinista y pruebas en Linux/Windows. Nuevas mecánicas no son requisitos.
> Este es el **tercer bloque de estudio GoF** del repositorio: integración de patrones en un único dominio. No sustituye los [23 ejemplos liminales independientes](../../colecciones/liminal/README.md) ni la colección técnica clásica.

## Qué se estudia aquí

No se busca construir un videojuego comercial ni una interfaz atractiva. El objeto de estudio será **la estructura del código Java 21**: límites de responsabilidad, dominio e invariantes, dependencias, paquetes, objetos-valor, entidades, casos de uso, SOLID, buen criterio de excepciones y patrones GoF que resuelvan problemas reales.

La clase [ClienteExpedicion](src/main/java/liminal/integrado/presentacion/ClienteExpedicion.java) **narra automáticamente cuatro desplazamientos válidos, dos intentos rechazados y los movimientos de una criatura** (uno por ruta inexistente y otro después de alcanzar la meta). **No se necesita teclado, menú interactivo, motor de juego, servidor ni persistencia**. La [prueba autónoma](src/test/java/liminal/integrado/PruebasExpedicion.java) verifica las invariantes sin intervención del estudiante.

## Documentos de diseño, en orden de lectura

1. [DOMINIO.md](DOMINIO.md): reglas del mundo heredadas, restricciones locales del mapa, transiciones y condiciones de cierre.
2. [Primera expedición — escenario concreto](docs/ESCENARIO_01.md): mapa de seis habitaciones, salidas, eventos y recorrido esperado.
3. [Arquitectura y UML](docs/ARQUITECTURA.md): entidades y colaboraciones implementadas, dependencias, buenas prácticas y alternativas.
4. [Auditoría arquitectónica final](docs/AUDITORIA_FINAL.md): revisión de SOLID, límites, patrones, riesgos, pruebas y criterio de suficiencia.

**Autoridad del dominio:** [DOMINIO de los ejemplos liminales](../../colecciones/liminal/DOMINIO.md). Este integrador **hereda R-00…R-14** y agrega reglas `R-INT-01…R-INT-11` únicamente para su caso de estudio. Si se descubre una contradicción, se resuelve primero en documentación, nunca haciendo que un patrón imponga reglas narrativas.

## Límites del primer módulo

- **Entrega mínima (etapa 1, completada):** dominio de habitaciones, posiciones, salidas dirigidas, explorador, expedición, caso de uso `ProcesarTurno`, cliente reproducible y pruebas ejecutables en Java 21.
- **Integración Strategy (etapa 2, completada):** el mismo mapa incluye un `MonstruoOscuro` y estrategias `Patrullar`, `Perseguir` y `Esperar` intercambiables. La criatura valida cada propuesta, conserva su identidad y puede coincidir con el explorador sin combate.
- **Escena de vigilancia (ampliación selectiva completada):** `SensorRuido` publica un evento mediante Observer y `CentroVigilancia` coordina luz y alarma como Mediator; ocurre cuando el monstruo entra en H-03.
- **Opcional, no pendiente:** cámaras, linterna, inventario, registros y más patrones. No añadirlos para completar artificialmente los 23 GoF.

**Regla de parada:** con una expedición completa, verificable por pruebas, un dominio consistente y una arquitectura comprensible, el integrador será **suficiente para estudiar**. Más funcionalidades son opcionales. El [plan común](../../colecciones/liminal/PLAN_INTEGRADOR.md) conserva la ruta de los tres bloques.

## Separación física

Las clases del proyecto viven bajo `src/main/java/liminal/integrado/` y **no importan implementaciones** de `colecciones/liminal/src/main/java`. Se reutiliza el **conocimiento y las reglas**, no los tipos Java de las demos.

## Verificación técnica independiente

Desde la raíz del repositorio, el comando `bash proyectos/expedicion-liminal-integrada/scripts/verificar.sh` compila con `--release 21`, ejecuta [`PruebasExpedicion`](src/test/java/liminal/integrado/PruebasExpedicion.java), [`PruebasCriaturas`](src/test/java/liminal/integrado/PruebasCriaturas.java) y [`PruebasVigilancia`](src/test/java/liminal/integrado/PruebasVigilancia.java), además de comprobar el relato del cliente. Existe además un [workflow específico de GitHub Actions](../../.github/workflows/expedicion-liminal-integrada.yml) para Linux y Windows, sin alterar el workflow general.

**Tres GoF integrados de forma explícita:** Strategy (comportamiento de la criatura), Observer (distribución de avisos del sensor) y Mediator (central que coordina receptores). `Expedicion` conserva internamente al monstruo y solo publica una vista `EstadoCriatura`; `ProcesarTurno` conecta el movimiento con el sensor. [Ver UML](docs/ARQUITECTURA.md) y [auditoría](docs/AUDITORIA_FINAL.md). El proyecto es suficiente para estudiar; no existe obligación de incorporar los 20 patrones restantes.
