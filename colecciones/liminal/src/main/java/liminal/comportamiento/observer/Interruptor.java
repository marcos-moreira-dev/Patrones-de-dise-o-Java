package liminal.comportamiento.observer;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

final class Interruptor {
    private final String id;
    private boolean activado;
    private final Map<String, ObservadorInterruptor> observadores = new LinkedHashMap<>();

    Interruptor(String id) { this.id = validarId(id); }
    String id() { return id; }
    boolean activado() { return activado; }
    int cantidadObservadores() { return observadores.size(); }

    void suscribir(ObservadorInterruptor observador) {
        Objects.requireNonNull(observador, "Observador obligatorio");
        String nombre = validarId(observador.id());
        if (observadores.containsKey(nombre)) throw new IllegalArgumentException("ID repetido: " + nombre);
        observadores.put(nombre, observador);
    }
    void desuscribir(String idObservador) { observadores.remove(idObservador); }

    void pulsar() {
        activado = !activado;
        // Se fijan los destinatarios de esta ronda, aunque un callback cambie
        // las suscripciones. Son referencias copiadas, no observadores clonados.
        for (ObservadorInterruptor observador : List.copyOf(observadores.values())) {
            observador.alCambiar(activado);
        }
    }
    static String validarId(String id) {
        Objects.requireNonNull(id, "ID obligatorio");
        if (id.isBlank()) throw new IllegalArgumentException("ID vacío");
        return id;
    }
}
