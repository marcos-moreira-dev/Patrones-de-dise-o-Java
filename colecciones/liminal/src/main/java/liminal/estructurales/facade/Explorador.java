package liminal.estructurales.facade;

import java.util.Objects;

final class Explorador {
    private final String id;
    private String ubicacion;
    Explorador(String id, String ubicacion, MapaLocal mapa) {
        this.id = Objects.requireNonNull(id);
        if (id.isBlank() || !Objects.requireNonNull(mapa).contiene(ubicacion)) {
            throw new IllegalArgumentException("Explorador fuera del mapa");
        }
        this.ubicacion = ubicacion;
    }
    String ubicacion() { return ubicacion; }
    void cambiarUbicacion(String destino) { ubicacion = destino; }
}
