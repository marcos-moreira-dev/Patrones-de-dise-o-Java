package liminal.comportamiento.command;

import java.util.Objects;

final class PanelBotones {
    // El invocador depende de Comando, no de si la acción afecta a luz o puerta:
    // cambiar de receptor no obliga a modificar el panel.
    private Comando comando;
    void asignar(Comando nuevo) { comando = Objects.requireNonNull(nuevo); }
    String pulsar() {
        if (comando == null) throw new IllegalStateException("No hay orden asignada");
        return comando.ejecutar();
    }
}
