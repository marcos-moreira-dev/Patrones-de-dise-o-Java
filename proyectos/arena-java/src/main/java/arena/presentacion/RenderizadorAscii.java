package arena.presentacion;

import arena.dominio.Accion;
import arena.dominio.Jugador;
import arena.dominio.Partida;
import java.io.PrintStream;
import java.util.Objects;

/**
 * Vista ASCII mínima: no usa ANSI, colores, JavaFX ni dependencias gráficas.
 * Es una representación del estado; no decide las reglas del combate.
 */
public final class RenderizadorAscii {
    private static final int ANCHO = 52;

    /** Dibuja una vista puntual de la partida y las órdenes disponibles. */
    public void dibujar(Partida partida, PrintStream salida) {
        Objects.requireNonNull(partida);
        Objects.requireNonNull(salida);

        String borde = "+" + "-".repeat(ANCHO + 2) + "+";
        salida.println();
        salida.println(borde);
        fila(salida, "ARENA JAVA  /  " + partida.escenario().nombre().toUpperCase()
                + "  /  TURNO " + partida.turno());
        salida.println(borde);
        mostrarJugador(salida, "TU", partida.heroe(), true);
        mostrarJugador(salida, "RIVAL", partida.rival(), false);
        salida.println(borde);
        if (partida.activa()) {
            partida.escenario().aviso(partida.turno()).ifPresent(texto -> fila(salida, "AVISO: " + texto));
            fila(salida, "1 Atacar  2 Defender  3 Especial (-3 energia)");
            boolean salto = partida.escenario().impedimento(Accion.SALTAR).isEmpty();
            boolean huida = partida.escenario().impedimento(Accion.HUIR).isEmpty();
            fila(salida, "4 Saltar" + (salto ? "" : " [X]")
                    + "   5 Huir" + (huida ? "" : " [X]") + "   0 Salir");
        } else {
            fila(salida, "PARTIDA TERMINADA: " + partida.estado());
        }
        salida.println(borde);
    }

    private void mostrarJugador(PrintStream salida, String etiqueta, Jugador jugador, boolean energia) {
        String linea = String.format("%-5s %-10.10s HP %2d/%2d [%s]",
                etiqueta, jugador.nombre(), jugador.vida(), jugador.vidaMaxima(),
                barra(jugador.vida(), jugador.vidaMaxima()));
        if (energia) {
            linea += String.format(" EN %d/%d", jugador.energia(), jugador.energiaMaxima());
        }
        fila(salida, linea);
    }

    private String barra(int actual, int maximo) {
        int llenas = (int) ((long) actual * 10 / maximo);
        return "#".repeat(llenas) + ".".repeat(10 - llenas);
    }

    private void fila(PrintStream salida, String contenido) {
        salida.printf("| %-" + ANCHO + "." + ANCHO + "s |%n", contenido);
    }
}
