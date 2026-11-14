package liminal.comportamiento.chainofresponsibility;

import java.io.PrintStream;
import java.util.Set;

/** Chain of Responsibility: las comprobaciones de un cruce forman una cadena. */
public final class ClienteChainOfResponsibility {
    private ClienteChainOfResponsibility() {}

    public static void ejecutar(PrintStream out) {
        ValidadorCruce cadena = new VerificarDestino(
                new VerificarSalida(new VerificarPuerta(null)));
        SolicitudCruce cerrado = escena(false);
        SolicitudCruce abierto = escena(true);
        out.println("Puerta cerrada -> " + cadena.verificar(cerrado));
        out.println("Puerta abierta -> " + cadena.verificar(abierto));
    }

    public static void main(String[] args) { ejecutar(System.out); }

    private static SolicitudCruce escena(boolean abierta) {
        Ruta ruta = new Ruta("H-01", "H-02");
        return new SolicitudCruce(
                Set.of("H-01", "H-02"), Set.of(ruta), Set.of(ruta),
                abierta ? Set.of(ruta) : Set.of(), "H-01", "H-02");
    }
}
