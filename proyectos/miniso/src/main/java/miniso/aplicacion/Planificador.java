package miniso.aplicacion;

import miniso.dominio.Proceso;

/** Strategy: el simulador no conoce cómo se organiza la cola de listos. */
public interface Planificador {
    void admitir(Proceso proceso);
    Proceso siguiente();
    boolean debeCederCpu(int ticksConsecutivos);
    String nombre();
}
