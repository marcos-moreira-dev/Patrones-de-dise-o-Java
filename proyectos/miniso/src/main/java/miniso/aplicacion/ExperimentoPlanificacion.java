package miniso.aplicacion;

import java.util.ArrayList;
import java.util.List;

/**
 * Experimento determinista: misma carga y mismo tiempo de llegada para
 * tres políticas. Son ticks ficticios, no mediciones de velocidad del PC.
 */
public final class ExperimentoPlanificacion {
    private ExperimentoPlanificacion() {}

    public record Resultado(String politica, double esperaMedia, double respuestaMedia,
                            double retornoMedio, long alternancias, long ticksCpu) {}

    /** CU-SO10: compara políticas manteniendo fija la carga de tres procesos. */
    public static List<Resultado> comparar() {
        List<Planificador> politicas = List.of(PlanificadorCola.fcfs(),
                PlanificadorCola.roundRobin(2), new PlanificadorPrioridad());
        List<Resultado> resultados = new ArrayList<>();
        for (Planificador politica : politicas) {
            SimuladorSO so = new SimuladorSO(politica, 32);
            so.crearProceso("editor", 6, 2, 8);
            so.crearProceso("musica", 2, 9, 8);
            so.crearProceso("juego", 4, 5, 12);
            // 6 + 2 + 4 unidades, sin bloqueos: exactamente 12 ticks útiles.
            for (int i = 0; i < 12; i++) so.avanzarTick();
            List<EstadisticaProceso> datos = so.estadisticas();
            double espera = datos.stream().mapToLong(EstadisticaProceso::ticksListo).average().orElse(0);
            double respuesta = datos.stream().mapToLong(EstadisticaProceso::respuesta).average().orElse(0);
            double retorno = datos.stream().mapToLong(EstadisticaProceso::retorno).average().orElse(0);
            resultados.add(new Resultado(so.politica(), espera, respuesta, retorno,
                    so.cambiosDeProceso(), so.ticksCpuOcupados()));
        }
        return List.copyOf(resultados);
    }
}
