package liminal.integrado.dominio.mundo;

import java.util.Objects;

/** Conexión con identidad propia: ir de A a B no habilita automáticamente B a A. */
public final class Salida {
    private final String id;
    private final Habitacion origen;
    private final Habitacion destino;
    private final Direccion direccion;

    Salida(String id, Habitacion origen, Habitacion destino, Direccion direccion) {
        this.id = Objects.requireNonNull(id);
        this.origen = Objects.requireNonNull(origen);
        this.destino = Objects.requireNonNull(destino);
        this.direccion = Objects.requireNonNull(direccion);
    }

    public String id() { return id; }
    public Habitacion origen() { return origen; }
    public Habitacion destino() { return destino; }
    public Direccion direccion() { return direccion; }
}

