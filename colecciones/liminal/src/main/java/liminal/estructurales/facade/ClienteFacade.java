package liminal.estructurales.facade;

import java.io.PrintStream;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/** Facade: ofrece un único método para cruzar una salida segura. */
public final class ClienteFacade {
    private ClienteFacade() {}

    public static void ejecutar(PrintStream out) {
        MapaLocal mapa = new MapaLocal();
        mapa.agregarHabitacion("H-01");
        mapa.agregarHabitacion("H-02");
        PuertaPaso puerta = new PuertaPaso("P-01");
        mapa.agregarSalida("H-01", "H-02", puerta);
        Explorador explorador = new Explorador("E-01", "H-01", mapa);
        FachadaCruce fachada = new FachadaCruce(explorador, mapa);
        out.println("Inicio: " + explorador.ubicacion());
        puerta.abrir();
        fachada.cruzar("H-02");
        out.println("Cruce correcto: " + explorador.ubicacion());
    }

    public static void probar() {
        MapaLocal mapa = new MapaLocal();
        mapa.agregarHabitacion("H-01");
        mapa.agregarHabitacion("H-02");
        mapa.agregarHabitacion("H-03");
        mapa.agregarHabitacion("H-04");
        PuertaPaso puerta = new PuertaPaso("P-01");
        mapa.agregarSalida("H-01", "H-02", puerta);
        mapa.agregarSalida("H-02", "H-03", null); // Pasillo sin puerta.
        Explorador explorador = new Explorador("E-01", "H-01", mapa);
        FachadaCruce fachada = new FachadaCruce(explorador, mapa);
        esperarFallo(() -> fachada.cruzar("H-02"));
        if (!explorador.ubicacion().equals("H-01")) throw new AssertionError("La puerta cerrada no permite pasar");
        puerta.abrir();
        esperarFallo(() -> fachada.cruzar("H-03"));
        if (!explorador.ubicacion().equals("H-01")) throw new AssertionError("Salida inexistente no cambia ubicación");
        fachada.cruzar("H-02");
        if (!explorador.ubicacion().equals("H-02")) throw new AssertionError("No se cruzó la salida");
        esperarFallo(() -> fachada.cruzar("H-01"));
        if (!explorador.ubicacion().equals("H-02")) throw new AssertionError("Las salidas son dirigidas");
        fachada.cruzar("H-03");
        if (!explorador.ubicacion().equals("H-03")) {
            throw new AssertionError("R-04: una salida sin puerta debe ser transitable");
        }
        esperarFallo(() -> fachada.cruzar("H-04")); // Habitación existente, sin salida.
        if (!explorador.ubicacion().equals("H-03")) {
            throw new AssertionError("Un cruce inválido no debe mover al explorador");
        }
        esperarFallo(() -> new PuertaPaso(" "));
        if (!explorador.ubicacion().equals("H-03")) {
            throw new AssertionError("R-01: el rechazo de puerta sin ID no altera la ubicación");
        }
    }

    private static void esperarFallo(Runnable accion) {
        try { accion.run(); }
        catch (IllegalArgumentException | IllegalStateException esperado) { return; }
        throw new AssertionError("Cruce inválido aceptado");
    }
    public static void main(String[] args) { ejecutar(System.out); }
}

enum EstadoPaso { CERRADA, ABIERTA, BLOQUEADA }
final class PuertaPaso {
    private final String id;
    private EstadoPaso estado = EstadoPaso.CERRADA;
    PuertaPaso(String id) {
        this.id = Objects.requireNonNull(id);
        if (id.isBlank()) throw new IllegalArgumentException("ID de puerta vacío");
    }
    String id() { return id; }
    EstadoPaso estado() { return estado; }
    void abrir() {
        if (estado != EstadoPaso.CERRADA) throw new IllegalStateException("No puede abrirse");
        estado = EstadoPaso.ABIERTA;
    }
    boolean transitable() { return estado == EstadoPaso.ABIERTA; }
}
record Ruta(String origen, String destino) {}
record Salida(PuertaPaso puerta) {} // null significa que esta salida no tiene puerta.

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
final class Explorador {
    private final String id;
    private String ubicacion;
    Explorador(String id, String ubicacion, MapaLocal mapa) {
        this.id = Objects.requireNonNull(id);
        if (id.isBlank() || !Objects.requireNonNull(mapa).contiene(ubicacion)) {
            throw new IllegalArgumentException("Explorador fuera del mapa");
        }
        this.ubicacion = ubicacion;
    }
    String ubicacion() { return ubicacion; }
    void cambiarUbicacion(String destino) { ubicacion = destino; }
}
final class FachadaCruce {
    private final Explorador explorador;
    private final MapaLocal mapa;

    FachadaCruce(Explorador explorador, MapaLocal mapa) {
        this.explorador = Objects.requireNonNull(explorador);
        this.mapa = Objects.requireNonNull(mapa);
    }
    void cruzar(String destino) {
        if (!mapa.contiene(destino)) throw new IllegalArgumentException("Destino inexistente");
        Salida salida = mapa.salida(explorador.ubicacion(), destino);
        if (salida == null) throw new IllegalArgumentException("No existe salida dirigida");
        if (salida.puerta() != null && !salida.puerta().transitable()) {
            throw new IllegalStateException("La puerta no está abierta");
        }
        explorador.cambiarUbicacion(destino);
    }
}
