package liminal.creacionales.prototype;

import java.io.PrintStream;
import java.util.List;

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

    public static void main(String[] args) { ejecutar(System.out); }
}
