package liminal.creacionales.prototype;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


record HabitacionPrototipo(String id, String ambiente, List<String> objetos)
        implements Prototipo<HabitacionPrototipo> {
    HabitacionPrototipo {
        Objects.requireNonNull(id);
        Objects.requireNonNull(ambiente);
        Objects.requireNonNull(objetos);
        if (id.isBlank() || ambiente.isBlank()) throw new IllegalArgumentException("Texto vacío");
        objetos = List.copyOf(objetos);
    }

    public HabitacionPrototipo copiarConId(String nuevoId) {
        Objects.requireNonNull(nuevoId);
        if (nuevoId.isBlank() || id.equals(nuevoId)) {
            throw new IllegalArgumentException("La copia necesita un ID nuevo");
        }
        return new HabitacionPrototipo(nuevoId, ambiente, objetos);
    }
}
