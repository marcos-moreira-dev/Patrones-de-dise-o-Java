package liminal.estructurales.composite;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

final class GrupoLuces implements Encendible {
    private final String nombre;
    // Un grupo mantiene referencias compartibles, no propiedad física exclusiva:
    // Composite (GoF) no implica automáticamente composición UML.
    private final List<Encendible> hijos = new ArrayList<>();

    GrupoLuces(String nombre) {
        this.nombre = Objects.requireNonNull(nombre);
        if (nombre.isBlank()) throw new IllegalArgumentException("Nombre del grupo vacío");
    }
    String nombre() { return nombre; }
    List<Encendible> hijos() { return List.copyOf(hijos); }

    void agregar(Encendible objeto) {
        Objects.requireNonNull(objeto);
        if (objeto == this || hijos.contains(objeto)
                || (objeto instanceof GrupoLuces grupo && grupo.contieneGrupo(this, new HashSet<>()))) {
            throw new IllegalArgumentException("Duplicado o ciclo no permitido");
        }
        hijos.add(objeto);
    }

    private boolean contieneGrupo(GrupoLuces buscado, Set<GrupoLuces> visitados) {
        if (this == buscado) return true;
        if (!visitados.add(this)) return false;
        for (Encendible hijo : hijos) {
            if (hijo instanceof GrupoLuces g && g.contieneGrupo(buscado, visitados)) return true;
        }
        return false;
    }
    public void encender() { for (Encendible hijo : hijos) hijo.encender(); }
    public void apagar() { for (Encendible hijo : hijos) hijo.apagar(); }
}
