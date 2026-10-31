package arena.aplicacion;

import arena.dominio.Accion;
import arena.dominio.MotorCombate;
import arena.dominio.Partida;
import java.util.List;
import java.util.Optional;

/**
 * Pequeña cadena ordenada de comprobaciones con un motivo por fallo.
 * Es una alternativa legible a repetir las mismas condiciones en cada comando.
 * No exige construir una infraestructura genérica de validación.
 */
public final class ReglasAccion {
    private ReglasAccion() {}

    @FunctionalInterface
    public interface Regla {
        Optional<String> revisar(Partida partida, Accion accion);
    }

    /** @return reglas aplicadas en el orden en que deben evaluarse */
    public static List<Regla> porDefecto() {
        return List.of(new PartidaVigente(), new AccionDelTerreno(), new EnergiaSuficiente());
    }

    private record PartidaVigente() implements Regla {
        @Override
        public Optional<String> revisar(Partida partida, Accion accion) {
            return partida.activa() ? Optional.empty() : Optional.of("La partida ya finalizo.");
        }
    }

    private record AccionDelTerreno() implements Regla {
        @Override
        public Optional<String> revisar(Partida partida, Accion accion) {
            return partida.escenario().impedimento(accion);
        }
    }

    private record EnergiaSuficiente() implements Regla {
        @Override
        public Optional<String> revisar(Partida partida, Accion accion) {
            return accion == Accion.ESPECIAL && partida.heroe().energia() < MotorCombate.COSTE_ESPECIAL
                    ? Optional.of("Falta energia para el golpe especial (cuesta 3).")
                    : Optional.empty();
        }
    }
}
