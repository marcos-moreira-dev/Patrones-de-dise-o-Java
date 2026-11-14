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
