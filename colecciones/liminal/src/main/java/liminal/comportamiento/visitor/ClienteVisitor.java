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

    public static void probar() {
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
    public static void main(String[] args) { ejecutar(System.out); }

    private static List<Elemento> escena() {
        return List.of(new Puerta("P-01"), new Luz("L-01"), new Linterna("A-01"));
    }
}

interface Elemento {
    void aceptar(Visitante visitante);
}
record Puerta(String id) implements Elemento {
    Puerta {
        Objects.requireNonNull(id);
        if (id.isBlank()) throw new IllegalArgumentException("ID vacío");
    }
    public void aceptar(Visitante visitante) { visitante.visitar(this); }
}
record Luz(String id) implements Elemento {
    Luz {
        Objects.requireNonNull(id);
        if (id.isBlank()) throw new IllegalArgumentException("ID vacío");
    }
    public void aceptar(Visitante visitante) { visitante.visitar(this); }
}
record Linterna(String id) implements Elemento {
    Linterna {
        Objects.requireNonNull(id);
        if (id.isBlank()) throw new IllegalArgumentException("ID vacío");
    }
    public void aceptar(Visitante visitante) { visitante.visitar(this); }
}

interface Visitante {
    void visitar(Puerta puerta);
    void visitar(Luz luz);
    void visitar(Linterna linterna);
}

final class VisitanteDescripcion implements Visitante {
    private final List<String> resultados = new ArrayList<>();
    public void visitar(Puerta puerta) { resultados.add("PUERTA " + puerta.id()); }
    public void visitar(Luz luz) { resultados.add("LUZ " + luz.id()); }
    public void visitar(Linterna linterna) { resultados.add("LINTERNA " + linterna.id()); }
    List<String> resultados() { return List.copyOf(resultados); }
}

final class VisitanteConteo implements Visitante {
    private int cantidad;
    public void visitar(Puerta puerta) { cantidad++; }
    public void visitar(Luz luz) { cantidad++; }
    public void visitar(Linterna linterna) { cantidad++; }
    int cantidad() { return cantidad; }
}
