package liminal.creacionales.singleton;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/** Demostración de Singleton, limitado a un panel de avisos ficticio. */
public final class ClienteSingleton {
    private ClienteSingleton() {}

    public static void ejecutar(PrintStream out) {
        PanelAvisos a = PanelAvisos.instancia();
        PanelAvisos b = PanelAvisos.instancia();
        a.limpiarParaDemo();
        a.publicar("Puerta P-01 bloqueada");
        b.publicar("Se escucha un ruido");
        out.println("¿Es el mismo panel? " + (a == b));
        out.println("Avisos: " + b.mensajes().size());
        out.println("Último aviso: " + b.mensajes().get(1));
    }

    public static void probar() {
        PanelAvisos a = PanelAvisos.instancia();
        PanelAvisos b = PanelAvisos.instancia();
        if (a != b) throw new AssertionError("Singleton debe devolver la misma instancia");
        a.limpiarParaDemo();
        a.publicar("Luz encendida");
        if (!b.mensajes().equals(List.of("Luz encendida"))) {
            throw new AssertionError("Ambas referencias deben compartir estado");
        }
        try {
            a.publicar(" ");
            throw new AssertionError("Debe rechazar mensajes vacíos");
        } catch (IllegalArgumentException esperado) {
            if (a.mensajes().size() != 1) throw new AssertionError("No debe mutar");
        }
        a.limpiarParaDemo();
    }

    public static void main(String[] args) { ejecutar(System.out); }
}
