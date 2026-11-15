package liminal.integrado.dominio.exploracion;

import java.util.Objects;
import java.util.Optional;
import liminal.integrado.dominio.criaturas.EstadoCriatura;
import liminal.integrado.dominio.criaturas.EstrategiaMovimiento;
import liminal.integrado.dominio.criaturas.MonstruoOscuro;
import liminal.integrado.dominio.mundo.Direccion;
import liminal.integrado.dominio.mundo.Habitacion;
import liminal.integrado.dominio.mundo.MapaLiminal;

/** Sesión de dominio: mantiene identidad, turnos, meta y criatura sin consola. */
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

    /** Fotografía inmutable; no se devuelve el objeto mutable MonstruoOscuro. */
    public Optional<EstadoCriatura> estadoCriatura() {
        return criatura == null ? Optional.empty()
                : Optional.of(new EstadoCriatura(criatura.id(), criatura.ubicacion()));
    }

    public void incorporarCriatura(String id, Habitacion inicio,
                                  EstrategiaMovimiento estrategia) {
        Objects.requireNonNull(inicio, "Inicio obligatorio");
        Objects.requireNonNull(estrategia, "Estrategia obligatoria");
        // La sesión construye su propio monstruo: nadie conserva una referencia
        // mutable con la que pueda moverlo sin consumir un turno.
        if (mapa.habitacion(inicio.id()) != inicio) {
            throw new IllegalArgumentException("La criatura pertenece a otro mapa");
        }
        if (criatura != null || turnos > 0 || finalizada) {
            throw new IllegalStateException("No se admite otra criatura ni incorporaciones tardías");
        }
        criatura = new MonstruoOscuro(id, mapa, inicio, estrategia);
    }

    public void cambiarEstrategiaCriatura(EstrategiaMovimiento nueva) {
        Objects.requireNonNull(nueva, "Estrategia obligatoria");
        if (criatura == null || finalizada) {
            throw new IllegalStateException("La expedición no admite cambio de estrategia");
        }
        criatura.cambiarEstrategia(nueva);
    }

    public AvanceExpedicion avanzar(Direccion direccion) {
        Objects.requireNonNull(direccion);
        if (finalizada) throw new IllegalStateException("La expedición ya finalizó");
        // Si falla el paso del explorador, no hay turno ni acción de criatura.
        Habitacion nueva = explorador.mover(direccion);
        turnos++;
        finalizada = nueva == meta;
        if (criatura == null) return new AvanceExpedicion(nueva, null, false, false);

        Habitacion anterior = criatura.ubicacion();
        boolean rechazada = false;
        try {
            criatura.avanzar(nueva);
        } catch (IllegalStateException propuestaInvalida) {
            // Dos acciones sucesivas: no se revierte el movimiento ya válido
            // del explorador cuando una Strategy propone una ruta ilegal.
            rechazada = true;
        }
        return new AvanceExpedicion(nueva,
                new EstadoCriatura(criatura.id(), criatura.ubicacion()),
                criatura.ubicacion() != anterior, rechazada);
    }
}
