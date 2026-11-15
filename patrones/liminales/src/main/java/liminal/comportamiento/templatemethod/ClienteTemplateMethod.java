package liminal.comportamiento.templatemethod;

import java.io.PrintStream;

/** Template Method: el orden del recorrido es fijo y la observación varía. */
public final class ClienteTemplateMethod {
    private ClienteTemplateMethod() {}

    public static void ejecutar(PrintStream out) {
        RutaExploracion oficina = new RutaOficina();
        RutaExploracion piscina = new RutaPiscina();
        out.println(String.join(" -> ", oficina.recorrer()));
        out.println(String.join(" -> ", piscina.recorrer()));
    }

    public static void main(String[] args) { ejecutar(System.out); }
}
