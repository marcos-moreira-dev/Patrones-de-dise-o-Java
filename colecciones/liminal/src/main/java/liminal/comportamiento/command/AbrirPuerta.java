package liminal.comportamiento.command;

import java.io.PrintStream;
import java.util.Objects;


final class AbrirPuerta implements Comando {
    private final Puerta puerta;
    AbrirPuerta(Puerta puerta) { this.puerta = Objects.requireNonNull(puerta); }
    public String ejecutar() {
        puerta.abrir();
        return "Puerta " + puerta.id() + ": ABIERTA";
    }
}
