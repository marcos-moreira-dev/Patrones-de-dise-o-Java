package liminal.comportamiento.state;

import java.io.PrintStream;
import java.util.Objects;



final class EstadoCerrada implements EstadoPuerta {
    public String nombre() { return "CERRADA"; }
    public EstadoPuerta abrir() { return new EstadoAbierta(); }
    public EstadoPuerta bloquear() { return new EstadoBloqueada(); }
}
