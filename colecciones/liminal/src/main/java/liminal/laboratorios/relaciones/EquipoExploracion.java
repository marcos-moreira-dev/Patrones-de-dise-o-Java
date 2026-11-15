package liminal.laboratorios.relaciones;

import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

/** Agregación compartida LOCAL: los exploradores sobreviven a la disolución del equipo. */
final class EquipoExploracion {
    private final String nombre;
    private final Set<Explorador> integrantes = new LinkedHashSet<>();
    private boolean activo = true;

    EquipoExploracion(String nombre) { this.nombre = Reglas.exigirTexto(nombre); }
    String nombre() { return nombre; }
    boolean activo() { return activo; }

    void agregar(Explorador explorador) {
        if (!activo) throw new IllegalStateException("Equipo disuelto");
        Objects.requireNonNull(explorador);
        if (!integrantes.add(explorador)) {
            throw new IllegalArgumentException("Explorador ya incorporado al equipo");
        }
    }
    boolean contiene(Explorador explorador) { return integrantes.contains(explorador); }
    int cantidadIntegrantes() { return integrantes.size(); }

    void disolver() {
        activo = false;
        integrantes.clear();
    }
}
