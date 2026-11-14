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


    public static void main(String[] args) { ejecutar(System.out); }
}
