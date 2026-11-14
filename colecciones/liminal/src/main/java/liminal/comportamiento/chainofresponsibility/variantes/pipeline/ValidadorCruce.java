package liminal.comportamiento.chainofresponsibility.variantes.pipeline;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/** Cadena que visita todos los validadores sin detenerse en el primer fallo. */
abstract class ValidadorCruce {
    private ValidadorCruce siguiente;

    final void enlazar(ValidadorCruce nuevoSiguiente) {
        Objects.requireNonNull(nuevoSiguiente);
        if (nuevoSiguiente == this || siguiente != null) {
            throw new IllegalArgumentException("Siguiente inválido");
        }
        siguiente = nuevoSiguiente;
    }

    final List<String> verificar(SolicitudCruce solicitud) {
        Objects.requireNonNull(solicitud);
        List<String> errores = new ArrayList<>();
        revisarEnCadena(solicitud, errores);
        return List.copyOf(errores);
    }

    private void revisarEnCadena(SolicitudCruce solicitud, List<String> errores) {
        comprobar(solicitud, errores);
        if (siguiente != null) siguiente.revisarEnCadena(solicitud, errores);
    }

    protected abstract void comprobar(SolicitudCruce solicitud, List<String> errores);
}
