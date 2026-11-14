package liminal.creacionales.prototype;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/** Prototype: copiar configuración sin duplicar la identidad. */
public final class ClientePrototype {
    private ClientePrototype() {}

    public static void ejecutar(PrintStream out) {
        HabitacionPrototipo original = new HabitacionPrototipo(
                "H-01", "Pasillo amarillo", List.of("Linterna", "Llave"));
        HabitacionPrototipo copia = original.copiarConId("H-02");
        out.println("Original: " + original.id());
        out.println("Copia: " + copia.id());
        out.println("Mismos objetos de plantilla: " + original.objetos().equals(copia.objetos()));
        out.println("¿Misma instancia? " + (original == copia));
    }

    public static void probar() {
        HabitacionPrototipo h1 = new HabitacionPrototipo("H-01", "Piscinas", List.of("Radio"));
        HabitacionPrototipo h2 = h1.copiarConId("H-02");
        if (h1 == h2 || h1.id().equals(h2.id())) throw new AssertionError("Identidades duplicadas");
        if (!h1.ambiente().equals(h2.ambiente()) || !h1.objetos().equals(h2.objetos())) {
            throw new AssertionError("Configuración incorrecta");
        }
        try {
            h1.copiarConId("H-01");
            throw new AssertionError("Debe exigir un ID distinto");
        } catch (IllegalArgumentException esperado) {
            // La plantilla original no cambia.
        }
        List<String> externa = new ArrayList<>();
        externa.add("Cámara");
        HabitacionPrototipo h3 = new HabitacionPrototipo("H-03", "Oficina", externa);
        externa.add("Llave");
        if (h3.objetos().size() != 1) throw new AssertionError("La plantilla debe proteger su lista");
    }

    public static void main(String[] args) { ejecutar(System.out); }
}
