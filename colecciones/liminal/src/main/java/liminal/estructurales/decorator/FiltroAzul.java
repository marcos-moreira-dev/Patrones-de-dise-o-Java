package liminal.estructurales.decorator;

final class FiltroAzul extends DecoradorLinterna {
    FiltroAzul(Linterna base) { super(base); }
    public String descripcion() { return super.descripcion() + " y filtro azul"; }
}
