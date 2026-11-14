package liminal.estructurales.decorator;

import java.io.PrintStream;
import java.util.Objects;


final class FiltroAzul extends DecoradorLinterna {
    FiltroAzul(Linterna base) { super(base); }
    public String descripcion() { return super.descripcion() + " y filtro azul"; }
}
