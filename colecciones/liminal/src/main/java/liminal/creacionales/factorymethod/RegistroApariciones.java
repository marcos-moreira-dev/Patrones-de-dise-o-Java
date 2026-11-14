package liminal.creacionales.factorymethod;

import java.io.PrintStream;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;



final class RegistroApariciones {
    private final Set<String> habitaciones;
    private final Set<String> ids = new HashSet<>();

    RegistroApariciones(Set<String> habitaciones) {
        this.habitaciones = Set.copyOf(habitaciones);
    }
    void verificar(String id, String habitacion) {
        Objects.requireNonNull(id);
        if (id.isBlank() || ids.contains(id)) throw new IllegalArgumentException("ID inválido o repetido");
        if (!habitaciones.contains(habitacion)) throw new IllegalArgumentException("Habitación inexistente");
    }
    void registrar(Criatura criatura) {
        verificar(criatura.id(), criatura.habitacion());
        ids.add(criatura.id());
    }
    int cantidad() { return ids.size(); }
}
