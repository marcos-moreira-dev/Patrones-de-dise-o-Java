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
        PuertaPaso puerta = new PuertaPaso();
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
        PuertaPaso puerta = new PuertaPaso();
        mapa.agregarSalida("H-01", "H-02", puerta);
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
    private EstadoPaso estado = EstadoPaso.CERRADA;
    EstadoPaso estado() { return estado; }
    void abrir() {
        if (estado != EstadoPaso.CERRADA) throw new IllegalStateException("No puede abrirse");
        estado = EstadoPaso.ABIERTA;
    }
    boolean transitable() { return estado == EstadoPaso.ABIERTA; }
}
record Ruta(String origen, String destino) {}

final class MapaLocal {
    private final Set<String> habitaciones = new HashSet<>();
    private final Map<Ruta, PuertaPaso> salidas = new HashMap<>();

    void agregarHabitacion(String id) {
        validarId(id);
        if (!habitaciones.add(id)) throw new IllegalArgumentException("Habitación duplicada");
    }
    void agregarSalida(String origen, String destino, PuertaPaso puerta) {
        if (!habitaciones.contains(origen) || !habitaciones.contains(destino)) {
            throw new IllegalArgumentException("Habitación inexistente");
        }
        Objects.requireNonNull(puerta);
        if (salidas.putIfAbsent(new Ruta(origen, destino), puerta) != null) {
            throw new IllegalArgumentException("Salida duplicada");
        }
    }
    boolean contiene(String id) { return habitaciones.contains(id); }
    PuertaPaso puerta(String origen, String destino) { return salidas.get(new Ruta(origen, destino)); }

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
        PuertaPaso puerta = mapa.puerta(explorador.ubicacion(), destino);
        if (puerta == null) throw new IllegalArgumentException("No existe salida dirigida");
        if (!puerta.transitable()) throw new IllegalStateException("La puerta no está abierta");
        explorador.cambiarUbicacion(destino);
    }
}
