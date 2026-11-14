package liminal.estructurales.facade;

import java.io.PrintStream;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;


final class FachadaCruce {
    private final Explorador explorador;
    private final MapaLocal mapa;

    FachadaCruce(Explorador explorador, MapaLocal mapa) {
        this.explorador = Objects.requireNonNull(explorador);
        this.mapa = Objects.requireNonNull(mapa);
    }
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
