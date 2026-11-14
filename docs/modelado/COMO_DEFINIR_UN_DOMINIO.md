# Cómo definir un dominio antes de programar

> Guía transversal del repositorio. Apta para Java 21, UML y patrones de diseño. Un dominio no es una lista de clases ni un diagrama hecho a partir del código: es un **modelo explícito de una realidad o ficción delimitada**.

## 1. Qué es un dominio (y qué no es)

**Dominio:** conjunto de conceptos, relaciones, hechos asumidos, comportamientos y restricciones relevantes para resolver un problema. Se modela desde un **contexto delimitado**: por ejemplo, *explorar habitaciones*, no «simular todo el universo de Backrooms».

No son lo mismo:

- **Realidad o ficción de referencia:** ideas culturales, imágenes o historias que inspiran.
- **Modelo de dominio:** qué afirmaciones decidimos que sean ciertas *en nuestro sistema*.
- **Caso de uso:** qué objetivo intenta cumplir un actor.
- **Patrón GoF:** solución de diseño para organizar responsabilidades y colaboraciones.
- **Implementación Java:** clases, objetos, interfaces, referencias y algoritmos que respetan las reglas.

**Orden correcto:** problema → límites → vocabulario → reglas → ejemplos y contraejemplos → operaciones → UML → código → pruebas. No hay que utilizar un patrón para inventar arbitrariamente las reglas del mundo.

## 2. Cómo escribir una regla verificable

Una buena regla responde cuatro preguntas:

1. **Ámbito:** ¿a qué objetos y durante qué etapa se aplica?
2. **Condición:** ¿qué debe ser verdadero?
3. **Contraejemplo:** ¿qué situación está prohibida?
4. **Verificación:** ¿cómo sabemos que la implementación la respeta?

Forma recomendada: **ID + enunciado en español + expresión opcional + ejemplo válido + caso inválido + prueba**.

| Tipo de afirmación | Pregunta | Ejemplo |
| --- | --- | --- |
| Supuesto | ¿Qué daremos por cierto? | «En esta simulación el mapa no cambia durante un movimiento» |
| Invariante | ¿Qué nunca debe quedar incumplido en un estado válido? | «Cada explorador activo ocupa exactamente una habitación» |
| Restricción | ¿Qué valores o vínculos están permitidos? | «Una puerta no puede estar abierta y bloqueada simultáneamente» |
| Precondición | ¿Qué debe cumplirse *antes* de ejecutar? | «La salida existe y la puerta permite pasar» |
| Postcondición | ¿Qué se garantiza *después* de ejecutar con éxito? | «La ubicación pasa a ser la habitación destino» |
| Regla de transición | ¿Qué cambios de estado están permitidos? | «Desbloquear transforma BLOQUEADA en CERRADA» |
| Política de error | ¿Qué ocurre cuando la operación no puede completarse? | «Se rechaza el movimiento y la ubicación no cambia» |
| Restricción local | ¿Qué solo exige un ejercicio? | «En el ejemplo Observer, un interruptor notifica a dos luces» |

**Invariante** no significa «algo que jamás cambia»: una *ubicación* puede cambiar; lo invariable es que su valor sea **siempre válido**. Una **restricción** es un término amplio: puede ser invariante, precondición, multiplicidad o regla de transición. Un **supuesto** es una decisión de modelado, no una verdad externa demostrada.

## 3. Un poco de notación matemática, sin perder claridad

No es obligatorio usar lógica formal; úsala cuando una frase natural sea ambigua.

| Notación | Lectura |
| --- | --- |
| `∀ x ∈ X, P(x)` | Para todo x de X se cumple P |
| `∃! x ∈ X : P(x)` | Existe exactamente un x que cumple P |
| `A ⇒ B` | Si A, entonces B |
| `A ∧ B` | A y B simultáneamente |
| `A ∈ {a,b,c}` | A solo puede tomar esos valores |
| `|S| ≤ 1` | El conjunto S tiene como máximo un elemento |
| `pre → acción → post` | Condición inicial, operación, condición resultante |

### Ejemplo guiado: movimiento entre habitaciones

**R-UBI-01 — Ubicación única del explorador activo**

- Texto: «En una expedición activa, cada explorador tiene exactamente una ubicación actual que corresponde a una habitación existente del mapa».
- Formalización breve: `∀ e ∈ ExploradoresActivos, e.habitacionActual ∈ HabitacionesDelMapa` y `|ubicacionesActuales(e)| = 1`.
- Válido: Ana está en H-01.
- Inválido: Ana está a la vez en H-01 y H-02; o su ubicación es H-99, que no existe.
- Prueba: crear un explorador en H-01; intentar asignar una habitación ajena al mapa; la operación debe rechazarla.

**R-MOV-02 — Movimiento autorizado**

- Precondición: desde la habitación actual existe una salida dirigida hacia el destino; si tiene puerta, está abierta.
- Postcondición si tiene éxito: la ubicación pasa a ser el destino **sin crear otro explorador**.
- Si falla: ubicación idéntica a la inicial; motivo de rechazo conocido.

En términos de Hoare (opcional): `{salidaExiste ∧ puertaTransitable} mover(destino) {ubicacionActual = destino}`.

Estas fórmulas solo expresan lo que **hemos decidido modelar**; no convierten una leyenda de internet en una ley matemática.

## 4. De las reglas a clases y asociaciones UML

**Primero las relaciones semánticas, después la estructura de referencias Java.**

| Pregunta de dominio | Decisión de modelado |
| --- | --- |
| ¿Tiene identidad propia? | Posible entidad, p. ej. `Habitacion` con ID |
| ¿Importan sus valores, no su identidad? | Posible objeto-valor, p. ej. `Coordenada` o `ColorLuz` |
| ¿Qué debe garantizar? | Invariante y métodos que evitan estados ilegales |
| ¿Con cuántos se relaciona? | Multiplicidad UML: `1`, `0..1`, `0..*`... |
| ¿Quién lo conoce y necesita acceder? | Navegabilidad / asociación / dependencia |
| ¿Es una parte con propietario exclusivo? | Posible composición; justificar pertenencia y ciclo de vida |
| ¿Es un grupo con autonomía de los miembros? | Posible agregación *solo si* su significado queda definido |
| ¿Qué operaciones cambian el estado? | Transiciones, precondiciones y postcondiciones |

**Trampa frecuente:** `List<Objeto>`, `new Objeto()` y `final` no demuestran composición. Dos colecciones Java pueden referenciar **el mismo objeto**. UML expresa también las restricciones que la implementación deberá garantizar. Ver [UML_GUIDE.md](../../UML_GUIDE.md).

### Ejemplo mínimo en Java 21

Este extracto protege una propiedad, no implementa un patrón GoF:

```java
enum EstadoPuerta { ABIERTA, CERRADA, BLOQUEADA }

final class Puerta {
    private EstadoPuerta estado = EstadoPuerta.CERRADA;

    void abrir() {
        if (estado == EstadoPuerta.BLOQUEADA) {
            throw new IllegalStateException("La puerta está bloqueada");
        }
        estado = EstadoPuerta.ABIERTA;
    }

    boolean sePuedeAtravesar() {
        return estado == EstadoPuerta.ABIERTA;
    }
}
```

La regla «solo es transitable una puerta abierta» no nace del `enum`: **la decidimos en el dominio**. La clase mantiene ese estado y el método comprueba la condición.

## 5. Cómo relacionar el dominio con los patrones de diseño

Los patrones **no son propiedades del mundo**. Son alternativas de organización del código para necesidades particulares.

**Ejemplo: puerta con tres estados**

- Regla del dominio: una puerta BLOQUEADA no se atraviesa, y se desbloquea con credencial válida.
- Necesidad técnica: evitar un bloque extenso de condiciones que crezca al añadir nuevos estados.
- Patrón candidato: **State**. Clases `Puerta`, `EstadoPuerta` y sus estados concretos.
- Evidencia: las pruebas de abrir/desbloquear/atravesar producen los mismos resultados válidos que exige el contrato.
- **Alternativa legítima:** un `enum` y métodos sencillos podrían ser mejores si solo existen tres estados y poca variación. No se impone State por obligación del dominio.

**Ejemplo: diferentes movimientos de una criatura**

- Regla del dominio: una criatura activa tiene como máximo un comportamiento de movimiento seleccionado en un instante.
- Necesidad técnica: intercambiar algoritmos de patrulla/persecución sin duplicar la criatura.
- Patrón candidato: **Strategy**.
- Alternativa: una sola implementación directa si no existe variación relevante.

Para cada patrón debe documentarse: **problema de diseño → reglas afectadas → colaboraciones → clase cliente → UML → comportamiento observable → pruebas → qué mejora frente a una alternativa**.

## 6. Plantilla reutilizable de regla

```text
ID: R-XXX-01
Nombre:
Ámbito:
Enunciado (qué es cierto):
Formalización (opcional):
Ejemplo válido:
Contraejemplo:
Operaciones afectadas:
Política de error:
Relaciones y multiplicidades UML:
Prueba(s) que la verifican:
Origen: supuesto propio / referente visual / requisito del proyecto
```

No todas las reglas necesitan símbolos matemáticos. Un texto claro, preciso y comprobable es preferible a una fórmula decorativa.

## 7. Proceso de construcción y revisión del dominio

1. Delimita el propósito y lo que **no** vas a modelar.
2. Define el vocabulario y evita sinónimos confusos.
3. Separa afirmaciones propias de elementos tomados de referencias culturales.
4. Escribe 5–15 reglas fundamentales con IDs estables; revisa ejemplos y contraejemplos.
5. Define eventos y transiciones válidas; decide qué sucede ante un intento inválido.
6. Identifica entidades, objetos-valor, servicios y asociaciones; decide multiplicidades.
7. Convierte cada regla verificable en una o varias pruebas.
8. Evalúa dónde un patrón mejora una implementación, sin cambiar las reglas.
9. Si aparece una contradicción, corrige **primero** el contrato o documenta una excepción local.
10. Versiona las decisiones: nunca cambies silenciosamente una regla «escrita en piedra».

### Criterios de terminado

Un dominio está suficientemente definido para comenzar una demo cuando podemos responder: **qué objetos existen, quién puede referenciar a quién, qué está permitido, qué está prohibido, qué cambia, qué permanece verdadero y cómo se prueba**. No requiere modelar todos los detalles de una ficción.

## 8. Aplicación al repositorio

- [Expedición Liminal — DOMINIO.md](../../colecciones/liminal/DOMINIO.md): reglas concretas del mundo ficticio.
- [Expedición Liminal — PLAN.md](../../colecciones/liminal/PLAN.md): mapeo de patrones, archivos y controles de calidad.
- [Guía UML](../../UML_GUIDE.md): notación y distinción entre referencias Java y relaciones semánticas.

**Criterio editorial:** nombres del dominio en español sencillo; los nombres oficiales de patrones GoF conservan su denominación; pocas explicaciones dentro del código, explicación extensa en Markdown y UML.
