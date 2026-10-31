package miniso.presentacion;

import java.io.PrintStream;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;
import miniso.aplicacion.EstadisticaProceso;
import miniso.aplicacion.EventoTick;
import miniso.aplicacion.ExperimentoPlanificacion;
import miniso.aplicacion.Planificador;
import miniso.aplicacion.PlanificadorCola;
import miniso.aplicacion.PlanificadorPrioridad;
import miniso.aplicacion.ResultadoAlta;
import miniso.aplicacion.ResultadoOperacion;
import miniso.aplicacion.SimuladorSO;
import miniso.aplicacion.SistemaArchivosVirtual;
import miniso.aplicacion.VistaProceso;

/**
 * Adaptador de entrada/salida: interpreta órdenes textuales y muestra resultados.
 * No implementa reglas de CPU, memoria ni estructura del árbol virtual.
 */
public final class ConsolaMiniSO {
    private final Scanner entrada;
    private final PrintStream salida;

    public ConsolaMiniSO(Scanner entrada, PrintStream salida) {
        this.entrada = entrada;
        this.salida = salida;
    }

    /** CU-SO00: inicia una sesión sin crear procesos reales del ordenador. */
    public void iniciar() {
        salida.println("MiniSO | simulacion educativa, NO es un SO real");
        salida.println("Planificador: 1=FCFS, 2=Round Robin (q=2), 3=Prioridad");
        if (!entrada.hasNextLine()) return;
        Planificador politica = switch (entrada.nextLine().trim()) {
            case "2" -> PlanificadorCola.roundRobin(2);
            case "3" -> new PlanificadorPrioridad();
            default -> PlanificadorCola.fcfs();
        };
        SimuladorSO so = new SimuladorSO(politica, 32);
        SistemaArchivosVirtual archivos = new SistemaArchivosVirtual();
        salida.println("Politica: " + so.politica() + "; memoria ficticia: 32 KiB");
        ayuda();
        while (entrada.hasNextLine()) {
            salida.print("miniso> ");
            String linea = entrada.nextLine().strip();
            if (linea.isEmpty()) continue;
            String[] partes = linea.split("\\s+", 2);
            String orden = partes[0].toLowerCase(java.util.Locale.ROOT);
            String argumentos = partes.length > 1 ? partes[1].strip() : "";
            if (orden.equals("salir")) {
                salida.println("Fin de la simulacion. Nada del SO anfitrion fue modificado.");
                return;
            }
            ejecutar(so, archivos, orden, argumentos);
        }
    }

    private void ejecutar(SimuladorSO so, SistemaArchivosVirtual archivos, String orden, String args) {
        try {
            switch (orden) {
                case "ayuda" -> ayuda();
                case "crear" -> {
                    String[] p = args.split("\\s+");
                    if (p.length != 4) { salida.println("Uso: crear <nombre> <cpu> <prioridad> <KiB>"); break; }
                    ResultadoAlta resultado = so.crearProceso(p[0], Integer.parseInt(p[1]),
                            Integer.parseInt(p[2]), Integer.parseInt(p[3]));
                    salida.println(resultado.detalle());
                }
                case "tick" -> {
                    int cantidad = args.isEmpty() ? 1 : Integer.parseInt(args);
                    if (cantidad < 1 || cantidad > 1000) {
                        salida.println("Elige entre 1 y 1000 ticks"); break;
                    }
                    for (int i = 0; i < cantidad; i++) {
                        EventoTick evento = so.avanzarTick();
                        salida.println("[t=" + evento.reloj() + "] " + evento.detalle());
                    }
                }
                case "io" -> mostrar(so.solicitarIo(Integer.parseInt(args)));
                case "ps" -> {
                    salida.println("PID | NOMBRE | ESTADO | CPU | PRIORIDAD | KiB | E/S");
                    for (VistaProceso p : so.listarProcesos()) {
                        salida.printf("%d | %s | %s | %d | %d | %d | %d%n", p.pid(), p.nombre(),
                                p.estado(), p.cpuRestante(), p.prioridad(), p.memoriaKiB(), p.esperaIo());
                    }
                }
                case "estado" -> salida.println("t=" + so.reloj() + ", CPU PID=" + so.pidEnCpu()
                        + ", memoria=" + so.memoriaUsadaKiB() + "/" + so.capacidadKiB() + " KiB");
                case "metricas" -> mostrarMetricas(so);
                case "comparar" -> compararPoliticas();
                case "mkdir" -> mostrar(archivos.crearDirectorio(args));
                case "write" -> {
                    String[] p = args.split("\\s+", 2);
                    if (p.length != 2) { salida.println("Uso: write /ruta/archivo texto"); break; }
                    mostrar(archivos.escribir(p[0], p[1]));
                }
                case "cat" -> salida.println(archivos.leer(args).orElse("Archivo inexistente o ruta invalida"));
                case "tree" -> salida.print(archivos.arbol());
                default -> salida.println("Orden desconocida: " + orden + ". Escribe ayuda.");
            }
        } catch (NumberFormatException e) {
            salida.println("Se esperaba un numero entero valido. Escribe ayuda.");
        }
    }

    private void mostrar(ResultadoOperacion resultado) {
        salida.println((resultado.exito() ? "OK: " : "RECHAZADO: ") + resultado.detalle());
    }

    private void mostrarMetricas(SimuladorSO so) {
        List<EstadisticaProceso> datos = so.estadisticas();
        salida.println("PID | NOMBRE | CPU | LISTO | E/S | RESPUESTA | RETORNO");
        for (EstadisticaProceso p : datos) {
            salida.printf("%d | %s | %d | %d | %d | %s | %s%n", p.pid(), p.nombre(),
                    p.ticksCpu(), p.ticksListo(), p.ticksIo(), pendiente(p.respuesta()),
                    pendiente(p.retorno()));
        }
        salida.println("Alternancias de PID en ticks consecutivos: " + so.cambiosDeProceso());
        salida.println("Ticks con CPU ocupada: " + so.ticksCpuOcupados() + "/" + so.reloj());
        salida.println("- significa que el proceso aun no tiene esa metrica disponible.");
    }

    private static String pendiente(long valor) { return valor < 0 ? "-" : Long.toString(valor); }

    private void compararPoliticas() {
        salida.println("Carga identica: editor (6, p2), musica (2, p9), juego (4, p5). Llegada t=0.");
        salida.println("POLITICA | ESPERA MEDIA | RESPUESTA MEDIA | RETORNO MEDIO | ALTERNANCIAS");
        for (ExperimentoPlanificacion.Resultado r : ExperimentoPlanificacion.comparar()) {
            salida.printf(Locale.ROOT, "%s | %.2f | %.2f | %.2f | %d%n", r.politica(),
                    r.esperaMedia(), r.respuestaMedia(), r.retornoMedio(), r.alternancias());
        }
        salida.println("Los valores son ticks simulados, no tiempos del hardware.");
    }

    private void ayuda() {
        salida.println("crear <nombre> <cpu> <prioridad 0..9> <KiB> | ps | tick [n] | io <ticks> | estado");
        salida.println("metricas | comparar  (misma carga con FCFS, Round Robin y prioridad)");
        salida.println("mkdir /ruta | write /ruta/archivo texto | cat /ruta/archivo | tree | ayuda | salir");
        salida.println("io bloquea SOLO al proceso que esta ejecutando; empieza por hacer tick.");
    }
}
