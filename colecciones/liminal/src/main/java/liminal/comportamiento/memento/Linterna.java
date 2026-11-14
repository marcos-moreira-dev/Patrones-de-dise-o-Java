package liminal.comportamiento.memento;

import java.util.Objects;

final class Linterna {
    private int intensidad;
    private boolean filtroAzul;

    Linterna(int intensidad, boolean filtroAzul) { configurar(intensidad, filtroAzul); }
    int intensidad() { return intensidad; }
    boolean filtroAzul() { return filtroAzul; }

    void configurar(int intensidadNueva, boolean filtroNuevo) {
        if (intensidadNueva < 0 || intensidadNueva > 10) {
            throw new IllegalArgumentException("Intensidad fuera de 0..10");
        }
        intensidad = intensidadNueva;
        filtroAzul = filtroNuevo;
    }
    String descripcion() { return "intensidad " + intensidad + ", filtro azul " + filtroAzul; }

    Recuerdo guardarEstado() { return new Recuerdo(intensidad, filtroAzul); }
    void restaurar(Recuerdo recuerdo) {
        Objects.requireNonNull(recuerdo);
        configurar(recuerdo.intensidad, recuerdo.filtroAzul);
    }

    /** Solo Linterna conoce la estructura de la instantánea. */
    static final class Recuerdo {
        private final int intensidad;
        private final boolean filtroAzul;
        private Recuerdo(int intensidad, boolean filtroAzul) {
            this.intensidad = intensidad;
            this.filtroAzul = filtroAzul;
        }
    }
}
