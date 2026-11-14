package liminal.comportamiento.visitor;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/** Visitor: nuevas operaciones de inspección sin cambiarlas en cada objeto. */
public final class ClienteVisitor {
    private ClienteVisitor() {}

    public static void ejecutar(PrintStream out) {
        List<Elemento> objetos = escena();
        VisitanteDescripcion descripcion = new VisitanteDescripcion();
        for (Elemento e : objetos) e.aceptar(descripcion);
        for (String texto : descripcion.resultados()) out.println(texto);
    }


    public static void main(String[] args) { ejecutar(System.out); }

    private static List<Elemento> escena() {
        return List.of(new Puerta("P-01"), new Luz("L-01"), new Linterna("A-01"));
    }
}
