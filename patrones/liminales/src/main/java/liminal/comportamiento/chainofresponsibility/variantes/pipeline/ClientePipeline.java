package liminal.comportamiento.chainofresponsibility.variantes.pipeline;

import java.io.PrintStream;
import java.util.List;
import java.util.Set;

/** La cadena produce todos los fallos relevantes de una misma solicitud. */
public final class ClientePipeline {
    private ClientePipeline() {}

    public static void main(String[] args) { ejecutar(System.out); }

    public static void ejecutar(PrintStream out) {
        ValidadorCruce cadena = prepararCadena();
        Set<String> mapa = Set.of("H-01", "H-02");
        mostrar(cadena, "INVALIDA", new SolicitudCruce(mapa, "H-99", "H-02",
                false, false, false), out);
        mostrar(cadena, "SIN PUERTA", new SolicitudCruce(mapa, "H-01", "H-02",
                true, false, false), out);
        mostrar(cadena, "PUERTA CERRADA", new SolicitudCruce(mapa, "H-01", "H-02",
                true, true, false), out);
    }

    private static ValidadorCruce prepararCadena() {
        ValidadorCruce habitaciones = new ValidarHabitaciones();
        ValidadorCruce salida = new ValidarSalida();
        ValidadorCruce puerta = new ValidarPuerta();
        habitaciones.enlazar(salida);
        salida.enlazar(puerta);
        return habitaciones;
    }

    private static void mostrar(
            ValidadorCruce cadena, String nombre, SolicitudCruce s, PrintStream out) {
        List<String> errores = cadena.verificar(s);
        out.println(nombre + ": " + (errores.isEmpty() ? "PERMITIDO" : String.join(", ", errores)));
    }
}
