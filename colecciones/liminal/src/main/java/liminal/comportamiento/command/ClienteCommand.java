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
    }
    public static void main(String[] args) { ejecutar(System.out); }
}

interface Comando {
    String ejecutar();
}
final class Puerta {
    private final String id;
    private boolean abierta;
    Puerta(String id) { this.id = Objects.requireNonNull(id); }
    void abrir() {
        if (abierta) throw new IllegalStateException("La puerta ya está abierta");
        abierta = true;
    }
    boolean abierta() { return abierta; }
    String id() { return id; }
}
final class Luz {
    private final String id;
    private boolean encendida;
    Luz(String id) { this.id = Objects.requireNonNull(id); }
    void encender() { encendida = true; }
    boolean encendida() { return encendida; }
    String id() { return id; }
}
final class AbrirPuerta implements Comando {
    private final Puerta puerta;
    AbrirPuerta(Puerta puerta) { this.puerta = Objects.requireNonNull(puerta); }
    public String ejecutar() {
        puerta.abrir();
        return "Puerta " + puerta.id() + ": ABIERTA";
    }
}
final class EncenderLuz implements Comando {
    private final Luz luz;
    EncenderLuz(Luz luz) { this.luz = Objects.requireNonNull(luz); }
    public String ejecutar() {
        luz.encender();
        return "Luz " + luz.id() + ": ENCENDIDA";
    }
}
final class PanelBotones {
    private Comando comando;
    void asignar(Comando nuevo) { comando = Objects.requireNonNull(nuevo); }
    String pulsar() {
        if (comando == null) throw new IllegalStateException("No hay orden asignada");
        return comando.ejecutar();
    }
}
