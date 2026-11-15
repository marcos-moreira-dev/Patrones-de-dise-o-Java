package liminal.creacionales.prototype.variantes.registro;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/** Registro de prototipos; evita reutilizar IDs activos de sus creaciones. */
final class CatalogoPrototipos {
    private final Map<String, PrototipoRegistrable> plantillas = new HashMap<>();
    private final Set<String> idsGenerados = new HashSet<>();

    void registrar(String nombre, PrototipoRegistrable plantilla) {
        Objects.requireNonNull(nombre, "Nombre requerido");
        Objects.requireNonNull(plantilla, "Plantilla requerida");
        if (nombre.isBlank()) throw new IllegalArgumentException("Nombre vacío");
        if (plantillas.putIfAbsent(nombre, plantilla) != null) {
            throw new IllegalArgumentException("Nombre de plantilla duplicado");
        }
    }

    HabitacionGenerada crear(String nombre, String idNuevo) {
        Objects.requireNonNull(nombre, "Nombre requerido");
        Objects.requireNonNull(idNuevo, "ID requerido");
        if (idNuevo.isBlank()) throw new IllegalArgumentException("ID vacío");
        PrototipoRegistrable plantilla = plantillas.get(nombre);
        if (plantilla == null) throw new IllegalArgumentException("Plantilla desconocida");
        if (idsGenerados.contains(idNuevo)) throw new IllegalArgumentException("ID ya asignado");
        HabitacionGenerada resultado = Objects.requireNonNull(plantilla.crearConId(idNuevo));
        if (!idNuevo.equals(resultado.id())) {
            throw new IllegalStateException("El prototipo no respetó el nuevo ID");
        }
        idsGenerados.add(idNuevo);
        return resultado;
    }

    int cantidadPlantillas() { return plantillas.size(); }
    int cantidadCreadas() { return idsGenerados.size(); }
}
