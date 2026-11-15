package liminal.comportamiento.chainofresponsibility;

abstract class ValidadorCruce {
    private final ValidadorCruce siguiente;

    ValidadorCruce(ValidadorCruce siguiente) { this.siguiente = siguiente; }
    // Esta cadena solo valida y se detiene ante el primer rechazo;
    // ejecutar el movimiento pertenece a otra responsabilidad.
    final String verificar(SolicitudCruce solicitud) {
        String rechazo = detectarRechazo(solicitud);
        if (rechazo != null) return rechazo;
        return siguiente == null ? "PERMITIDO" : siguiente.verificar(solicitud);
    }
    protected abstract String detectarRechazo(SolicitudCruce solicitud);
}
