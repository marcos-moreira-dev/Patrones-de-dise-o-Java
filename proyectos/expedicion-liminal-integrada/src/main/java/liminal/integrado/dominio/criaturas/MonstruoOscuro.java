package liminal.integrado.dominio.criaturas;

import java.util.List;
import java.util.Objects;
import liminal.integrado.dominio.mundo.Habitacion;
import liminal.integrado.dominio.mundo.MapaLiminal;

/** Entidad que conserva su identidad y valida los destinos propuestos por Strategy. */
public final class MonstruoOscuro {
    private final String id;
    private final MapaLiminal mapa;
    private Habitacion ubicacion;
    private EstrategiaMovimiento estrategia;

    public MonstruoOscuro(String id, MapaLiminal mapa, Habitacion inicio,
                          EstrategiaMovimiento estrategia) {
        this.id = Objects.requireNonNull(id, "ID obligatorio");
        if (id.isBlank()) throw new IllegalArgumentException("ID vacío");
        this.mapa = Objects.requireNonNull(mapa, "Mapa obligatorio");
        this.ubicacion = Objects.requireNonNull(inicio, "Inicio obligatorio");
        if (mapa.habitacion(inicio.id()) != inicio) {
            throw new IllegalArgumentException("Ubicación de criatura ajena al mapa");
        }
        this.estrategia = Objects.requireNonNull(estrategia, "Estrategia obligatoria");
    }

    public String id() { return id; }
    public Habitacion ubicacion() { return ubicacion; }
    public boolean perteneceA(MapaLiminal otro) { return mapa == otro; }

    public void cambiarEstrategia(EstrategiaMovimiento nueva) {
        estrategia = Objects.requireNonNull(nueva, "Estrategia obligatoria");
    }

    public Habitacion avanzar(Habitacion objetivo) {
        Objects.requireNonNull(objetivo, "Objetivo obligatorio");
        if (mapa.habitacion(objetivo.id()) != objetivo) {
            throw new IllegalArgumentException("Objetivo ajeno al mapa");
        }
        List<Habitacion> opciones = mapa.destinosDesde(ubicacion);
        Habitacion propuesta = estrategia.elegirDestino(ubicacion, objetivo, opciones);
        // Strategy elige, pero esta entidad valida antes de mutar:
        // una implementación defectuosa no puede teletransportar a la criatura.
        if (propuesta != ubicacion && !opciones.contains(propuesta)) {
            throw new IllegalStateException("La estrategia propuso una salida inexistente");
        }
        ubicacion = propuesta;
        return ubicacion;
    }
}
