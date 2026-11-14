package liminal.comportamiento.observer;

import java.io.PrintStream;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;



final class Alarma implements ObservadorInterruptor {
    private final String id;
    private int avisos;
    Alarma(String id) { this.id = Interruptor.validarId(id); }
    public String id() { return id; }
    public void alCambiar(boolean activo) { if (activo) avisos++; }
    int avisos() { return avisos; }
}
