package liminal.integrado.dominio.mundo;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/** Responsable de las celdas, identidades y salidas dirigidas, no de los turnos. */
public final class MapaLiminal {
    private final String id;
    private final int ancho;
    private final int alto;
    private final Map<String, Habitacion> habitacionesPorId = new LinkedHashMap<>();
    private final Map<Posicion, Habitacion> habitacionesPorPosicion = new HashMap<>();
    private final Map<String, Salida> salidasPorId = new LinkedHashMap<>();
    private final Map<String, EnumMap<Direccion, Salida>> salidasPorOrigen = new HashMap<>();

    public MapaLiminal(String id, int ancho, int alto) {
        this.id = validarId(id);
        if (ancho <= 0 || alto <= 0) {
            throw new IllegalArgumentException("Dimensiones inválidas del mapa");
        }
        this.ancho = ancho;
        this.alto = alto;
    }

    public String id() { return id; }
    public int cantidadHabitaciones() { return habitacionesPorId.size(); }
    public int cantidadSalidas() { return salidasPorId.size(); }

    public Habitacion agregarHabitacion(String id, String nombre, Posicion posicion) {
        validarId(id);
        Objects.requireNonNull(nombre);
        Objects.requireNonNull(posicion);
        if (nombre.isBlank() || posicion.x() >= ancho || posicion.y() >= alto
                || habitacionesPorId.containsKey(id)
                || habitacionesPorPosicion.containsKey(posicion)) {
            throw new IllegalArgumentException("Habitación inválida, duplicada o fuera del mapa");
        }
        Habitacion nueva = new Habitacion(id, nombre, posicion);
        habitacionesPorId.put(id, nueva);
        habitacionesPorPosicion.put(posicion, nueva);
        return nueva;
    }

    public Salida agregarSalida(String id, String origenId, String destinoId) {
        validarId(id);
        validarId(origenId);
        validarId(destinoId);
        Habitacion origen = habitacionesPorId.get(origenId);
        Habitacion destino = habitacionesPorId.get(destinoId);
        if (origen == null || destino == null || salidasPorId.containsKey(id)) {
            throw new IllegalArgumentException("Salida inválida o duplicada");
        }
        Direccion direccion = Direccion.entre(origen.posicion(), destino.posicion());
        Map<Direccion, Salida> existentes = salidasPorOrigen.get(origenId);
        if (existentes != null && existentes.containsKey(direccion)) {
            throw new IllegalArgumentException("Ya existe una salida en esa dirección");
        }
        // Se validan ambos índices antes de mutarlos: un rechazo no registra
        // parcialmente una conexión (R-12).
        Salida nueva = new Salida(id, origen, destino, direccion);
        salidasPorId.put(id, nueva);
        salidasPorOrigen.computeIfAbsent(origenId,
                clave -> new EnumMap<>(Direccion.class)).put(direccion, nueva);
        return nueva;
    }

    public Habitacion habitacion(String id) {
        Habitacion habitacion = habitacionesPorId.get(validarId(id));
        if (habitacion == null) throw new IllegalArgumentException("Habitación inexistente");
        return habitacion;
    }

    public Habitacion habitacion(Posicion posicion) {
        Habitacion habitacion = habitacionesPorPosicion.get(
                Objects.requireNonNull(posicion));
        if (habitacion == null) throw new IllegalArgumentException("Celda sin habitación");
        return habitacion;
    }

    public List<Habitacion> destinosDesde(Habitacion origen) {
        Objects.requireNonNull(origen);
        if (habitacionesPorId.get(origen.id()) != origen) {
            throw new IllegalArgumentException("La habitación pertenece a otro mapa");
        }
        Map<Direccion, Salida> opciones = salidasPorOrigen.get(origen.id());
        // EnumMap ofrece un orden estable; toList() devuelve una instantánea
        // no modificable y no permite que una Strategy altere las salidas.
        return opciones == null ? List.of()
                : opciones.values().stream().map(Salida::destino).toList();
    }

    public Salida salidaDesde(Habitacion origen, Direccion direccion) {
        Objects.requireNonNull(origen);
        Objects.requireNonNull(direccion);
        if (habitacionesPorId.get(origen.id()) != origen) {
            throw new IllegalArgumentException("La habitación pertenece a otro mapa");
        }
        Map<Direccion, Salida> opciones = salidasPorOrigen.get(origen.id());
        Salida salida = opciones == null ? null : opciones.get(direccion);
        if (salida == null) {
            throw new IllegalStateException("No hay salida dirigida hacia " + direccion);
        }
        return salida;
    }

    private static String validarId(String id) {
        Objects.requireNonNull(id, "ID obligatorio");
        if (id.isBlank()) throw new IllegalArgumentException("ID vacío");
        return id;
    }
}

