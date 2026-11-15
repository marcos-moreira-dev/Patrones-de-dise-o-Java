package liminal.comportamiento.chainofresponsibility.variantes.pipeline;

import java.util.List;

final class ValidarSalida extends ValidadorCruce {
    @Override
    protected void comprobar(SolicitudCruce s, List<String> errores) {
        if (!s.haySalida()) errores.add("SIN_SALIDA");
    }
}
