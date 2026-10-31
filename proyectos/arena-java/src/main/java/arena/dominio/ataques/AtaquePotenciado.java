package arena.dominio.ataques;

/** Añade fuerza a cualquier ataque sin modificar su implementación original. */
public final class AtaquePotenciado extends AtaqueDecorado {
    private final int adicional;

    public AtaquePotenciado(Ataque original, int adicional) {
        super(original);
        if (adicional < 0) {
            throw new IllegalArgumentException("La bonificacion no puede ser negativa");
        }
        this.adicional = adicional;
    }

    @Override
    public int danio() {
        return Math.addExact(original().danio(), adicional);
    }

    @Override
    public String descripcion() {
        return original().descripcion() + " potenciado";
    }
}
