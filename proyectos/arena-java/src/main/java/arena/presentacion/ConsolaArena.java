package arena.presentacion;

import arena.aplicacion.Comando;
import arena.aplicacion.Comandos;
import arena.aplicacion.ResultadoAccion;
import arena.aplicacion.ServicioPartida;
import arena.dominio.Jugador;
import arena.dominio.MotorCombate;
import arena.dominio.Partida;
import arena.dominio.ReglasEscenario;
import arena.dominio.escenarios.ReglasBosque;
import arena.dominio.escenarios.ReglasArena;
import arena.infraestructura.AzarJava;
import java.io.PrintStream;
import java.util.Objects;
import java.util.Optional;
import java.util.Scanner;

/**
 * Adaptador de entrada: interpreta texto y construye Command.
 * El juego es totalmente determinista al reemplazar AzarJava en pruebas.
 */
public final class ConsolaArena {
    private final Scanner entrada;
    private final PrintStream salida;
    private final RenderizadorAscii renderizador = new RenderizadorAscii();

    public ConsolaArena(Scanner entrada, PrintStream salida) {
        this.entrada = Objects.requireNonNull(entrada);
        this.salida = Objects.requireNonNull(salida);
    }

    /** Ejecuta una partida breve elegida por la persona que juega. */
    public void iniciar() {
        salida.println("=== ARENA JAVA ===");
        salida.println("Un heroe, un rival y dos mundos con reglas distintas.");
        ReglasEscenario escenario = elegirEscenario();
        if (escenario == null) {
            return;
        }
        Jugador heroe = new Jugador("Heroe", 22, 7);
        Jugador rival = new Jugador(escenario.nombreRival(), 18, 0);
        Partida partida = new Partida(heroe, rival, escenario);
        ServicioPartida servicio = new ServicioPartida(partida, new MotorCombate(new AzarJava()));
        // Observer: la interfaz escucha, pero el dominio no sabe que existe una consola.
        servicio.suscribir(texto -> salida.println("  * " + texto));
        salida.println(escenario.descripcion());

        while (partida.activa() && entrada.hasNextLine()) {
            renderizador.dibujar(partida, salida);
            salida.print("Tu orden: ");
            String opcion = entrada.nextLine().trim();
            if ("0".equals(opcion)) {
                salida.println("Partida interrumpida por la persona que juega.");
                return;
            }
            Optional<Comando> comando = interpretar(opcion);
            if (comando.isEmpty()) {
                salida.println("Opcion desconocida. El turno no avanza.");
                continue;
            }
            ResultadoAccion resultado = comando.get().ejecutar(servicio);
            switch (resultado) {
                case ResultadoAccion.Rechazada rechazo ->
                        salida.println("  ! " + rechazo.motivo() + " (sin gastar turno)");
                case ResultadoAccion.Ejecutada ejecutada -> {
                    // Los eventos ya se publicaron a traves del observador.
                }
            }
        }
        renderizador.dibujar(partida, salida);
        salida.println("Resultado final: " + partida.estado());
    }

    private ReglasEscenario elegirEscenario() {
        while (entrada.hasNextLine()) {
            salida.println("1 Arena  |  2 Bosque  |  0 Salir");
            salida.print("Escenario: ");
            switch (entrada.nextLine().trim()) {
                case "1": return new ReglasArena();
                case "2": return new ReglasBosque();
                case "0": return null;
                default: salida.println("Elige 1, 2 o 0.");
            }
        }
        return null;
    }

    private Optional<Comando> interpretar(String opcion) {
        return switch (opcion) {
            case "1" -> Optional.of(new Comandos.Atacar());
            case "2" -> Optional.of(new Comandos.Defender());
            case "3" -> Optional.of(new Comandos.Especial());
            case "4" -> Optional.of(new Comandos.Saltar());
            case "5" -> Optional.of(new Comandos.Huir());
            default -> Optional.empty();
        };
    }
}
