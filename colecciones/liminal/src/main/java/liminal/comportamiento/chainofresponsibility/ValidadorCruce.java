package liminal.comportamiento.chainofresponsibility;

import java.io.PrintStream;
import java.util.Objects;
import java.util.Set;



abstract class ValidadorCruce {
    private final ValidadorCruce siguiente;

    ValidadorCruce(ValidadorCruce siguiente) { this.siguiente = siguiente; }
    final String verificar(SolicitudCruce solicitud) {
        String rechazo = detectarRechazo(solicitud);
        if (rechazo != null) return rechazo;
        return siguiente == null ? "PERMITIDO" : siguiente.verificar(solicitud);
    }
    protected abstract String detectarRechazo(SolicitudCruce solicitud);
}
