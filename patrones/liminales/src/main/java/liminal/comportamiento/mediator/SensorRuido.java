package liminal.comportamiento.mediator;

import java.util.Objects;

final class SensorRuido {
    private final String id;
    private final Mediador mediador;
    SensorRuido(String id, Mediador mediador) {
        this.id = Objects.requireNonNull(id);
        if (id.isBlank()) throw new IllegalArgumentException("ID vacío");
        this.mediador = Objects.requireNonNull(mediador);
    }
    void detectar() { mediador.notificarRuido(id); }
}
