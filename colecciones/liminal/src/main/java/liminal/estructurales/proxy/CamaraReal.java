package liminal.estructurales.proxy;

import java.io.PrintStream;
import java.util.Objects;


final class CamaraReal implements Camara {
    private final String habitacion;
    CamaraReal(String habitacion) { this.habitacion = Objects.requireNonNull(habitacion); }
    public String observar(String pase) { return "Vista: " + habitacion; }
}
