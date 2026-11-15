package liminal.comportamiento.visitor;

interface Visitante {
    void visitar(Puerta puerta);
    void visitar(Luz luz);
    void visitar(Linterna linterna);
}
