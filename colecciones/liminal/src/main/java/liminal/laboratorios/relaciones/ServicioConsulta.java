package liminal.laboratorios.relaciones;

import java.util.Objects;

/** Dependencia: recibe un explorador como parámetro, sin retener su referencia. */
final class ServicioConsulta {
    private ServicioConsulta() {}
    static String describirUbicacion(Explorador explorador) {
        Objects.requireNonNull(explorador);
        return explorador.id() + " está en " + explorador.ubicacion().id();
    }
}
