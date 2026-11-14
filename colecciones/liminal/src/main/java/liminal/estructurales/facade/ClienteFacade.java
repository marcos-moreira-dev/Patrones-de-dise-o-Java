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
