package liminal.comportamiento.command.variantes.deshacer;

import java.io.PrintStream;

/** Recorrido por ejecución, deshacer y rehacer de una puerta. */
public final class ClienteDeshacer {
    private ClienteDeshacer() {}

    public static void main(String[] args) { ejecutar(System.out); }

    public static void ejecutar(PrintStream out) {
        Puerta puerta = new Puerta("P-01");
        HistorialComandos historial = new HistorialComandos();
        out.println("INICIO: " + puerta.estado());
        historial.ejecutar(new AbrirPuerta(puerta));
        out.println("EJECUTAR: " + puerta.estado());
        historial.deshacer();
        out.println("DESHACER: " + puerta.estado());
        historial.rehacer();
        out.println("REHACER: " + puerta.estado());
    }
}
