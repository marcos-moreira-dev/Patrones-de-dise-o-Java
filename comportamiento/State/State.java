package comportamiento.State;

/**
 * PATRÓN: State
 *
 * INTERFAZ: State
 *
 * ROL EN EL PATRÓN:
 *  - Declara la interfaz común para todos los estados concretos.
 *  - Cada estado define su propio comportamiento cuando el Contexto lo usa.
 *
 * PROPÓSITO:
 *  Permitir que un objeto cambie su comportamiento cuando cambia su estado interno.
 *  Es útil para evitar condicionales masivos (switch o if/else) que controlan el flujo.
 *
 * CRITERIOS DE USO:
 *  ✅ Cuando un objeto tiene varios estados y su comportamiento varía según el estado.
 *  ✅ Cuando se busca eliminar condicionales repetitivos.
 *
 * ⚠️ Evitarlo cuando:
 *  - Hay pocos estados y no cambian en tiempo de ejecución (es más simple usar condicionales).
 *
 * VENTAJAS:
 *  + Desacopla los estados del contexto.
 *  + Facilita agregar nuevos estados.
 *
 * DESVENTAJAS:
 *  - Aumenta el número de clases.
 *
 * EJEMPLO EN BACKEND:
 *  - Ciclo de vida de una orden: pendiente, procesada, entregada.
 *
 * EJEMPLO EN PIZZERÍA ONLINE:
 *  - Pedido pasa de “Preparando” → “En reparto” → “Entregado”.
 *
 * USO EN CLASE CLIENTE:
 *  Context pedido = new Context();
 *  pedido.request(); // estado A
 *  pedido.request(); // cambia a estado B
 *
 * SALIDA ESPERADA:
 *  Estado actual: A
 *  Cambiando a estado B
 *  Estado actual: B
 *  Cambiando a estado A
 */
public interface State {
    /**
     * Atiende una solicitud segun el estado actual y puede cambiar el estado futuro.
     * Un semaforo es una analogia: cada estado determina su proximo comportamiento.
     * Esta demo usa estados A/B para aislar el mecanismo del patron.
     *
     * @param context objeto cuyo comportamiento depende del estado
     */
    void handle(Context context);
}
