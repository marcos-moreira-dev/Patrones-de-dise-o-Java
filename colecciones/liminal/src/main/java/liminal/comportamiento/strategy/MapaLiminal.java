package liminal.comportamiento.strategy;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;



final class MapaLiminal {
    private final Set<String> habitaciones = new LinkedHashSet<>();
    private final Set<String> idsSalidas = new LinkedHashSet<>();
    private final Map<String, Set<String>> vecinos = new LinkedHashMap<>();

    void agregarHabitacion(String id) {
        validarId(id);
        if (!habitaciones.add(id)) throw new IllegalArgumentException("Habitación duplicada");
        vecinos.put(id, new LinkedHashSet<>());
    }
    void agregarSalida(String id, String origen, String destino) {
        validarId(id);
        exigirHabitacion(origen);
        exigirHabitacion(destino);
        if (idsSalidas.contains(id)) throw new IllegalArgumentException("Salida duplicada");
        if (vecinos.get(origen).contains(destino)) throw new IllegalArgumentException("Conexión duplicada");
        idsSalidas.add(id);
        vecinos.get(origen).add(destino);
    }
    boolean contiene(String habitacion) { return habitaciones.contains(habitacion); }
    boolean conecta(String origen, String destino) {
        return vecinos.containsKey(origen) && vecinos.get(origen).contains(destino);
    }
    List<String> destinosDesde(String origen) {
        exigirHabitacion(origen);
        return List.copyOf(new ArrayList<>(vecinos.get(origen)));
    }
    void exigirHabitacion(String nombre) {
        if (!contiene(nombre)) throw new IllegalArgumentException("Habitación inexistente: " + nombre);
    }
    private static void validarId(String id) {
        Objects.requireNonNull(id, "ID obligatorio");
        if (id.isBlank()) throw new IllegalArgumentException("ID vacío");
    }
}
