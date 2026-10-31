package miniso.presentacion;

import java.util.Scanner;

/** Punto de entrada del microproyecto, sin frameworks y sin permisos especiales. */
public final class MiniSO {
    private MiniSO() {}

    public static void main(String[] args) {
        new ConsolaMiniSO(new Scanner(System.in), System.out).iniciar();
    }
}
