package miniso.aplicacion;

import miniso.dominio.Proceso;
import miniso.estructuras.ColaCircular;

/** FCFS no interrumpe; Round Robin rota cada quantum de ticks. */
public final class PlanificadorCola implements Planificador {
    private final ColaCircular<Proceso> cola = new ColaCircular<>();
    private final int quantum;

    private PlanificadorCola(int quantum) { this.quantum = quantum; }

    /** FCFS: primera llegada, primera atención, sin desalojo. */
    public static PlanificadorCola fcfs() { return new PlanificadorCola(0); }

    /** RR: el quantum limita ticks consecutivos de CPU, no el trabajo total. */
    public static PlanificadorCola roundRobin(int quantum) {
        if (quantum <= 0) throw new IllegalArgumentException("Quantum positivo requerido");
        return new PlanificadorCola(quantum);
    }

    @Override public void admitir(Proceso proceso) { cola.encolar(proceso); }
    @Override public Proceso siguiente() { return cola.estaVacia() ? null : cola.desencolar(); }
    @Override public boolean debeCederCpu(int ticksConsecutivos) {
        return quantum > 0 && ticksConsecutivos >= quantum;
    }
    @Override public String nombre() { return quantum == 0 ? "FCFS" : "Round Robin (q=" + quantum + ")"; }
}
