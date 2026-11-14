package liminal.comportamiento.observer.variantes.pushpull;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

/** Suscripciones en orden reproducible; sin hilos ni copias de las luces. */
final class Interruptor {
    private final String id;
    private final Map<String, ObservadorPush> push = new LinkedHashMap<>();
    private final Map<String, ObservadorPull> pull = new LinkedHashMap<>();
    private boolean activado;

    Interruptor(String id) {
        this.id = Objects.requireNonNull(id);
        if (id.isBlank()) throw new IllegalArgumentException("ID vacío");
    }

    boolean activado() { return activado; }

    void suscribir(ObservadorPush observador) {
        Objects.requireNonNull(observador);
        validarId(observador.id());
        push.put(observador.id(), observador);
    }

    void suscribir(ObservadorPull observador) {
        Objects.requireNonNull(observador);
        validarId(observador.id());
        pull.put(observador.id(), observador);
    }

    private void validarId(String idObservador) {
        Objects.requireNonNull(idObservador);
        if (idObservador.isBlank() || push.containsKey(idObservador)
                || pull.containsKey(idObservador)) {
            throw new IllegalArgumentException("ID de observador inválido o duplicado");
        }
    }

    void retirar(String idObservador) {
        if (push.remove(idObservador) == null) pull.remove(idObservador);
    }

    void pulsar() {
        activado = !activado;
        for (ObservadorPush observador : push.values()) observador.actualizar(activado);
        for (ObservadorPull observador : pull.values()) observador.notificarCambio(this);
    }
}
