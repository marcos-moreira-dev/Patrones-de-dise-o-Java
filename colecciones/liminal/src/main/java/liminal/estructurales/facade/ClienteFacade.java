package liminal.estructurales.facade;

import java.io.PrintStream;

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

    public static void main(String[] args) { ejecutar(System.out); }
}
