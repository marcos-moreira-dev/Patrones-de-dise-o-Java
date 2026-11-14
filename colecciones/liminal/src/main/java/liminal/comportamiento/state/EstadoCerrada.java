package liminal.comportamiento.state;

final class EstadoCerrada implements EstadoPuerta {
    public String nombre() { return "CERRADA"; }
    public EstadoPuerta abrir() { return new EstadoAbierta(); }
    public EstadoPuerta bloquear() { return new EstadoBloqueada(); }
}
