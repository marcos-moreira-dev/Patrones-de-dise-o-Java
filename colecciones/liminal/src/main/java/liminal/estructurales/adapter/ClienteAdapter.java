package liminal.estructurales.adapter;

import java.io.PrintStream;
import java.util.Objects;

/** Adapter: convertir un aparato antiguo al contrato de aviso actual. */
public final class ClienteAdapter {
    private ClienteAdapter() {}

    public static void ejecutar(PrintStream out) {
        RadioAntigua radio = new RadioAntigua();
        Comunicador equipo = new AdaptadorRadioAntigua(radio);
        equipo.enviar("Explorador en H-01");
        out.println(radio.ultimaEmision());
    }

    public static void probar() {
        RadioAntigua radio = new RadioAntigua();
        Comunicador equipo = new AdaptadorRadioAntigua(radio);
        equipo.enviar("Luz encendida");
        if (!radio.ultimaEmision().equals("Radio antigua: Luz encendida")) {
            throw new AssertionError("Adapter debe traducir enviar a emitir");
        }
        try {
            equipo.enviar(" ");
            throw new AssertionError("Debe rechazar mensajes vacíos");
        } catch (IllegalArgumentException esperado) {
            if (!radio.ultimaEmision().equals("Radio antigua: Luz encendida")) {
                throw new AssertionError("No debe mutar ante error");
            }
        }
    }
    public static void main(String[] args) { ejecutar(System.out); }
}

interface Comunicador {
    void enviar(String aviso);
}

/** API vieja que no implementa el contrato nuevo. */
final class RadioAntigua {
    private String ultimaEmision = "";
    void emitirPorFrecuencia(String mensaje) { ultimaEmision = "Radio antigua: " + mensaje; }
    String ultimaEmision() { return ultimaEmision; }
}

final class AdaptadorRadioAntigua implements Comunicador {
    private final RadioAntigua radio;

    AdaptadorRadioAntigua(RadioAntigua radio) { this.radio = Objects.requireNonNull(radio); }
    public void enviar(String aviso) {
        Objects.requireNonNull(aviso);
        if (aviso.isBlank()) throw new IllegalArgumentException("Aviso vacío");
        radio.emitirPorFrecuencia(aviso);
    }
}
