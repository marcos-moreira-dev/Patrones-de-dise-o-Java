package arena;

import arena.aplicacion.Comandos;
import arena.aplicacion.ResultadoAccion;
import arena.aplicacion.ServicioPartida;
import arena.dominio.Accion;
import arena.dominio.EventoCombate;
import arena.dominio.Jugador;
import arena.dominio.MotorCombate;
import arena.dominio.Partida;
import arena.dominio.ReglasEscenario;
import arena.dominio.ataques.Ataque;
import arena.dominio.ataques.AtaqueBasico;
import arena.dominio.ataques.AtaquePotenciado;
import arena.dominio.escenarios.ReglasArena;
import arena.dominio.escenarios.ReglasBosque;
import arena.presentacion.ConsolaArena;
import arena.presentacion.RenderizadorAscii;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Pruebas ejecutables con Java puro, sin JUnit ni dependencia de Maven.
 * Se lanzan con: java -cp out arena.PruebasArena
 */
public final class PruebasArena {
    private static int comprobaciones;

    private PruebasArena() {}

    public static void main(String[] args) {
        condicionesDeArena();
        fronteraDelDominio();
        combateEnBosque();
        energiaYEspecial();
        defensa();
        victoriaYDerrota();
        huida();
        observadores();
        invariantes();
        presentacion();
        System.out.println("Arena Java: " + comprobaciones + " comprobaciones correctas.");
    }

    private static ServicioPartida partida(ReglasEscenario escenario, int vidaHeroe, int energia, int vidaRival) {
        return new ServicioPartida(
                new Partida(new Jugador("Heroe", vidaHeroe, energia),
                        new Jugador("Rival", vidaRival, 0), escenario),
                new MotorCombate(limite -> 0));
    }

    private static void condicionesDeArena() {
        ServicioPartida servicio = partida(new ReglasArena(), 20, 6, 20);
        comprobar(new Comandos.Huir().ejecutar(servicio) instanceof ResultadoAccion.Rechazada,
                "Arena prohibe huir");
        comprobar(new Comandos.Saltar().ejecutar(servicio) instanceof ResultadoAccion.Rechazada,
                "Arena prohibe saltar");
        comprobar(servicio.partida().turno() == 1, "Los rechazos no consumen turno");
        comprobar(servicio.partida().heroe().vida() == 20, "Rechazo sin daño");
    }

    private static void fronteraDelDominio() {
        ServicioPartida servicio = partida(new ReglasArena(), 20, 2, 20);
        MotorCombate motor = new MotorCombate(limite -> 0);
        try {
            motor.resolver(servicio.partida(), Accion.HUIR);
            throw new AssertionError("Se pudo eludir el reglamento llamando directamente al motor");
        } catch (IllegalStateException esperado) {
            comprobaciones++;
        }
        try {
            motor.resolver(servicio.partida(), Accion.ESPECIAL);
            throw new AssertionError("Se pudo omitir la validacion de energia");
        } catch (IllegalStateException esperado) {
            comprobaciones++;
        }
        comprobar(servicio.partida().estado() == Partida.Estado.ACTIVA,
                "Una llamada directa invalida no cambia el resultado");
        comprobar(servicio.partida().heroe().energia() == 2,
                "Una llamada directa invalida no gasta recursos");
    }

    private static void combateEnBosque() {
        ServicioPartida salto = partida(new ReglasBosque(), 20, 6, 30);
        salto.ejecutar(Accion.ATACAR);
        salto.ejecutar(Accion.ATACAR);
        comprobar(salto.partida().turno() == 3, "Se aproxima el turno de raices");
        comprobar(salto.partida().escenario().aviso(3).isPresent(), "El peligro se anuncia");
        salto.ejecutar(Accion.SALTAR);
        comprobar(salto.partida().heroe().vida() == 11, "Saltar evita 3 de daño de raices");
        comprobar(salto.partida().turno() == 4, "Saltar gasta un turno");

        ServicioPartida sinSalto = partida(new ReglasBosque(), 20, 6, 30);
        sinSalto.ejecutar(Accion.ATACAR);
        sinSalto.ejecutar(Accion.ATACAR);
        sinSalto.ejecutar(Accion.DEFENDER);
        comprobar(sinSalto.partida().heroe().vida() == 10, "Las raices golpean al no saltar");
    }

    private static void energiaYEspecial() {
        ServicioPartida servicio = partida(new ReglasArena(), 20, 6, 30);
        comprobar(new Comandos.Especial().ejecutar(servicio) instanceof ResultadoAccion.Ejecutada,
                "El golpe especial puede ejecutarse");
        comprobar(servicio.partida().heroe().energia() == 3, "Especial cuesta tres puntos");
        comprobar(servicio.partida().rival().vida() == 23, "Decorator añade 3 al daño base 4");
        Ataque base = new AtaqueBasico(4);
        Ataque mejorado = new AtaquePotenciado(base, 3);
        comprobar(base.danio() == 4 && mejorado.danio() == 7, "Decorator no muta el original");

        ServicioPartida sinEnergia = partida(new ReglasArena(), 20, 2, 30);
        comprobar(sinEnergia.ejecutar(Accion.ESPECIAL) instanceof ResultadoAccion.Rechazada,
                "Sin energia se rechaza sin excepcion");
        comprobar(sinEnergia.partida().turno() == 1 && sinEnergia.partida().heroe().energia() == 2,
                "No se consumen recursos al rechazar");
    }

    private static void defensa() {
        ServicioPartida servicio = partida(new ReglasArena(), 20, 6, 20);
        servicio.partida().heroe().consumirEnergia(2);
        servicio.ejecutar(Accion.DEFENDER);
        comprobar(servicio.partida().heroe().vida() == 19, "Defensa reduce daño enemigo de 3 a 1");
        comprobar(servicio.partida().heroe().energia() == 5, "Defensa recupera 1 de energia");
    }

    private static void victoriaYDerrota() {
        ServicioPartida victoria = partida(new ReglasArena(), 20, 6, 4);
        victoria.ejecutar(Accion.ATACAR);
        comprobar(victoria.partida().estado() == Partida.Estado.VICTORIA, "Atacar puede ganar");
        comprobar(victoria.partida().heroe().vida() == 20, "No hay respuesta del rival vencido");
        comprobar(victoria.ejecutar(Accion.ATACAR) instanceof ResultadoAccion.Rechazada,
                "No hay turnos despues de finalizar");

        ServicioPartida derrota = partida(new ReglasArena(), 2, 6, 20);
        derrota.ejecutar(Accion.ATACAR);
        comprobar(derrota.partida().estado() == Partida.Estado.DERROTA, "El rival puede vencer");
        comprobar(derrota.partida().heroe().vida() == 0, "La vida no es negativa");
    }

    private static void huida() {
        ServicioPartida servicio = partida(new ReglasBosque(), 20, 6, 20);
        comprobar(servicio.ejecutar(Accion.HUIR) instanceof ResultadoAccion.Ejecutada,
                "En bosque se permite huir");
        comprobar(servicio.partida().estado() == Partida.Estado.HUIDA, "Huir termina el encuentro");
        comprobar(servicio.partida().heroe().vida() == 20, "No hay respuesta tras la huida");
    }

    private static void observadores() {
        ServicioPartida servicio = partida(new ReglasArena(), 20, 6, 20);
        List<EventoCombate> eventos = new ArrayList<>();
        servicio.suscribir(eventos::add);
        servicio.ejecutar(Accion.HUIR);
        comprobar(eventos.isEmpty(), "Un rechazo no publica eventos");
        servicio.ejecutar(Accion.ATACAR);
        comprobar(eventos.size() == 2, "Observer recibe ataque y respuesta");
        comprobar(eventos.get(0).tipo() == EventoCombate.Tipo.ATAQUE
                && eventos.get(1).tipo() == EventoCombate.Tipo.RESPUESTA,
                "Los observadores reciben tipos sin analizar cadenas");
    }

    private static void invariantes() {
        Jugador jugador = new Jugador("A", 5, 2);
        jugador.recibirDanio(999);
        comprobar(jugador.vida() == 0, "Vida protegida ante daño excesivo");
        comprobar(!jugador.consumirEnergia(3), "No gasta energia que no tiene");
        jugador.recuperarEnergia(999);
        comprobar(jugador.energia() == 2, "La energia respeta el maximo");
        try {
            jugador.recibirDanio(-1);
            throw new AssertionError("Faltaba la excepcion para parametro imposible");
        } catch (IllegalArgumentException esperado) {
            comprobaciones++;
        }
    }

    private static void presentacion() {
        ServicioPartida servicio = partida(new ReglasBosque(), 20, 6, 20);
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        PrintStream salida = new PrintStream(bytes, true, StandardCharsets.UTF_8);
        new RenderizadorAscii().dibujar(servicio.partida(), salida);
        String imagen = bytes.toString(StandardCharsets.UTF_8);
        comprobar(imagen.contains("ARENA JAVA") && imagen.contains("Rival"),
                "La vista ASCII muestra escenario y personajes");
        comprobar(imagen.contains("#"), "La vista dibuja barras de vida");

        ByteArrayOutputStream consola = new ByteArrayOutputStream();
        new ConsolaArena(new Scanner("2\n5\n"), new PrintStream(consola, true,
                StandardCharsets.UTF_8)).iniciar();
        comprobar(consola.toString(StandardCharsets.UTF_8).contains("Resultado final: HUIDA"),
                "La consola permite elegir bosque y huir");

        ByteArrayOutputStream finEntrada = new ByteArrayOutputStream();
        new ConsolaArena(new Scanner("2\n"), new PrintStream(finEntrada, true,
                StandardCharsets.UTF_8)).iniciar();
        String texto = finEntrada.toString(StandardCharsets.UTF_8);
        comprobar(texto.contains("Entrada finalizada") && !texto.contains("Resultado final: ACTIVA"),
                "EOF interrumpe sin anunciar un resultado falso");
    }

    private static void comprobar(boolean condicion, String descripcion) {
        if (!condicion) {
            throw new AssertionError(descripcion);
        }
        comprobaciones++;
    }
}
