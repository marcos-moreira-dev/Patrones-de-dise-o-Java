package liminal.creacionales.prototype;

import java.util.List;
import java.util.Objects;

/** Entidad copiable: el ID cambia, la configuración no se comparte como objeto mutable. */
final class HabitacionPrototipo implements Prototipo<HabitacionPrototipo> {
    private final String id;
    private final String ambiente;
    private final List<String> objetos;

    HabitacionPrototipo(String id, String ambiente, List<String> objetos) {
        this.id = Objects.requireNonNull(id, "ID requerido");
        this.ambiente = Objects.requireNonNull(ambiente, "Ambiente requerido");
        Objects.requireNonNull(objetos, "Lista de nombres de plantilla requerida");
        if (id.isBlank() || ambiente.isBlank()) {
            throw new IllegalArgumentException("Texto vacío");
        }
        this.objetos = List.copyOf(objetos);
    }

    String id() { return id; }
    String ambiente() { return ambiente; }
    List<String> objetos() { return objetos; }

    public HabitacionPrototipo copiarConId(String nuevoId) {
        Objects.requireNonNull(nuevoId, "Nuevo ID requerido");
        if (nuevoId.isBlank() || id.equals(nuevoId)) {
            throw new IllegalArgumentException("La copia necesita un ID nuevo");
        }
        return new HabitacionPrototipo(nuevoId, ambiente, objetos);
    }
}
