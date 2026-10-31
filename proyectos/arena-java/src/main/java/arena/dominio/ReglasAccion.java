package arena.dominio;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * Reglas del dominio para autorizar una acción en cualquier punto de entrada.
 * Una cadena corta permite añadir una comprobación sin duplicar condiciones.
 * No depende de la aplicación ni de la consola.
 */
public final class ReglasAccion {
    private static final List<Regla> REGLAS =
            List.of(new PartidaVigente(), new AccionDelTerreno(), new EnergiaSuficiente());

    private ReglasAccion() {}

    /**
     * @param partida contexto cuya integridad debe protegerse
     * @param accion acción solicitada
     * @return primer motivo de rechazo, o vacío cuando se permite
     */
    public static Optional<String> impedimento(Partida partida, Accion accion) {
        Objects.requireNonNull(partida);
        Objects.requireNonNull(accion);
        for (Regla regla : REGLAS) {
            Optional<String> rechazo = regla.revisar(partida, accion);
            if (rechazo.isPresent()) {
                return rechazo;
            }
        }
        return Optional.empty();
    }

    private interface Regla {
        Optional<String> revisar(Partida partida, Accion accion);
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
            return partida.heroe().energia() < accion.costeEnergia()
                    ? Optional.of("Falta energia: esta accion cuesta " + accion.costeEnergia() + ".")
                    : Optional.empty();
        }
    }
}
