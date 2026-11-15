package liminal.comportamiento.observer.variantes.pushpull;

import java.util.LinkedHashMap;
import java.util.List;
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
        // La instantanea evita modificar un Map mientras un observador se retira.
        List<ObservadorPush> destinatariosPush = List.copyOf(push.values());
        List<ObservadorPull> destinatariosPull = List.copyOf(pull.values());
        for (ObservadorPush observador : destinatariosPush) observador.actualizar(activado);
        for (ObservadorPull observador : destinatariosPull) observador.notificarCambio(this);
    }
}
