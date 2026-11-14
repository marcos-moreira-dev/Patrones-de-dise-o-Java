package liminal.comportamiento.command;

import java.util.Objects;

final class PanelBotones {
    private Comando comando;
    void asignar(Comando nuevo) { comando = Objects.requireNonNull(nuevo); }
    String pulsar() {
        if (comando == null) throw new IllegalStateException("No hay orden asignada");
        return comando.ejecutar();
    }
}
