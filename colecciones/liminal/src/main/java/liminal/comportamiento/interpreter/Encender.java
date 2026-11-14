package liminal.comportamiento.interpreter;

import java.io.PrintStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;


record Encender(String id) implements Expresion {
    public String interpretar(Contexto contexto) {
        Luz luz = contexto.luz(id);
        luz.encender();
        return "Luz " + id + " ENCENDIDA";
    }
}
