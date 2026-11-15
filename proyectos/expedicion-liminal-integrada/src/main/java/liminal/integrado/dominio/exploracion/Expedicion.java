package liminal.integrado.dominio.exploracion;

import java.util.Objects;
import liminal.integrado.dominio.criaturas.MonstruoOscuro;
import liminal.integrado.dominio.mundo.Direccion;
import liminal.integrado.dominio.mundo.Habitacion;
import liminal.integrado.dominio.mundo.MapaLiminal;

/** Sesión de dominio: meta, participante y turnos; sin entrada/salida por consola. */
public final class Expedicion {
    private final String id;
    private final Explorador explorador;
    private final Habitacion meta;
    private final MapaLiminal mapa;
    private MonstruoOscuro criatura;
    private int turnos;
    private boolean finalizada;

    public Expedicion(String id, MapaLiminal mapa, Explorador explorador, Habitacion meta) {
        this.id = Objects.requireNonNull(id, "ID obligatorio");
        if (id.isBlank()) throw new IllegalArgumentException("ID vacío");
        this.mapa = Objects.requireNonNull(mapa);
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
    public MonstruoOscuro criatura() { return criatura; }

    public void incorporarCriatura(MonstruoOscuro nueva) {
        Objects.requireNonNull(nueva, "Criatura obligatoria");
        if (!nueva.perteneceA(mapa)) {
            throw new IllegalArgumentException("La criatura pertenece a otro mapa");
        }
        // Alcance didáctico: máximo una criatura, incorporada antes del primer turno.
        if (criatura != null || turnos > 0 || finalizada) {
            throw new IllegalStateException("No se admite otra criatura ni incorporaciones tardías");
        }
        criatura = nueva;
    }

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

