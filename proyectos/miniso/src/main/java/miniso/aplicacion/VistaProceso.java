package miniso.aplicacion;

import miniso.dominio.EstadoProceso;

/** Instantánea inmutable para la presentación; no expone la entidad mutable. */
public record VistaProceso(long pid, String nombre, EstadoProceso estado,
                          int cpuRestante, int prioridad, int memoriaKiB, int esperaIo) {}
