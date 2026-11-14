package liminal.estructurales.bridge;

import java.io.PrintStream;
import java.util.Objects;

/** Bridge: tipo de alerta y forma de comunicarla evolucionan por separado. */
public final class ClienteBridge {
    private ClienteBridge() {}

    public static void ejecutar(PrintStream out) {
        AlertaLiminal puerta = new AlertaPuerta(new CanalRadio());
        AlertaLiminal ruido = new AlertaRuido(new CanalLuz());
        out.println(puerta.emitir());
        out.println(ruido.emitir());
    }

    public static void probar() {
        AlertaLiminal puertaRadio = new AlertaPuerta(new CanalRadio());
        AlertaLiminal puertaLuz = new AlertaPuerta(new CanalLuz());
        AlertaLiminal ruidoRadio = new AlertaRuido(new CanalRadio());
        if (!puertaRadio.emitir().contains("Radio") || !puertaLuz.emitir().contains("Luz")) {
            throw new AssertionError("El canal debe poder cambiar");
        }
        if (!ruidoRadio.emitir().contains("ruido")) {
            throw new AssertionError("El tipo de alerta también debe variar");
        }
    }
    public static void main(String[] args) { ejecutar(System.out); }
}
