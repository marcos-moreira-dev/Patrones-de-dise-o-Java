package liminal.comportamiento.chainofresponsibility;

final class VerificarDestino extends ValidadorCruce {
    VerificarDestino(ValidadorCruce siguiente) { super(siguiente); }
    protected String detectarRechazo(SolicitudCruce s) {
        if (!s.habitaciones().contains(s.origen())) return "ORIGEN_INEXISTENTE";
        return s.habitaciones().contains(s.destino()) ? null : "DESTINO_INEXISTENTE";
    }
}
