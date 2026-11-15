package liminal.estructurales.decorator;

final class LenteDeAlcance extends DecoradorLinterna {
    private final int extra;
    LenteDeAlcance(Linterna base, int extra) {
        super(base);
        if (extra < 0) throw new IllegalArgumentException("Mejora negativa");
        this.extra = extra;
    }
    public int alcance() { return Math.addExact(super.alcance(), extra); }
    public String descripcion() { return super.descripcion() + " con lente"; }
}
