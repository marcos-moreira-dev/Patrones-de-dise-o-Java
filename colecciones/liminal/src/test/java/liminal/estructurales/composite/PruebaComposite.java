package liminal.estructurales.composite;

import java.util.List;

/** Prueba independiente del recorrido pedagógico. */
public final class PruebaComposite {
    private PruebaComposite() {}

    public static void ejecutar() {
        LuzIndividual uno = new LuzIndividual("L-01");
        LuzIndividual dos = new LuzIndividual("L-02");
        GrupoLuces pasillo = new GrupoLuces("Pasillo");
        GrupoLuces edificio = new GrupoLuces("Edificio");
        pasillo.agregar(uno);
        edificio.agregar(pasillo);
        edificio.agregar(dos);
        edificio.encender();
        if (!uno.encendida() || !dos.encendida()) throw new AssertionError("Encendido recursivo");
        edificio.apagar();
        if (uno.encendida() || dos.encendida()) throw new AssertionError("Apagado recursivo");
        try {
            pasillo.agregar(edificio);
            throw new AssertionError("No se aceptan ciclos");
        } catch (IllegalArgumentException esperado) {
            if (!pasillo.hijos().equals(List.of(uno))) throw new AssertionError("Sin mutación parcial");
        }
        try {
            new LuzIndividual("");
            throw new AssertionError("No debe existir una luz sin ID");
        } catch (IllegalArgumentException esperado) {
            // R-01.
        }
        try {
            new GrupoLuces(" ");
            throw new AssertionError("El grupo necesita un nombre");
        } catch (IllegalArgumentException esperado) {
            // R-01.
        }
    }
}
