package liminal.comportamiento.state;

import java.io.PrintStream;
import java.util.Objects;


final class EstadoBloqueada implements EstadoPuerta {
    public String nombre() { return "BLOQUEADA"; }
    public EstadoPuerta desbloquear(String llave, String esperada) {
        if (!llave.equals(esperada)) throw new IllegalArgumentException("Llave no autorizada");
        return new EstadoCerrada();
    }
}
