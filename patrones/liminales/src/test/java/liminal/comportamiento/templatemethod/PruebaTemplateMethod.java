package liminal.comportamiento.templatemethod;

import java.util.List;

/** Prueba independiente del recorrido pedagógico. */
public final class PruebaTemplateMethod {
    private PruebaTemplateMethod() {}

    public static void ejecutar() {
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
}
