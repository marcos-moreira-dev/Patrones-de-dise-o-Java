package liminal.comportamiento.observer;

import java.io.PrintStream;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;



interface ObservadorInterruptor {
    String id();
    void alCambiar(boolean activo);
}
