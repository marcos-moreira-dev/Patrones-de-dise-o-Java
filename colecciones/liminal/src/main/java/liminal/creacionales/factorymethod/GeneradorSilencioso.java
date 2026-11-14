package liminal.creacionales.factorymethod;

import java.io.PrintStream;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;


final class GeneradorSilencioso extends GeneradorCriatura {
    protected Criatura crearCriatura(String id, String habitacion) {
        return new MonstruoSilencioso(id, habitacion);
    }
}
