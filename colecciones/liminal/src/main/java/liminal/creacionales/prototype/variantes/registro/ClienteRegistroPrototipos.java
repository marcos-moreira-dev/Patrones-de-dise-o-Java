package liminal.creacionales.prototype.variantes.registro;

import java.io.PrintStream;

/** Elegir una plantilla registrada evita conocer sus clases concretas al crear. */
public final class ClienteRegistroPrototipos {
    private ClienteRegistroPrototipos() {}

    public static void main(String[] args) { ejecutar(System.out); }

    public static void ejecutar(PrintStream out) {
        CatalogoPrototipos catalogo = prepararCatalogo();
        HabitacionGenerada a = catalogo.crear("amarillo", "H-01");
        HabitacionGenerada b = catalogo.crear("piscinas", "H-02");
        mostrar(a, out);
        mostrar(b, out);
    }

    private static CatalogoPrototipos prepararCatalogo() {
        CatalogoPrototipos catalogo = new CatalogoPrototipos();
        catalogo.registrar("amarillo", new PlantillaRegistrada("Pasillo amarillo", 4, true));
        catalogo.registrar("piscinas", new PlantillaRegistrada("Piscina vacía", 2, false));
        return catalogo;
    }

    private static void mostrar(HabitacionGenerada habitacion, PrintStream out) {
        out.println(habitacion.id() + ": " + habitacion.ambiente()
                + " / luces: " + habitacion.luces());
    }
}
