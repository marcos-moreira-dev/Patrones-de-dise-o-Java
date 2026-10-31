package miniso.dominio;

/**
 * Entidad de dominio: un trabajo que consume unidades ficticias de CPU y memoria.
 * Sus transiciones son operaciones; no se modifica su estado con setters.
 */
public final class Proceso {
    private final long pid;
    private final String nombre;
    private final int prioridad;
    private final int memoriaKiB;
    private int cpuRestante;
    private int esperaIo;
    private EstadoProceso estado = EstadoProceso.NUEVO;

    /** CU-SO01: da de alta la entidad; todavía no ingresa a la cola de listos. */
    public Proceso(long pid, String nombre, int unidadesCpu, int prioridad, int memoriaKiB) {
        if (pid <= 0 || nombre == null || nombre.isBlank() || unidadesCpu <= 0
                || prioridad < 0 || prioridad > 9 || memoriaKiB <= 0) {
            throw new IllegalArgumentException("Datos de proceso invalidos");
        }
        this.pid = pid;
        this.nombre = nombre.strip();
        this.cpuRestante = unidadesCpu;
        this.prioridad = prioridad;
        this.memoriaKiB = memoriaKiB;
    }

    public long pid() { return pid; }
    public String nombre() { return nombre; }
    public int prioridad() { return prioridad; }
    public int memoriaKiB() { return memoriaKiB; }
    public int cpuRestante() { return cpuRestante; }
    public int esperaIo() { return esperaIo; }
    public EstadoProceso estado() { return estado; }

    /** Admite solo procesos nuevos o que cedieron CPU, o terminaron su E/S. */
    public void ponerListo() {
        if (estado != EstadoProceso.NUEVO && estado != EstadoProceso.EJECUTANDO
                && estado != EstadoProceso.BLOQUEADO) {
            throw new IllegalStateException("Transicion a LISTO no permitida desde " + estado);
        }
        if (estado == EstadoProceso.BLOQUEADO && esperaIo != 0) {
            throw new IllegalStateException("La entrada/salida sigue pendiente");
        }
        estado = EstadoProceso.LISTO;
    }

    /** El proceso solo puede recibir CPU después de esperar en la cola. */
    public void comenzarCpu() {
        if (estado != EstadoProceso.LISTO) {
            throw new IllegalStateException("Solo un proceso LISTO recibe CPU");
        }
        estado = EstadoProceso.EJECUTANDO;
    }

    /** CU-SO02: consume exactamente una unidad; termina al agotar su trabajo. */
    public void consumirCpu() {
        if (estado != EstadoProceso.EJECUTANDO || cpuRestante <= 0) {
            throw new IllegalStateException("No se puede ejecutar un proceso fuera de CPU");
        }
        cpuRestante--;
        if (cpuRestante == 0) {
            estado = EstadoProceso.TERMINADO;
        }
    }

    /** CU-SO03: espera E/S sin ocupar la CPU, pero conserva su memoria. */
    public void solicitarIo(int unidades) {
        if (estado != EstadoProceso.EJECUTANDO || unidades <= 0) {
            throw new IllegalStateException("La E/S requiere un proceso ejecutando y tiempo positivo");
        }
        esperaIo = unidades;
        estado = EstadoProceso.BLOQUEADO;
    }

    /** Avanza una unidad de E/S y dice si el proceso acaba de despertarse. */
    public boolean avanzarIo() {
        if (estado != EstadoProceso.BLOQUEADO || esperaIo <= 0) {
            throw new IllegalStateException("Solo avanza E/S de procesos bloqueados");
        }
        esperaIo--;
        return esperaIo == 0;
    }
}
