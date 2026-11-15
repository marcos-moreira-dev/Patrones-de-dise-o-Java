package liminal.comportamiento.interpreter;

import java.util.Objects;

/** Otra expresión terminal con una interpretación diferente. */
final class Encender implements Expresion {
    private final String id;

    Encender(String id) { this.id = Objects.requireNonNull(id); }

    public String interpretar(Contexto contexto) {
        Luz luz = contexto.luz(id);
        luz.encender();
        return "Luz " + id + " ENCENDIDA";
    }
}
