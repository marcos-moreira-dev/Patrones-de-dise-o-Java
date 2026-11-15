package comportamiento.Observer;

import java.util.ArrayList;
import java.util.List;

/**
 * PATRÓN: Observer
 *
 * INTERFAZ/CLASE BASE: Subject
 *
 * ROL EN EL PATRÓN:
 *  - Mantiene una lista de observadores y ofrece operaciones para suscribirse,
 *    desuscribirse y notificar cambios.
 *
 * PROPÓSITO:
 *  - Centralizar la publicación de eventos/cambios de estado hacia los observadores.
 *
 * CRITERIOS DE USO:
 *  ✅ Cuando el emisor no debe conocer detalles de los receptores.
 *  ✅ Cuando la cantidad de receptores cambia en tiempo de ejecución.
 *
 * USO EN CLASE CLIENTE:
 *  Subject s = new ConcreteSubject();
 *  s.attach(new ConcreteObserver("A"));
 *  s.notifyObservers("Hola");
 *
 * SALIDA ESPERADA:
 *  [A] recibió: Hola
 */
public abstract class Subject {
    protected final List<Observer> observers = new ArrayList<>();

    /**
     * Suscribe un observador a futuras notificaciones.
     *
     * @param o observador que recibira eventos
     */
    public void attach(Observer o) { observers.add(o); }

    /**
     * Retira un observador de las notificaciones futuras.
     *
     * @param o observador que dejara de recibir eventos
     */
    public void detach(Observer o) { observers.remove(o); }

    /**
     * Distribuye el mensaje a los observadores registrados, de forma sincrona.
     *
     * @param message informacion del cambio producido
     */
    protected void notifyObservers(String message) {
        for (Observer o : observers) o.update(message);
    }
}
