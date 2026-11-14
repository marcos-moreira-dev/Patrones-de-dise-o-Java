package liminal.creacionales.singleton;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

final class PanelAvisos {
    private static final PanelAvisos UNICO = new PanelAvisos();
    private final List<String> avisos = new ArrayList<>();

    private PanelAvisos() {}
    static PanelAvisos instancia() { return UNICO; }

    void publicar(String mensaje) {
        Objects.requireNonNull(mensaje, "Mensaje obligatorio");
        if (mensaje.isBlank()) throw new IllegalArgumentException("Mensaje vacío");
        avisos.add(mensaje);
    }
    List<String> mensajes() { return List.copyOf(avisos); }

    // Solo para aislar las ejecuciones de este ejemplo.
    void limpiarParaDemo() { avisos.clear(); }
}
