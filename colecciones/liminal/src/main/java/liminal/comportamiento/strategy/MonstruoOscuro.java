package liminal.comportamiento.strategy;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;



final class MonstruoOscuro {
    private final String id;
    private final MapaLiminal mapa;
    private String ubicacion;
    private EstrategiaMovimiento estrategia;

    MonstruoOscuro(String id, MapaLiminal mapa, String ubicacion, EstrategiaMovimiento estrategia) {
        this.id = Objects.requireNonNull(id);
        if (id.isBlank()) throw new IllegalArgumentException("ID vacío");
        this.mapa = Objects.requireNonNull(mapa);
        mapa.exigirHabitacion(ubicacion);
        this.ubicacion = ubicacion;
        this.estrategia = Objects.requireNonNull(estrategia);
    }
    String id() { return id; }
    String ubicacion() { return ubicacion; }
    void cambiarEstrategia(EstrategiaMovimiento nueva) {
        estrategia = Objects.requireNonNull(nueva, "Estrategia obligatoria");
    }
    void avanzar(String objetivo) {
        mapa.exigirHabitacion(objetivo);
        String destino = estrategia.elegirDestino(mapa, ubicacion, objetivo);
        if (!mapa.contiene(destino) ||
            (!ubicacion.equals(destino) && !mapa.conecta(ubicacion, destino))) {
            throw new IllegalStateException("La estrategia propuso un destino inválido");
        }
        ubicacion = destino;
    }
}
