package liminal.comportamiento.command;

import java.io.PrintStream;
import java.util.Objects;

/** Command: un panel ejecuta acciones encapsuladas sobre dispositivos. */
public final class ClienteCommand {
    private ClienteCommand() {}

    public static void ejecutar(PrintStream out) {
        Puerta puerta = new Puerta("P-01");
        Luz luz = new Luz("L-01");
        PanelBotones panel = new PanelBotones();
        panel.asignar(new AbrirPuerta(puerta));
        out.println(panel.pulsar());
        panel.asignar(new EncenderLuz(luz));
        out.println(panel.pulsar());
    }

    public static void probar() {
        Puerta puerta = new Puerta("P-01");
        Luz luz = new Luz("L-01");
        PanelBotones panel = new PanelBotones();
        panel.asignar(new AbrirPuerta(puerta));
        panel.pulsar();
        if (!puerta.abierta()) throw new AssertionError("Debe abrir");
        try {
            panel.pulsar();
            throw new AssertionError("No debe abrir una puerta ya abierta");
        } catch (IllegalStateException esperado) {
            if (!puerta.abierta()) throw new AssertionError("No debe alterar estado ante error");
        }
        panel.asignar(new EncenderLuz(luz));
        panel.pulsar();
        if (!luz.encendida()) throw new AssertionError("Debe encender la luz");
        try {
            new Puerta(" ");
            throw new AssertionError("La puerta necesita un ID");
        } catch (IllegalArgumentException esperado) {
            // Se protege R-01.
        }
        try {
            new Luz("");
            throw new AssertionError("La luz necesita un ID");
        } catch (IllegalArgumentException esperado) {
            // Se protege R-01.
        }
    }
    public static void main(String[] args) { ejecutar(System.out); }
}
