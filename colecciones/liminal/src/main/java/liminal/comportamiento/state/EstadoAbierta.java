package liminal.comportamiento.state;

final class EstadoAbierta implements EstadoPuerta {
    public String nombre() { return "ABIERTA"; }
    public boolean transitable() { return true; }
    public EstadoPuerta cerrar() { return new EstadoCerrada(); }
}
