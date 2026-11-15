# Facade — Atravesar un pasillo con seguridad

![Pasillo amarillo con alfombra, paredes repetidas y luces fluorescentes](../../../assets/ambientes/pasillo-amarillo.webp)

*Referencia ambiental de esta escena. El contrato válido se define en [DOMINIO.md](../../../DOMINIO.md), no en la imagen.*

![UML de Facade: una fachada coordina explorador, mapa y puerta](../../uml/facade.svg)

**Escena:** un explorador desea pasar de H-01 a H-02. Para ello debe comprobar que ambas habitaciones existen, que hay una **salida dirigida** y que, **si existe una puerta**, está abierta.

**Dominio:** R-02, R-03, R-04, R-05 y R-12. Un movimiento rechazado **no cambia** la habitación actual. En **este ejercicio** `PuertaPaso` solo necesita los estados `CERRADA` y `ABIERTA`: no hay mecanismo de cerradura ni operación de bloqueo. El tercer estado permitido por R-05, `BLOQUEADA`, se implementa y practica en **[State](../comportamiento/state.md)**; declararlo aquí sin una forma de alcanzarlo distraería del objetivo de Facade.

**Problema:** si cada cliente repite las validaciones del mapa, puerta y explorador, es fácil olvidar una condición.

**Patrón:** `FachadaCruce` ofrece `cruzar(destino)` y coordina `Explorador` y `MapaLocal`. El mapa distingue una salida inexistente de una salida sin puerta; esta última puede atravesarse. La fachada no añade excepciones al dominio ni teletransportación.

**Integridad R-03:** el explorador conserva una referencia al mapa en el que fue creado; la fachada exige **esa misma instancia de mapa**, aunque otro tenga habitaciones con los mismos IDs. Así se evita un cruce artificial entre expediciones con mapas distintos. La validación de identidad de referencia ocurre al construir la fachada, sin mover al explorador.

**Java y UML:** la fachada y el explorador guardan referencias a objetos que **utilizan** para verificar el cruce; son asociaciones, no relaciones de propiedad compuesta. El mapa guarda referencias a objetos `Salida` identificados por `Ruta`; cada `Salida` puede tener **cero o una** `PuertaPaso`. Por eso el diagrama muestra `MapaLocal → Salida → PuertaPaso`, en lugar de fingir que el mapa contiene directamente una puerta. El método solo actualiza la ubicación **al final** de todas las comprobaciones.

**Consola:** `Inicio: H-01` seguido de `Cruce correcto: H-02`.

**Pruebas:** puerta cerrada, destino inexistente, mapa ajeno y ausencia de salida de regreso son rechazos sin movimiento; el cruce con puerta abierta funciona **y una salida sin puerta también permite avanzar**.

**Alternativa:** con un solo método sin coordinación adicional, la fachada puede ser innecesaria.

[Ver cliente y código](../../../src/main/java/liminal/estructurales/facade/ClienteFacade.java).

**Explorar los archivos por separado:** [clases Java](../../../src/main/java/liminal/estructurales/facade/) · [PruebaFacade.java](../../../src/test/java/liminal/estructurales/facade/PruebaFacade.java) · [guía de lectura del código](../../../GUIA_LECTURA_CODIGO.md).

## Otras implementaciones (sin código extra)

**Una fachada o fachadas por subsistema:** agrupar recorridos relacionados reduce acoplamiento, pero una fachada gigante para todos los objetos liminales escondería el dominio. Mantener métodos con responsabilidades acotadas.
