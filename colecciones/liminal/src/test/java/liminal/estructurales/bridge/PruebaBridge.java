package liminal.estructurales.bridge;

/** Pruebas del patrón, fuera del cliente pedagógico. */
public final class PruebaBridge {
    private PruebaBridge() {}

    public static void ejecutar() {
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
}
