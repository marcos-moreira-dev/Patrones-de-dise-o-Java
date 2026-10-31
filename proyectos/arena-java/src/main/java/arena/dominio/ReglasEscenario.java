package arena.dominio;

import java.util.Optional;

/**
 * Strategy: cada terreno define reglas distintas sin modificar a Jugador.
 * Sus respuestas describen el dominio y no producen efectos de consola.
 */
public interface ReglasEscenario {
    String nombre();

    /** @return nombre del adversario apropiado para este entorno */
    String nombreRival();

    String descripcion();

    /**
     * @param accion acción solicitada
     * @return motivo de rechazo, o vacío cuando está permitida
     */
    Optional<String> impedimento(Accion accion);

    /**
     * Calcula el peligro ambiental para un turno válido.
     *
     * @param turno número de turno, desde uno
     * @param accion acción elegida
     * @return daño no negativo del terreno
     */
    int danioTerreno(int turno, Accion accion);

    /** Describe un peligro próximo, si existe; de otro modo devuelve vacío. */
    Optional<String> aviso(int turno);
}
