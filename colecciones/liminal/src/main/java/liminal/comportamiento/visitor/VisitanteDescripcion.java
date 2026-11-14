package liminal.comportamiento.visitor;

import java.util.ArrayList;
import java.util.List;

final class VisitanteDescripcion implements Visitante {
    private final List<String> resultados = new ArrayList<>();
    public void visitar(Puerta puerta) { resultados.add("PUERTA " + puerta.id()); }
    public void visitar(Luz luz) { resultados.add("LUZ " + luz.id()); }
    public void visitar(Linterna linterna) { resultados.add("LINTERNA " + linterna.id()); }
    List<String> resultados() { return List.copyOf(resultados); }
}
