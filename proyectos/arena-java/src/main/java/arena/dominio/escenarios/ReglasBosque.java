package arena.dominio.escenarios;

import arena.dominio.Accion;
import arena.dominio.ReglasEscenario;
import java.util.Optional;

/**
 * En este bosque imaginario emergen raíces cada tres turnos.
 * Saltar durante ese turno evita el daño, pero consume la acción del héroe.
 */
public final class ReglasBosque implements ReglasEscenario {
    private static final int DANIO_RAICES = 3;

    @Override
    public String nombre() {
        return "Bosque";
    }

    @Override
    public String nombreRival() {
        return "Lobo";
    }

    @Override
    public String descripcion() {
        return "Puedes huir. Cada tres turnos hay raices: saltar las esquiva.";
    }

    @Override
    public Optional<String> impedimento(Accion accion) {
        return Optional.empty();
    }

    @Override
    public int danioTerreno(int turno, Accion accion) {
        return turno > 0 && turno % 3 == 0 && accion != Accion.SALTAR ? DANIO_RAICES : 0;
    }

    @Override
    public Optional<String> aviso(int turno) {
        return turno > 0 && turno % 3 == 0
                ? Optional.of("RAICES ahora: salta para evitar 3 de danio.")
                : Optional.empty();
    }
}
