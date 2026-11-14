package liminal.comportamiento.command;

import java.io.PrintStream;
import java.util.Objects;


final class EncenderLuz implements Comando {
    private final Luz luz;
    EncenderLuz(Luz luz) { this.luz = Objects.requireNonNull(luz); }
    public String ejecutar() {
        luz.encender();
        return "Luz " + luz.id() + ": ENCENDIDA";
    }
}
