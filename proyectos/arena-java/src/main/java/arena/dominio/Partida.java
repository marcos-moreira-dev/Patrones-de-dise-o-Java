package arena.dominio;

import java.util.Objects;

/**
 * Agregado de una partida breve: dos personajes, un terreno y un turno vigente.
 * El estado terminal no equivale al patrón GoF State: aquí basta un enum.
 */
public final class Partida {
    public enum Estado {
        ACTIVA, VICTORIA, DERROTA, HUIDA
    }

    private final Jugador heroe;
    private final Jugador rival;
    private final ReglasEscenario escenario;
    private int turno = 1;
    private Estado estado = Estado.ACTIVA;

    public Partida(Jugador heroe, Jugador rival, ReglasEscenario escenario) {
        this.heroe = Objects.requireNonNull(heroe);
        this.rival = Objects.requireNonNull(rival);
        this.escenario = Objects.requireNonNull(escenario);
        if (!heroe.estaVivo() || !rival.estaVivo() || heroe == rival) {
            throw new IllegalArgumentException("Se requieren dos personajes vivos y diferentes");
        }
    }

    public Jugador heroe() {
        return heroe;
    }

    public Jugador rival() {
        return rival;
    }

    public ReglasEscenario escenario() {
        return escenario;
    }

    public int turno() {
        return turno;
    }

    public Estado estado() {
        return estado;
    }

    public boolean activa() {
        return estado == Estado.ACTIVA;
    }

    /**
     * Finaliza la partida una única vez.
     *
     * @param nuevoEstado resultado terminal
     * @throws IllegalArgumentException si se intenta usar ACTIVA como resultado
     * @throws IllegalStateException si la partida ya finalizó
     */
    public void finalizar(Estado nuevoEstado) {
        if (nuevoEstado == null || nuevoEstado == Estado.ACTIVA) {
            throw new IllegalArgumentException("Se requiere un estado terminal");
        }
        if (!activa()) {
            throw new IllegalStateException("La partida ya termino");
        }
        estado = nuevoEstado;
    }

    /** Avanza el turno únicamente si la partida permanece activa. */
    public void avanzarTurno() {
        if (!activa()) {
            throw new IllegalStateException("No puede avanzar una partida terminada");
        }
        turno++;
    }
}
