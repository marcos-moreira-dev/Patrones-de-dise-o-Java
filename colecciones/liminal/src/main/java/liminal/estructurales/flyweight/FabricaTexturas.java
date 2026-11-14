package liminal.estructurales.flyweight;

import java.io.PrintStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;


final class FabricaTexturas {
    private final Map<ClaveTextura, Textura> texturas = new HashMap<>();
    Textura obtener(String material, String color) {
        Textura propuesta = new Textura(material, color);
        return texturas.computeIfAbsent(new ClaveTextura(material, color), clave -> propuesta);
    }
    int cantidad() { return texturas.size(); }
}
