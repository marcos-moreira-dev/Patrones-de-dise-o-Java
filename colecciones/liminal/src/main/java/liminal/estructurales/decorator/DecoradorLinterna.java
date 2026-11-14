package liminal.estructurales.decorator;

import java.util.Objects;

abstract class DecoradorLinterna implements Linterna {
    protected final Linterna envuelta;
    DecoradorLinterna(Linterna envuelta) { this.envuelta = Objects.requireNonNull(envuelta); }
    public int alcance() { return envuelta.alcance(); }
    public String descripcion() { return envuelta.descripcion(); }
}
