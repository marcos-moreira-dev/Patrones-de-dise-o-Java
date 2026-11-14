package liminal.comportamiento.chainofresponsibility;

final class VerificarSalida extends ValidadorCruce {
    VerificarSalida(ValidadorCruce siguiente) { super(siguiente); }
    protected String detectarRechazo(SolicitudCruce s) {
        return s.salidas().contains(new Ruta(s.origen(), s.destino())) ? null : "SALIDA_INEXISTENTE";
    }
}
