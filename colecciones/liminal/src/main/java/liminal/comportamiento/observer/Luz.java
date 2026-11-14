package liminal.comportamiento.observer;

import java.io.PrintStream;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;



final class Luz implements ObservadorInterruptor {
    private final String id;
    private boolean encendida;
    Luz(String id) { this.id = Interruptor.validarId(id); }
    public String id() { return id; }
    public void alCambiar(boolean activo) { encendida = activo; }
    boolean encendida() { return encendida; }
}
