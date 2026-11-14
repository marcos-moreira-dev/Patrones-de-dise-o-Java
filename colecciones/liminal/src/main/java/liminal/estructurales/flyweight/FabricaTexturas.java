package liminal.estructurales.flyweight;

import java.util.HashMap;
import java.util.Map;

final class FabricaTexturas {
    private final Map<ClaveTextura, Textura> texturas = new HashMap<>();
    Textura obtener(String material, String color) {
        ClaveTextura clave = new ClaveTextura(material, color);
        return texturas.computeIfAbsent(clave,
                valor -> new Textura(valor.material(), valor.color()));
    }
    int cantidad() { return texturas.size(); }
}
