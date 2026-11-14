package liminal.creacionales.factorymethod;

import java.io.PrintStream;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/** Factory Method: la clase creadora delega el tipo de criatura. */
public final class ClienteFactoryMethod {
    private ClienteFactoryMethod() {}

    public static void ejecutar(PrintStream out) {
        RegistroApariciones registro = new RegistroApariciones(Set.of("H-01", "H-02"));
        GeneradorCriatura oscuro = new GeneradorOscuro();
        GeneradorCriatura silencioso = new GeneradorSilencioso();
        Criatura a = oscuro.aparecer("M-01", "H-01", registro);
        Criatura b = silencioso.aparecer("M-02", "H-02", registro);
        out.println(a.nombre() + " en " + a.habitacion());
        out.println(b.nombre() + " en " + b.habitacion());
    }





    public static void main(String[] args) { ejecutar(System.out); }
}
