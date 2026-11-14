package liminal.comportamiento.chainofresponsibility;

final class VerificarPuerta extends ValidadorCruce {
    VerificarPuerta(ValidadorCruce siguiente) { super(siguiente); }
    protected String detectarRechazo(SolicitudCruce s) {
        Ruta ruta = new Ruta(s.origen(), s.destino());
        if (!s.puertas().contains(ruta)) return null;
        return s.abiertas().contains(ruta) ? null : "PUERTA_CERRADA";
    }
}
