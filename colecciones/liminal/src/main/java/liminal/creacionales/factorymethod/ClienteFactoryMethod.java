package liminal.creacionales.factorymethod;

import java.io.PrintStream;
import java.util.Set;

/** Factory Method: cada generador elige el tipo concreto de criatura. */
public final class ClienteFactoryMethod {
    private ClienteFactoryMethod() {}

    public static void main(String[] args) { ejecutar(System.out); }

    public static void ejecutar(PrintStream out) {
        RegistroApariciones registro = prepararHabitaciones();
        generarApariciones(registro, out);
    }

    private static RegistroApariciones prepararHabitaciones() {
        return new RegistroApariciones(Set.of("H-01", "H-02"));
    }

    private static void generarApariciones(RegistroApariciones registro, PrintStream out) {
        GeneradorCriatura oscuro = new GeneradorOscuro();
        GeneradorCriatura silencioso = new GeneradorSilencioso();

        Criatura a = oscuro.aparecer("M-01", "H-01", registro);
        Criatura b = silencioso.aparecer("M-02", "H-02", registro);
        mostrar(a, out);
        mostrar(b, out);
    }

    private static void mostrar(Criatura criatura, PrintStream out) {
        out.println(criatura.nombre() + " en " + criatura.habitacion());
    }
}
