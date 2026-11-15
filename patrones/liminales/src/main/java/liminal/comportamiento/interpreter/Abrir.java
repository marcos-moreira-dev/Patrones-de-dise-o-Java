package liminal.comportamiento.interpreter;

import java.util.Objects;

/** Expresión terminal GoF, modelada como clase explícita. */
final class Abrir implements Expresion {
    private final String id;

    Abrir(String id) { this.id = Objects.requireNonNull(id); }

    public String interpretar(Contexto contexto) {
        Puerta puerta = contexto.puerta(id);
        puerta.abrir();
        return "Puerta " + id + " ABIERTA";
    }
}
