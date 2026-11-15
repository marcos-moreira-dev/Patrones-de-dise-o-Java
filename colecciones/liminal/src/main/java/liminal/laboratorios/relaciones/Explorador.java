package liminal.laboratorios.relaciones;

import java.util.Objects;

/** Asociación: ubicación actual en una única habitación registrada. */
final class Explorador {
    private final String id;
    private final MapaLiminal mapa;
    private final Habitacion ubicacion;

    Explorador(String id, MapaLiminal mapa, String idHabitacion) {
        this.id = Reglas.exigirTexto(id);
        this.mapa = Objects.requireNonNull(mapa);
        this.ubicacion = mapa.buscar(Objects.requireNonNull(idHabitacion, "ID requerido"));
    }
    String id() { return id; }
    MapaLiminal mapa() { return mapa; }
    Habitacion ubicacion() { return ubicacion; }
}
