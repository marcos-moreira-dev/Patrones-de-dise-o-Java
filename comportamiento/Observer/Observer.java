

/**
 * PATRÓN: Observer
 *
 * INTERFAZ: Observer
 *
 * ROL EN EL PATRÓN:
 *  - Define el contrato que deben implementar los observadores para ser notificados.
 *
 * PROPÓSITO:
 *  - Permitir que múltiples objetos (observadores) reaccionen automáticamente
 *    cuando el sujeto (subject) cambie de estado/publique un evento.
 *
 * CRITERIOS DE USO:
 *  ✅ Cuando varios componentes deben enterarse de cambios sin acoplamiento fuerte.
 *  ✅ Cuando quieres desacoplar el emisor (Subject) de los receptores (Observers).
 *  ✅ Cuando el número de receptores puede variar dinámicamente.
 *
 * EJEMPLO BACKEND:
 *  - Sistema de eventos: al crear un pedido se dispara notificación a auditoría, mailing, facturación.
 *
 * EJEMPLO PIZZERÍA:
 *  - “NuevoPedidoCreado”: cocina, repartidor y contabilidad se enteran automáticamente.
 *
 * USO EN CLASE CLIENTE:
 *  Subject subject = new ConcreteSubject();
 *  subject.attach(new ConcreteObserver("Cocina"));
 *  subject.setState("Pedido #101 listo");
 *
 * SALIDA ESPERADA (a nivel de Observer concreto):
 *  - Se imprime un mensaje por cada observador registrado cuando recibe `update(...)`.
 */
public interface Observer {
    void update(String message);
}
