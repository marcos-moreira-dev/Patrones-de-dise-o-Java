package liminal.comportamiento.strategy;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/** Strategy: el mismo monstruo cambia de algoritmo sin cambiar de identidad. */
public final class ClienteStrategy {
    private ClienteStrategy() {}

    public static void ejecutar(PrintStream out) {
        MapaLiminal mapa = new MapaLiminal();
        mapa.agregarHabitacion("Pasillo amarillo");
        mapa.agregarHabitacion("Piscina vacía");
        mapa.agregarSalida("S-01", "Pasillo amarillo", "Piscina vacía");
        mapa.agregarSalida("S-02", "Piscina vacía", "Pasillo amarillo");
        MonstruoOscuro monstruo = new MonstruoOscuro("M-01", mapa, "Pasillo amarillo", new Patrullar());
        out.println("Monstruo oscuro: " + monstruo.ubicacion());
        monstruo.avanzar("Pasillo amarillo");
        out.println("Patrullar -> " + monstruo.ubicacion());
        monstruo.cambiarEstrategia(new Perseguir());
        monstruo.avanzar("Pasillo amarillo");
        out.println("Perseguir -> " + monstruo.ubicacion());
        monstruo.cambiarEstrategia(new Esperar());
        monstruo.avanzar("Piscina vacía");
        out.println("Esperar -> " + monstruo.ubicacion());
    }

    public static void main(String[] args) { ejecutar(System.out); }
}

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

interface EstrategiaMovimiento {
    String elegirDestino(MapaLiminal mapa, String origen, String objetivo);
}
final class Patrullar implements EstrategiaMovimiento {
    public String elegirDestino(MapaLiminal mapa, String origen, String objetivo) {
        List<String> opciones = mapa.destinosDesde(origen);
        return opciones.isEmpty() ? origen : opciones.get(0);
    }
}
final class Perseguir implements EstrategiaMovimiento {
    public String elegirDestino(MapaLiminal mapa, String origen, String objetivo) {
        return mapa.conecta(origen, objetivo) ? objetivo : origen;
    }
}
final class Esperar implements EstrategiaMovimiento {
    public String elegirDestino(MapaLiminal mapa, String origen, String objetivo) { return origen; }
}

final class MonstruoOscuro {
    private final String id;
    private final MapaLiminal mapa;
    private String ubicacion;
    private EstrategiaMovimiento estrategia;

    MonstruoOscuro(String id, MapaLiminal mapa, String ubicacion, EstrategiaMovimiento estrategia) {
        this.id = Objects.requireNonNull(id);
        if (id.isBlank()) throw new IllegalArgumentException("ID vacío");
        this.mapa = Objects.requireNonNull(mapa);
        mapa.exigirHabitacion(ubicacion);
        this.ubicacion = ubicacion;
        this.estrategia = Objects.requireNonNull(estrategia);
    }
    String id() { return id; }
    String ubicacion() { return ubicacion; }
    void cambiarEstrategia(EstrategiaMovimiento nueva) {
        estrategia = Objects.requireNonNull(nueva, "Estrategia obligatoria");
    }
    void avanzar(String objetivo) {
        mapa.exigirHabitacion(objetivo);
        String destino = estrategia.elegirDestino(mapa, ubicacion, objetivo);
        if (!mapa.contiene(destino) ||
            (!ubicacion.equals(destino) && !mapa.conecta(ubicacion, destino))) {
            throw new IllegalStateException("La estrategia propuso un destino inválido");
        }
        ubicacion = destino;
    }
}
