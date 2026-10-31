package arena.presentacion;

import java.util.Scanner;

/** Punto de entrada independiente de los ejemplos antiguos del repositorio. */
public final class ArenaJava {
    private ArenaJava() {}

    /**
     * Inicia la partida en consola.
     *
     * @param args no se requieren argumentos
     */
    public static void main(String[] args) {
        new ConsolaArena(new Scanner(System.in), System.out).iniciar();
    }
}
