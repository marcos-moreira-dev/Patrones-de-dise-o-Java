package liminal.comportamiento.templatemethod;

import java.io.PrintStream;
import java.util.List;

/** Template Method: el orden del recorrido es fijo y la observación varía. */
public final class ClienteTemplateMethod {
    private ClienteTemplateMethod() {}

    public static void ejecutar(PrintStream out) {
        RutaExploracion oficina = new RutaOficina();
        RutaExploracion piscina = new RutaPiscina();
        out.println(String.join(" -> ", oficina.recorrer()));
        out.println(String.join(" -> ", piscina.recorrer()));
    }

    public static void probar() {
        List<String> oficina = new RutaOficina().recorrer();
        List<String> piscina = new RutaPiscina().recorrer();
        if (!oficina.get(0).equals("Preparar linterna")
                || !piscina.get(0).equals("Preparar linterna")
                || !oficina.get(2).equals("Regresar al punto seguro")
                || !piscina.get(2).equals("Regresar al punto seguro")) {
            throw new AssertionError("Se alteró el orden común de la plantilla");
        }
        if (oficina.get(1).equals(piscina.get(1))) {
            throw new AssertionError("El paso especial debe cambiar según la ruta");
        }
    }
    public static void main(String[] args) { ejecutar(System.out); }
}
