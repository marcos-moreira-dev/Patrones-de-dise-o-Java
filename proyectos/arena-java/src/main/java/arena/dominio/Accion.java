package arena.dominio;

/** Acciones posibles de un turno; su disponibilidad depende del escenario. */
public enum Accion {
    ATACAR(0),
    DEFENDER(0),
    ESPECIAL(3),
    SALTAR(0),
    HUIR(0);

    private final int costeEnergia;

    Accion(int costeEnergia) {
        this.costeEnergia = costeEnergia;
    }

    /** @return energía necesaria para solicitar esta acción */
    public int costeEnergia() {
        return costeEnergia;
    }
}
