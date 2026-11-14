package liminal.comportamiento.chainofresponsibility.variantes.pipeline;

import java.util.List;

/** Una salida sin puerta es transitable conforme a R-04. */
final class ValidarPuerta extends ValidadorCruce {
    @Override
    protected void comprobar(SolicitudCruce s, List<String> errores) {
        if (s.haySalida() && s.tienePuerta() && !s.puertaAbierta()) {
            errores.add("PUERTA_CERRADA");
        }
    }
}
