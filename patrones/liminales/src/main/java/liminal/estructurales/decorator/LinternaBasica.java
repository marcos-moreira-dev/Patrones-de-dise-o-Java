package liminal.estructurales.decorator;

/** Componente concreto al que se agregan envolturas Decorator. */
final class LinternaBasica implements Linterna {
    private final int alcance;

    LinternaBasica(int alcance) {
        if (alcance < 0) throw new IllegalArgumentException("Alcance negativo");
        this.alcance = alcance;
    }

    public int alcance() { return alcance; }
    public String descripcion() { return "linterna básica"; }
}
