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

        // Dos grupos pueden referenciar la misma luz: esto no es propiedad exclusiva.
        GrupoLuces otroGrupo = new GrupoLuces("Otro pasillo");
        otroGrupo.agregar(uno);
        edificio.encender();
        otroGrupo.apagar();
        if (uno.encendida() || !dos.encendida()) {
            throw new AssertionError("Los dos grupos deben compartir la misma instancia de luz");
        }
        edificio.apagar();
        try {
            pasillo.agregar(uno);
            throw new AssertionError("El mismo grupo no admite la misma referencia dos veces");
        } catch (IllegalArgumentException esperado) {
            if (!pasillo.hijos().equals(List.of(uno))) {
                throw new AssertionError("Rechazar duplicados no debe mutar el grupo");
            }
        }
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
