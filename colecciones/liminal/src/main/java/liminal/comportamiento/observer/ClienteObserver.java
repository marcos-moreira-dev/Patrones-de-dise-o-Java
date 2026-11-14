package liminal.comportamiento.observer;

import java.io.PrintStream;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/** Observer: un interruptor notifica a luz y alarma. */
public final class ClienteObserver {
    private ClienteObserver() {}

    public static void ejecutar(PrintStream out) {
        Interruptor interruptor = new Interruptor("I-01");
        Luz luz = new Luz("L-01");
        Alarma alarma = new Alarma("A-01");
        interruptor.suscribir(luz);
        interruptor.suscribir(alarma);
        interruptor.pulsar();
        out.println("Pulsar -> " + (interruptor.activado() ? "ENCENDIDO" : "APAGADO"));
        out.println("Luz -> " + (luz.encendida() ? "ENCENDIDA" : "APAGADA"));
        out.println("Avisos de alarma -> " + alarma.avisos());
        interruptor.pulsar();
        out.println("Volver a pulsar -> " + (interruptor.activado() ? "ENCENDIDO" : "APAGADO"));
        out.println("Luz -> " + (luz.encendida() ? "ENCENDIDA" : "APAGADA"));
    }

    public static void main(String[] args) { ejecutar(System.out); }
}

interface ObservadorInterruptor {
    String id();
    void alCambiar(boolean activo);
}

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
        // Copia estable si alguien modifica suscripciones durante una notificación.
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

final class Luz implements ObservadorInterruptor {
    private final String id;
    private boolean encendida;
    Luz(String id) { this.id = Interruptor.validarId(id); }
    public String id() { return id; }
    public void alCambiar(boolean activo) { encendida = activo; }
    boolean encendida() { return encendida; }
}

final class Alarma implements ObservadorInterruptor {
    private final String id;
    private int avisos;
    Alarma(String id) { this.id = Interruptor.validarId(id); }
    public String id() { return id; }
    public void alCambiar(boolean activo) { if (activo) avisos++; }
    int avisos() { return avisos; }
}
