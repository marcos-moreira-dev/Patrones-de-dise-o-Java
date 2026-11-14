package liminal.comportamiento.command;

import java.io.PrintStream;
import java.util.Objects;

/** Prueba independiente del recorrido pedagógico. */
public final class PruebaCommand {
    private PruebaCommand() {}

    public static void ejecutar() {
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
}
