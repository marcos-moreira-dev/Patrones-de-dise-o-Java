package liminal.creacionales.builder.variantes.director;

import java.io.PrintStream;

/** Enseña el rol Director que no necesita el Builder fluido original. */
public final class ClienteDirector {
    private ClienteDirector() {}

    public static void main(String[] args) { ejecutar(System.out); }

    public static void ejecutar(PrintStream out) {
        DirectorAmbientes director = new DirectorAmbientes();
        ConstructorEscenario constructor = new ConstructorHabitacion();
        director.construirPasillo("H-01", constructor);
        mostrar(constructor.construir(), out);
        director.construirPiscina("H-02", constructor);
        mostrar(constructor.construir(), out);
    }

    private static void mostrar(Habitacion h, PrintStream out) {
        out.println(h.id() + ": " + h.ambiente()
                + " / luces " + h.luces()
                + " / puerta " + (h.conPuerta() ? "sí" : "no"));
    }
}
