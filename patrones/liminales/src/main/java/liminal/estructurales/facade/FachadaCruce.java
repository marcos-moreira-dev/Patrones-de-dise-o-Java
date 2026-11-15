package liminal.estructurales.facade;

import java.util.Objects;

final class FachadaCruce {
    private final Explorador explorador;
    private final MapaLocal mapa;

    FachadaCruce(Explorador explorador, MapaLocal mapa) {
        this.explorador = Objects.requireNonNull(explorador);
        this.mapa = Objects.requireNonNull(mapa);
        if (!explorador.perteneceA(mapa)) {
            throw new IllegalArgumentException("El explorador pertenece a otro mapa");
        }
    }
    // La fachada coordina; el mapa conoce las salidas y el explorador su ubicación.
    // Solo se cambia la ubicación después de validar todas las precondiciones.
    void cruzar(String destino) {
        if (!mapa.contiene(destino)) throw new IllegalArgumentException("Destino inexistente");
        Salida salida = mapa.salida(explorador.ubicacion(), destino);
        if (salida == null) throw new IllegalArgumentException("No existe salida dirigida");
        if (salida.puerta() != null && !salida.puerta().transitable()) {
            throw new IllegalStateException("La puerta no está abierta");
        }
        explorador.cambiarUbicacion(destino);
    }
}
