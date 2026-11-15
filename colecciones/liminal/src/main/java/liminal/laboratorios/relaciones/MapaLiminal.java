package liminal.laboratorios.relaciones;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

/** El mapa conoce habitaciones, pero no se declara dueño de su ciclo de vida. */
final class MapaLiminal {
    private final String id;
    private final Map<String, Habitacion> habitaciones = new LinkedHashMap<>();

    MapaLiminal(String id) { this.id = Reglas.exigirTexto(id); }
    String id() { return id; }

    void agregarHabitacion(Habitacion habitacion) {
        Objects.requireNonNull(habitacion);
        if (habitaciones.putIfAbsent(habitacion.id(), habitacion) != null) {
            throw new IllegalArgumentException("ID de habitación duplicado");
        }
    }

    Habitacion buscar(String idHabitacion) {
        Habitacion habitacion = habitaciones.get(idHabitacion);
        if (habitacion == null) throw new IllegalArgumentException("Habitación fuera del mapa");
        return habitacion;
    }
    int cantidadHabitaciones() { return habitaciones.size(); }
}
