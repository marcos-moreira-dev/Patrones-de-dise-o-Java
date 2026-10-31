package miniso.aplicacion;

import miniso.dominio.EstadoProceso;

/**
 * Instantánea inmutable de métricas de un proceso ficticio.
 * El valor -1 indica que aún no puede calcularse respuesta o retorno.
 */
public record EstadisticaProceso(long pid, String nombre, EstadoProceso estado,
                                long llegada, long primerTickCpu, long finalizacion,
                                long ticksCpu, long ticksListo, long ticksIo) {

    /** Ticks transcurridos antes de recibir CPU por primera vez. */
    public long respuesta() {
        return primerTickCpu < 0 ? -1 : primerTickCpu - llegada - 1;
    }

    /** Ticks entre admisión y terminación, incluidos CPU, LISTO y E/S. */
    public long retorno() {
        return finalizacion < 0 ? -1 : finalizacion - llegada;
    }
}
