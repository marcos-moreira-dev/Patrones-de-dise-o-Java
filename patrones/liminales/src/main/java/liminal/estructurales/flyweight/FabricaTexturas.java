package liminal.estructurales.flyweight;

import java.util.HashMap;
import java.util.Map;

final class FabricaTexturas {
    // ClaveTextura compara valores y permite compartir Textura inmutable;
    // la identidad de cada pared permanece fuera de esta caché.
    private final Map<ClaveTextura, Textura> texturas = new HashMap<>();
    Textura obtener(String material, String color) {
        ClaveTextura clave = new ClaveTextura(material, color);
        return texturas.computeIfAbsent(clave,
                valor -> new Textura(valor.material(), valor.color()));
    }
    int cantidad() { return texturas.size(); }
}
