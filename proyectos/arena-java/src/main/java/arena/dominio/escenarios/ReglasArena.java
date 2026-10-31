package arena.dominio.escenarios;

import arena.dominio.Accion;
import arena.dominio.ReglasEscenario;
import java.util.Optional;

/** Reglamento ficticio del coliseo: no se puede huir ni saltar. */
public final class ReglasArena implements ReglasEscenario {
    @Override
    public String nombre() {
        return "Arena";
    }

    @Override
    public String nombreRival() {
        return "Gladiador";
    }

    @Override
    public String descripcion() {
        return "Duelo reglamentado: no se permite huir ni saltar.";
    }

    @Override
    public Optional<String> impedimento(Accion accion) {
        return switch (accion) {
            case SALTAR -> Optional.of("El reglamento de la arena prohibe saltar.");
            case HUIR -> Optional.of("No se puede abandonar un duelo de arena.");
            default -> Optional.empty();
        };
    }

    @Override
    public int danioTerreno(int turno, Accion accion) {
        return 0;
    }

    @Override
    public Optional<String> aviso(int turno) {
        return Optional.empty();
    }
}
