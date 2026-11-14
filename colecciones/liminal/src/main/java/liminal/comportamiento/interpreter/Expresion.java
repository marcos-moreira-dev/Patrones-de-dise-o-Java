package liminal.comportamiento.interpreter;

import java.io.PrintStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;



interface Expresion {
    String interpretar(Contexto contexto);
}
