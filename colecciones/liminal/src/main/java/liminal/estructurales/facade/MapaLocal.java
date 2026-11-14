package liminal.estructurales.facade;

import java.io.PrintStream;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

 // null significa que esta salida no tiene puerta.

final class MapaLocal {
    private final Set<String> habitaciones = new HashSet<>();
    private final Map<Ruta, Salida> salidas = new HashMap<>();

    void agregarHabitacion(String id) {
        validarId(id);
        if (!habitaciones.add(id)) throw new IllegalArgumentException("Habitación duplicada");
    }
    void agregarSalida(String origen, String destino, PuertaPaso puerta) {
        if (!habitaciones.contains(origen) || !habitaciones.contains(destino)) {
            throw new IllegalArgumentException("Habitación inexistente");
        }
        // La puerta es opcional: una salida sin puerta se puede atravesar.
        if (salidas.putIfAbsent(new Ruta(origen, destino), new Salida(puerta)) != null) {
            throw new IllegalArgumentException("Salida duplicada");
        }
    }
    boolean contiene(String id) { return habitaciones.contains(id); }
    Salida salida(String origen, String destino) { return salidas.get(new Ruta(origen, destino)); }

    private static void validarId(String id) {
        Objects.requireNonNull(id);
        if (id.isBlank()) throw new IllegalArgumentException("ID vacío");
    }
}
