package liminal.comportamiento.chainofresponsibility.variantes.pipeline;

import java.util.Objects;
import java.util.Set;

/** Solicitud inmutable: validar no desplaza al explorador. */
final class SolicitudCruce {
    private final Set<String> habitaciones;
    private final String origen;
    private final String destino;
    private final boolean salida;
    private final boolean tienePuerta;
    private final boolean puertaAbierta;

    SolicitudCruce(Set<String> habitaciones, String origen, String destino,
            boolean salida, boolean tienePuerta, boolean puertaAbierta) {
        this.habitaciones = Set.copyOf(Objects.requireNonNull(habitaciones));
        this.origen = Objects.requireNonNull(origen);
        this.destino = Objects.requireNonNull(destino);
        if (origen.isBlank() || destino.isBlank()) {
            throw new IllegalArgumentException("IDs de habitación vacíos");
        }
        if ((!salida && tienePuerta) || (!tienePuerta && puertaAbierta)) {
            throw new IllegalArgumentException("Salida y puerta incoherentes");
        }
        this.salida = salida;
        this.tienePuerta = tienePuerta;
        this.puertaAbierta = puertaAbierta;
    }

    boolean existeOrigen() { return habitaciones.contains(origen); }
    boolean existeDestino() { return habitaciones.contains(destino); }
    boolean haySalida() { return salida; }
    boolean tienePuerta() { return tienePuerta; }
    boolean puertaAbierta() { return puertaAbierta; }
}
