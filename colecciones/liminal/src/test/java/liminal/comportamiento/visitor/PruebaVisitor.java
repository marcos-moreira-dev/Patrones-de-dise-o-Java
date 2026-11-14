package liminal.comportamiento.visitor;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/** Prueba independiente del recorrido pedagógico. */
public final class PruebaVisitor {
    private PruebaVisitor() {}

    public static void ejecutar() {
        List<Elemento> objetos = escena();
        VisitanteDescripcion descripcion = new VisitanteDescripcion();
        VisitanteConteo conteo = new VisitanteConteo();
        for (Elemento e : objetos) {
            e.aceptar(descripcion);
            e.aceptar(conteo);
        }
        if (conteo.cantidad() != 3 || descripcion.resultados().size() != 3) {
            throw new AssertionError("Los visitantes deben procesar todos los tipos");
        }
        if (!descripcion.resultados().get(0).contains("P-01")
                || !descripcion.resultados().get(2).contains("LINTERNA")) {
            throw new AssertionError("Despacho del visitante incorrecto");
        }
        try {
            new Puerta(" ");
            throw new AssertionError("Una puerta necesita un ID");
        } catch (IllegalArgumentException esperado) {
            // R-01.
        }
    }

    private static List<Elemento> escena() {
        return List.of(new Puerta("P-01"), new Luz("L-01"), new Linterna("A-01"));
    }
}
