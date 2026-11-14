package liminal.comportamiento.interpreter;

import java.io.PrintStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;



record Abrir(String id) implements Expresion {
    public String interpretar(Contexto contexto) {
        Puerta puerta = contexto.puerta(id);
        puerta.abrir();
        return "Puerta " + id + " ABIERTA";
    }
}
