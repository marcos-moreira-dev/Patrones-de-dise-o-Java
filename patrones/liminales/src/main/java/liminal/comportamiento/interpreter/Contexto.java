package liminal.comportamiento.interpreter;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

final class Contexto {
    private final Map<String, Puerta> puertas = new HashMap<>();
    private final Map<String, Luz> luces = new HashMap<>();

    void agregarPuerta(String id) {
        validarId(id);
        if (puertas.putIfAbsent(id, new Puerta()) != null) throw new IllegalArgumentException("ID repetido");
    }
    void agregarLuz(String id) {
        validarId(id);
        if (luces.putIfAbsent(id, new Luz()) != null) throw new IllegalArgumentException("ID repetido");
    }
    Puerta puerta(String id) {
        Puerta p = puertas.get(id);
        if (p == null) throw new IllegalArgumentException("Puerta inexistente");
        return p;
    }
    Luz luz(String id) {
        Luz l = luces.get(id);
        if (l == null) throw new IllegalArgumentException("Luz inexistente");
        return l;
    }
    private static void validarId(String id) {
        Objects.requireNonNull(id);
        if (id.isBlank()) throw new IllegalArgumentException("ID vacío");
    }
}
