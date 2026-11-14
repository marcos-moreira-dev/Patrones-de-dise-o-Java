package liminal.comportamiento.command;

import java.io.PrintStream;

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

    public static void main(String[] args) { ejecutar(System.out); }
}
