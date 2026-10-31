package arena.aplicacion;

import arena.dominio.EventoCombate;
import java.util.List;
import java.util.Objects;

/**
 * Resultado esperado de una orden: un rechazo de las reglas no es una excepción.
 * Los records transparentan datos; la entidad Jugador conserva su comportamiento.
 */
public sealed interface ResultadoAccion
        permits ResultadoAccion.Ejecutada, ResultadoAccion.Rechazada {

    /** Orden válida con sus acontecimientos. */
    record Ejecutada(List<EventoCombate> eventos) implements ResultadoAccion {
        public Ejecutada {
            eventos = List.copyOf(eventos);
        }
    }

    /** Orden no ejecutada; no consume un turno ni recursos. */
    record Rechazada(String motivo) implements ResultadoAccion {
        public Rechazada {
            Objects.requireNonNull(motivo);
        }
    }
}
