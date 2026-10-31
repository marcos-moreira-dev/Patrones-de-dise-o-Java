package miniso.aplicacion;

import miniso.dominio.Proceso;
import miniso.estructuras.MonticuloPrioridad;

/** Prioridad no expropiativa: mayor prioridad al quedar libre la CPU. */
public final class PlanificadorPrioridad implements Planificador {
    private final MonticuloPrioridad<Proceso> listos = new MonticuloPrioridad<>();

    @Override public void admitir(Proceso proceso) { listos.insertar(proceso, proceso.prioridad()); }
    @Override public Proceso siguiente() { return listos.estaVacio() ? null : listos.extraer(); }
    @Override public boolean debeCederCpu(int ticksConsecutivos) { return false; }
    @Override public String nombre() { return "Prioridad no expropiativa"; }
}
