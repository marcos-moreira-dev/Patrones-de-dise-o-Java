package liminal.comportamiento.command.variantes.deshacer;

import java.util.Objects;

/** Reglas de transición R-05; revisión para rechazar deshacer obsoleto. */
final class Puerta {
    private final String id;
    private EstadoPuerta estado = EstadoPuerta.CERRADA;
    private int revision;

    Puerta(String id) {
        this.id = Objects.requireNonNull(id);
        if (id.isBlank()) throw new IllegalArgumentException("ID vacío");
    }

    String id() { return id; }
    EstadoPuerta estado() { return estado; }
    int revision() { return revision; }

    void abrir() {
        if (estado != EstadoPuerta.CERRADA) throw new IllegalStateException("Puerta no cerrada");
        estado = EstadoPuerta.ABIERTA;
        revision++;
    }

    void cerrar() {
        if (estado != EstadoPuerta.ABIERTA) throw new IllegalStateException("Puerta no abierta");
        estado = EstadoPuerta.CERRADA;
        revision++;
    }

    void bloquear() {
        if (estado != EstadoPuerta.CERRADA) throw new IllegalStateException("Puerta no cerrada");
        estado = EstadoPuerta.BLOQUEADA;
        revision++;
    }
}
