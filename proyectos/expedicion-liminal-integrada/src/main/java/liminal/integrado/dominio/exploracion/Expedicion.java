package liminal.integrado.dominio.exploracion;

import java.util.Objects;
import liminal.integrado.dominio.mundo.Direccion;
import liminal.integrado.dominio.mundo.Habitacion;
import liminal.integrado.dominio.mundo.MapaLiminal;

/** Sesión de dominio: meta, participante y turnos; sin entrada/salida por consola. */
public final class Expedicion {
    private final String id;
    private final Explorador explorador;
    private final Habitacion meta;
    private int turnos;
    private boolean finalizada;

    public Expedicion(String id, MapaLiminal mapa, Explorador explorador, Habitacion meta) {
        this.id = Objects.requireNonNull(id, "ID obligatorio");
        if (id.isBlank()) throw new IllegalArgumentException("ID vacío");
        Objects.requireNonNull(mapa);
        this.explorador = Objects.requireNonNull(explorador);
        this.meta = Objects.requireNonNull(meta);
        if (!explorador.perteneceA(mapa) || mapa.habitacion(meta.id()) != meta) {
            throw new IllegalArgumentException("Explorador y meta deben pertenecer al mismo mapa");
        }
        this.finalizada = explorador.ubicacion() == meta;
    }

    public String id() { return id; }
    public Explorador explorador() { return explorador; }
    public int turnos() { return turnos; }
    public boolean finalizada() { return finalizada; }

    public Habitacion avanzar(Direccion direccion) {
        Objects.requireNonNull(direccion);
        if (finalizada) throw new IllegalStateException("La expedición ya finalizó");
        // El mapa valida la salida antes de que se modifique la posición.
        Habitacion nueva = explorador.mover(direccion);
        turnos++;
        finalizada = nueva == meta;
        return nueva;
    }
}

