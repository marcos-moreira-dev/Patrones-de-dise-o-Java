package liminal.estructurales.flyweight;

import java.io.PrintStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/** Flyweight: muchas paredes comparten una textura inmutable. */
public final class ClienteFlyweight {
    private ClienteFlyweight() {}

    public static void ejecutar(PrintStream out) {
        FabricaTexturas fabrica = new FabricaTexturas();
        Pared a = new Pared("M-01", fabrica.obtener("papel", "amarillo"));
        Pared b = new Pared("M-02", fabrica.obtener("papel", "amarillo"));
        out.println("Paredes distintas: " + (a != b));
        out.println("Textura compartida: " + (a.textura() == b.textura()));
        out.println("Texturas almacenadas: " + fabrica.cantidad());
    }

    public static void probar() {
        FabricaTexturas fabrica = new FabricaTexturas();
        Pared uno = new Pared("M-01", fabrica.obtener("papel", "amarillo"));
        Pared dos = new Pared("M-02", fabrica.obtener("papel", "amarillo"));
        Pared tres = new Pared("M-03", fabrica.obtener("azulejo", "azul"));
        if (uno == dos || uno.textura() != dos.textura()) {
            throw new AssertionError("Instancias de pared distintas, textura idéntica");
        }
        if (uno.textura() == tres.textura() || fabrica.cantidad() != 2) {
            throw new AssertionError("Textura nueva para configuración diferente");
        }
        try {
            fabrica.obtener("", "verde");
            throw new AssertionError("Debe rechazar descripción vacía");
        } catch (IllegalArgumentException esperado) {
            if (fabrica.cantidad() != 2) throw new AssertionError("No debe mutar ante error");
        }
    }
    public static void main(String[] args) { ejecutar(System.out); }
}

record Textura(String material, String color) {
    Textura {
        Objects.requireNonNull(material);
        Objects.requireNonNull(color);
        if (material.isBlank() || color.isBlank()) throw new IllegalArgumentException("Textura inválida");
    }
}
record ClaveTextura(String material, String color) {}
final class FabricaTexturas {
    private final Map<ClaveTextura, Textura> texturas = new HashMap<>();
    Textura obtener(String material, String color) {
        Textura propuesta = new Textura(material, color);
        return texturas.computeIfAbsent(new ClaveTextura(material, color), clave -> propuesta);
    }
    int cantidad() { return texturas.size(); }
}
record Pared(String id, Textura textura) {
    Pared {
        Objects.requireNonNull(id);
        Objects.requireNonNull(textura);
        if (id.isBlank()) throw new IllegalArgumentException("ID vacío");
    }
}
