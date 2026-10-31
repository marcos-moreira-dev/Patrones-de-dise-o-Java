package miniso.aplicacion;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import miniso.dominio.EstadoProceso;
import miniso.dominio.Proceso;

/**
 * Coordina casos de uso: admisión, tick de CPU, E/S y contabilidad de memoria.
 * No crea hilos, no gestiona la RAM real ni modifica procesos del ordenador.
 * Cada tick cuesta O(B + coste de planificar), con B procesos bloqueados.
 */
public final class SimuladorSO {
    private final Planificador planificador;
    private final int capacidadKiB;
    private final Map<Long, Proceso> procesos = new LinkedHashMap<>();
    private int memoriaUsadaKiB;
    private long siguientePid = 1;
    private long reloj;
    private Proceso ejecutando;
    private int ticksConsecutivos;

    public SimuladorSO(Planificador planificador, int capacidadKiB) {
        this.planificador = Objects.requireNonNull(planificador, "planificador");
        if (capacidadKiB <= 0) throw new IllegalArgumentException("Capacidad positiva requerida");
        this.capacidadKiB = capacidadKiB;
    }

    public String politica() { return planificador.nombre(); }
    public long reloj() { return reloj; }
    public int capacidadKiB() { return capacidadKiB; }
    public int memoriaUsadaKiB() { return memoriaUsadaKiB; }
    public long pidEnCpu() { return ejecutando == null ? 0 : ejecutando.pid(); }

    /**
     * CU-SO01: admite un trabajo solo si tiene datos válidos y cabe en memoria.
     * La memoria queda reservada incluso mientras espera CPU o entrada/salida.
     */
    public ResultadoAlta crearProceso(String nombre, int cpu, int prioridad, int memoriaKiB) {
        if (nombre == null || nombre.isBlank() || cpu <= 0 || prioridad < 0 || prioridad > 9
                || memoriaKiB <= 0) {
            return new ResultadoAlta(false, 0, "Nombre, CPU, prioridad (0..9) y memoria deben ser validos");
        }
        if (memoriaKiB > capacidadKiB - memoriaUsadaKiB) {
            return new ResultadoAlta(false, 0, "Memoria insuficiente; no se admitio el proceso");
        }
        if (siguientePid == Long.MAX_VALUE) {
            return new ResultadoAlta(false, 0, "Se agoto el espacio simulado de PID");
        }
        Proceso proceso = new Proceso(siguientePid++, nombre, cpu, prioridad, memoriaKiB);
        proceso.ponerListo();
        planificador.admitir(proceso);
        procesos.put(proceso.pid(), proceso);
        memoriaUsadaKiB += memoriaKiB;
        return new ResultadoAlta(true, proceso.pid(), "PID " + proceso.pid() + " admitido en LISTO");
    }

    /**
     * CU-SO02: avanza un tick; una E/S finalizada se encola al final del tick,
     * por lo que no puede ejecutar CPU hasta el tick siguiente.
     */
    public EventoTick avanzarTick() {
        reloj++;
        if (ejecutando == null) {
            ejecutando = planificador.siguiente();
            ticksConsecutivos = 0;
            if (ejecutando != null) ejecutando.comenzarCpu();
        }
        EventoTick evento;
        if (ejecutando == null) {
            evento = new EventoTick(reloj, EventoTick.Tipo.OCIOSO, 0, "CPU ociosa");
        } else {
            Proceso proceso = ejecutando;
            proceso.consumirCpu();
            ticksConsecutivos++;
            if (proceso.estado() == EstadoProceso.TERMINADO) {
                memoriaUsadaKiB -= proceso.memoriaKiB();
                evento = new EventoTick(reloj, EventoTick.Tipo.FINALIZACION,
                        proceso.pid(), "PID " + proceso.pid() + " termino y libero memoria");
                ejecutando = null;
                ticksConsecutivos = 0;
            } else {
                boolean rotacion = planificador.debeCederCpu(ticksConsecutivos);
                evento = new EventoTick(reloj, EventoTick.Tipo.EJECUCION, proceso.pid(),
                        "PID " + proceso.pid() + ": " + proceso.cpuRestante() + " ticks restantes"
                        + (rotacion ? " (fin del quantum)" : ""));
                if (rotacion) {
                    proceso.ponerListo();
                    planificador.admitir(proceso);
                    ejecutando = null;
                    ticksConsecutivos = 0;
                }
            }
        }
        // Las E/S avanzan en paralelo con la CPU, nunca consumen un tick de ella.
        for (Proceso proceso : procesos.values()) {
            if (proceso.estado() == EstadoProceso.BLOQUEADO && proceso.avanzarIo()) {
                proceso.ponerListo();
                planificador.admitir(proceso);
            }
        }
        return evento;
    }

    /** CU-SO03: el proceso que tiene CPU cede el procesador para esperar E/S. */
    public ResultadoOperacion solicitarIo(int ticksIo) {
        if (ticksIo <= 0) return ResultadoOperacion.rechazada("La E/S debe durar al menos un tick");
        if (ejecutando == null) {
            return ResultadoOperacion.rechazada("No hay proceso ejecutando para bloquear");
        }
        long pid = ejecutando.pid();
        ejecutando.solicitarIo(ticksIo);
        ejecutando = null;
        ticksConsecutivos = 0;
        return ResultadoOperacion.aceptada("PID " + pid + " bloqueado por " + ticksIo + " ticks");
    }

    /** CU-SO04: la presentación recibe copias de datos, no la entidad mutable. */
    public List<VistaProceso> listarProcesos() {
        List<VistaProceso> vistas = new ArrayList<>();
        for (Proceso p : procesos.values()) {
            vistas.add(new VistaProceso(p.pid(), p.nombre(), p.estado(), p.cpuRestante(),
                    p.prioridad(), p.memoriaKiB(), p.esperaIo()));
        }
        return List.copyOf(vistas);
    }
}
