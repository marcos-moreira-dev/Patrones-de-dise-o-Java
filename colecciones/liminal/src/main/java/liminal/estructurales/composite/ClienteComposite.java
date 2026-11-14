package liminal.estructurales.composite;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/** Composite: controlar una luz individual y grupos con el mismo contrato. */
public final class ClienteComposite {
    private ClienteComposite() {}

    public static void ejecutar(PrintStream out) {
        LuzIndividual a = new LuzIndividual("L-01");
        LuzIndividual b = new LuzIndividual("L-02");
        GrupoLuces grupo = new GrupoLuces("Pasillo amarillo");
        grupo.agregar(a);
        grupo.agregar(b);
        grupo.encender();
        out.println("Luz L-01: " + (a.encendida() ? "ENCENDIDA" : "APAGADA"));
        out.println("Luz L-02: " + (b.encendida() ? "ENCENDIDA" : "APAGADA"));
    }

    public static void probar() {
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
    public static void main(String[] args) { ejecutar(System.out); }
}

interface Encendible {
    void encender();
    void apagar();
}
final class LuzIndividual implements Encendible {
    private final String id;
    private boolean encendida;

    LuzIndividual(String id) {
        this.id = Objects.requireNonNull(id);
        if (id.isBlank()) throw new IllegalArgumentException("ID vacío");
    }
    String id() { return id; }
    boolean encendida() { return encendida; }
    public void encender() { encendida = true; }
    public void apagar() { encendida = false; }
}

final class GrupoLuces implements Encendible {
    private final String nombre;
    private final List<Encendible> hijos = new ArrayList<>();

    GrupoLuces(String nombre) {
        this.nombre = Objects.requireNonNull(nombre);
        if (nombre.isBlank()) throw new IllegalArgumentException("Nombre del grupo vacío");
    }
    String nombre() { return nombre; }
    List<Encendible> hijos() { return List.copyOf(hijos); }

    void agregar(Encendible objeto) {
        Objects.requireNonNull(objeto);
        if (objeto == this || hijos.contains(objeto)
                || (objeto instanceof GrupoLuces grupo && grupo.contieneGrupo(this, new HashSet<>()))) {
            throw new IllegalArgumentException("Duplicado o ciclo no permitido");
        }
        hijos.add(objeto);
    }

    private boolean contieneGrupo(GrupoLuces buscado, Set<GrupoLuces> visitados) {
        if (this == buscado) return true;
        if (!visitados.add(this)) return false;
        for (Encendible hijo : hijos) {
            if (hijo instanceof GrupoLuces g && g.contieneGrupo(buscado, visitados)) return true;
        }
        return false;
    }
    public void encender() { for (Encendible hijo : hijos) hijo.encender(); }
    public void apagar() { for (Encendible hijo : hijos) hijo.apagar(); }
}
