package liminal.comportamiento.visitor;

final class VisitanteConteo implements Visitante {
    private int cantidad;
    public void visitar(Puerta puerta) { cantidad++; }
    public void visitar(Luz luz) { cantidad++; }
    public void visitar(Linterna linterna) { cantidad++; }
    int cantidad() { return cantidad; }
}
