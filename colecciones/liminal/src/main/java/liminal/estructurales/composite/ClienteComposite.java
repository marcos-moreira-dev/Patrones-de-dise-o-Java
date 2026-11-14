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


    public static void main(String[] args) { ejecutar(System.out); }
}
