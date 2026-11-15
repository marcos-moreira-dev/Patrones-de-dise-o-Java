package liminal.estructurales.facade;

import java.io.PrintStream;

/** Facade: el cliente atraviesa una salida sin coordinar todos los subsistemas. */
public final class ClienteFacade {
    private ClienteFacade() {}

    public static void main(String[] args) { ejecutar(System.out); }

    public static void ejecutar(PrintStream out) {
        PuertaPaso puerta = new PuertaPaso("P-01");
        MapaLocal mapa = prepararMapa(puerta);
        Explorador explorador = new Explorador("E-01", "H-01", mapa);
        FachadaCruce fachada = new FachadaCruce(explorador, mapa);
        demostrarCruce(puerta, fachada, explorador, out);
    }

    private static MapaLocal prepararMapa(PuertaPaso puerta) {
        MapaLocal mapa = new MapaLocal();
        mapa.agregarHabitacion("H-01");
        mapa.agregarHabitacion("H-02");
        mapa.agregarSalida("H-01", "H-02", puerta);
        return mapa;
    }

    private static void demostrarCruce(
            PuertaPaso puerta, FachadaCruce fachada, Explorador explorador, PrintStream out) {
        out.println("Inicio: " + explorador.ubicacion());
        puerta.abrir();
        fachada.cruzar("H-02");
        out.println("Cruce correcto: " + explorador.ubicacion());
    }
}
