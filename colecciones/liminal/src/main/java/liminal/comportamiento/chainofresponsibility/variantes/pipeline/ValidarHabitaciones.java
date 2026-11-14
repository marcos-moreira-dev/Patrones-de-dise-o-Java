package liminal.comportamiento.chainofresponsibility.variantes.pipeline;

import java.util.List;

/** Comprueba pertenencia al mapa sin detener los siguientes validadores. */
final class ValidarHabitaciones extends ValidadorCruce {
    @Override
    protected void comprobar(SolicitudCruce s, List<String> errores) {
        if (!s.existeOrigen()) errores.add("ORIGEN_DESCONOCIDO");
        if (!s.existeDestino()) errores.add("DESTINO_DESCONOCIDO");
    }
}
