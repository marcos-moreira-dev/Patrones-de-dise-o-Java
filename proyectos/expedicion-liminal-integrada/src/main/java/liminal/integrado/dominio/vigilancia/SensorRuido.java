package liminal.integrado.dominio.vigilancia;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import liminal.integrado.dominio.mundo.Habitacion;

/** Sujeto de Observer: solo comunica una detección explícita. */
public final class SensorRuido {
    private final String id;
    private final Habitacion habitacionVigilada;
    private final Map<String, ObservadorRuido> observadores = new LinkedHashMap<>();

    public SensorRuido(String id, Habitacion habitacionVigilada) {
        this.id = validar(id);
        this.habitacionVigilada = Objects.requireNonNull(habitacionVigilada);
    }

    public String id() { return id; }
    public boolean vigila(Habitacion ubicacion) { return habitacionVigilada == ubicacion; }
    public int cantidadObservadores() { return observadores.size(); }

    public void suscribir(ObservadorRuido observador) {
        Objects.requireNonNull(observador);
        String idObservador = validar(observador.id());
        if (observadores.containsKey(idObservador)) {
            throw new IllegalArgumentException("Observador duplicado: " + idObservador);
        }
        observadores.put(idObservador, observador);
    }

    public void desuscribir(String idObservador) {
        observadores.remove(validar(idObservador));
    }

    public void detectarMovimiento(Habitacion ubicacion) {
        if (!vigila(Objects.requireNonNull(ubicacion))) {
            throw new IllegalArgumentException("El sensor no vigila esa habitación");
        }
        EventoRuido evento = new EventoRuido(id, ubicacion.id());
        // Instantánea de referencias: cambios de suscripción durante el callback
        // afectan a la siguiente notificación, no a esta ronda.
        for (ObservadorRuido observador : List.copyOf(observadores.values())) {
            observador.alDetectar(evento);
        }
    }

    private static String validar(String texto) {
        Objects.requireNonNull(texto);
        if (texto.isBlank()) throw new IllegalArgumentException("ID vacío");
        return texto;
    }
}
