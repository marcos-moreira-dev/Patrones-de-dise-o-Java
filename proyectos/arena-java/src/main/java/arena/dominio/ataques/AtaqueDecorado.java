package arena.dominio.ataques;

import java.util.Objects;

/** Decorator base: conserva el mismo contrato y delega en otro ataque. */
public abstract class AtaqueDecorado implements Ataque {
    private final Ataque original;

    protected AtaqueDecorado(Ataque original) {
        this.original = Objects.requireNonNull(original);
    }

    protected final Ataque original() {
        return original;
    }
}
