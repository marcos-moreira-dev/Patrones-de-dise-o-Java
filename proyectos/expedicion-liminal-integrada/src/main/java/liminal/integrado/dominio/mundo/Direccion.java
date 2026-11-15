package liminal.integrado.dominio.mundo;

import java.util.Objects;

/** Direcciones absolutas; no modelamos todavía la orientación del personaje. */
public enum Direccion {
    NORTE(0, -1), SUR(0, 1), ESTE(1, 0), OESTE(-1, 0);

    private final int dx;
    private final int dy;

    Direccion(int dx, int dy) {
        this.dx = dx;
        this.dy = dy;
    }

    static Direccion entre(Posicion origen, Posicion destino) {
        Objects.requireNonNull(origen);
        Objects.requireNonNull(destino);
        for (Direccion direccion : values()) {
            if ((long) destino.x() - origen.x() == direccion.dx
                    && (long) destino.y() - origen.y() == direccion.dy) {
                return direccion;
            }
        }
        throw new IllegalArgumentException("La salida debe unir celdas vecinas ortogonales");
    }
}

