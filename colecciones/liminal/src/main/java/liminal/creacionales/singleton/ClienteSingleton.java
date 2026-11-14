package liminal.creacionales.singleton;

import java.io.PrintStream;

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

    public static void main(String[] args) { ejecutar(System.out); }
}
