package liminal.estructurales.bridge;

final class AlertaPuerta extends AlertaLiminal {
    AlertaPuerta(CanalAviso canal) { super(canal); }
    protected String mensaje() { return "puerta bloqueada"; }
}
