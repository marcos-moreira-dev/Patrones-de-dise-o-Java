package liminal.integrado.dominio.vigilancia;

import java.util.Objects;

/** Receptor que conserva el último evento y la cantidad de avisos. */
public final class Alarma {
    private int avisos;
    private EventoRuido ultimo;

    public void avisar(EventoRuido evento) {
        Objects.requireNonNull(evento);
        avisos++;
        ultimo = evento;
    }

    public int avisos() { return avisos; }
    public EventoRuido ultimo() { return ultimo; }
}
