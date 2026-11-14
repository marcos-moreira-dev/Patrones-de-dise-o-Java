package liminal.estructurales.adapter;

/** API vieja que no implementa el contrato nuevo. */
final class RadioAntigua {
    private String ultimaEmision = "";
    void emitirPorFrecuencia(String mensaje) { ultimaEmision = "Radio antigua: " + mensaje; }
    String ultimaEmision() { return ultimaEmision; }
}
