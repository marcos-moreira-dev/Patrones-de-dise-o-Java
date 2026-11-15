package liminal.estructurales.bridge;

final class AlertaRuido extends AlertaLiminal {
    AlertaRuido(CanalAviso canal) { super(canal); }
    protected String mensaje() { return "ruido en el pasillo"; }
}
