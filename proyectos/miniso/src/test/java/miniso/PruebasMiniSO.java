package miniso;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Random;
import java.util.Scanner;
import miniso.aplicacion.EventoTick;
import miniso.aplicacion.PlanificadorCola;
import miniso.aplicacion.PlanificadorPrioridad;
import miniso.aplicacion.SimuladorSO;
import miniso.aplicacion.SistemaArchivosVirtual;
import miniso.dominio.EstadoProceso;
import miniso.dominio.Proceso;
import miniso.estructuras.ColaCircular;
import miniso.estructuras.MonticuloPrioridad;
import miniso.presentacion.ConsolaMiniSO;

/** Pruebas deterministas sin bibliotecas: java -cp out miniso.PruebasMiniSO. */
public final class PruebasMiniSO {
    private static int comprobaciones;

    private PruebasMiniSO() {}

    public static void main(String[] args) {
        colaCircular();
        monticulo();
        estructurasAleatorias();
        transiciones();
        fcfs();
        roundRobin();
        prioridad();
        entradaSalida();
        memoriaYRechazos();
        arbolVirtual();
        consola();
        System.out.println("MiniSO: " + comprobaciones + " comprobaciones correctas.");
    }

    private static void verificar(boolean condicion, String mensaje) {
        if (!condicion) throw new AssertionError(mensaje);
        comprobaciones++;
    }

    private static void debeFallar(Runnable accion, String mensaje) {
        try {
            accion.run();
            throw new AssertionError(mensaje);
        } catch (IllegalArgumentException | IllegalStateException esperado) {
            comprobaciones++;
        }
    }

    private static void colaCircular() {
        ColaCircular<Integer> cola = new ColaCircular<>();
        verificar(cola.estaVacia(), "cola empieza vacia");
        for (int i = 0; i < 12; i++) cola.encolar(i);
        for (int i = 0; i < 5; i++) verificar(cola.desencolar() == i, "FIFO inicial " + i);
        for (int i = 12; i < 30; i++) cola.encolar(i);
        verificar(cola.cantidad() == 25, "cantidad tras girar y crecer");
        for (int i = 5; i < 30; i++) verificar(cola.desencolar() == i, "FIFO con giro " + i);
        verificar(cola.estaVacia(), "cola agotada");
    }

    private static void monticulo() {
        MonticuloPrioridad<String> heap = new MonticuloPrioridad<>();
        heap.insertar("primero-5", 5);
        heap.insertar("bajo", 1);
        heap.insertar("alto", 9);
        heap.insertar("segundo-5", 5);
        for (int i = 0; i < 12; i++) heap.insertar("extra-" + i, 0);
        verificar(heap.cantidad() == 16, "heap crece");
        verificar(heap.extraer().equals("alto"), "mayor prioridad primero");
        verificar(heap.extraer().equals("primero-5"), "FIFO desempate uno");
        verificar(heap.extraer().equals("segundo-5"), "FIFO desempate dos");
        verificar(heap.extraer().equals("bajo"), "baja prioridad despues");
        for (int i = 0; i < 12; i++) verificar(heap.extraer().equals("extra-" + i), "heap estable " + i);
        verificar(heap.estaVacio(), "heap agotado");
        debeFallar(() -> heap.insertar("ilegal", 10), "prioridad ilegal");
    }

    /**
     * Pruebas diferenciales: miles de operaciones sobre estructuras propias
     * se comparan contra estructuras estándar usadas SOLO como oráculo.
     * La semilla fija hace reproducible cualquier fallo.
     */
    private static void estructurasAleatorias() {
        Random azar = new Random(20261031);
        ColaCircular<Integer> propia = new ColaCircular<>();
        ArrayDeque<Integer> referencia = new ArrayDeque<>();
        for (int i = 0; i < 2000; i++) {
            if (referencia.isEmpty() || azar.nextBoolean()) {
                propia.encolar(i);
                referencia.addLast(i);
            } else {
                verificar(propia.desencolar().equals(referencia.removeFirst()), "cola aleatoria " + i);
            }
            verificar(propia.cantidad() == referencia.size(), "cantidad de cola " + i);
        }

        record Referencia(int id, int prioridad) {}
        PriorityQueue<Referencia> esperados = new PriorityQueue<>(Comparator
                .comparingInt(Referencia::prioridad).reversed()
                .thenComparingInt(Referencia::id));
        MonticuloPrioridad<Integer> heap = new MonticuloPrioridad<>();
        for (int i = 0; i < 2000; i++) {
            if (esperados.isEmpty() || azar.nextBoolean()) {
                int prioridad = azar.nextInt(10);
                heap.insertar(i, prioridad);
                esperados.add(new Referencia(i, prioridad));
            } else {
                verificar(heap.extraer().equals(esperados.remove().id()), "monticulo aleatorio " + i);
            }
            verificar(heap.cantidad() == esperados.size(), "cantidad de monticulo " + i);
        }
        while (!esperados.isEmpty()) {
            verificar(heap.extraer().equals(esperados.remove().id()), "vaciado final del monticulo");
        }
        verificar(heap.estaVacio(), "monticulo aleatorio agotado");
    }

    private static void transiciones() {
        Proceso p = new Proceso(1, "prueba", 2, 5, 4);
        verificar(p.estado() == EstadoProceso.NUEVO, "creado nuevo");
        debeFallar(p::comenzarCpu, "nuevo no puede ejecutar");
        p.ponerListo();
        debeFallar(p::ponerListo, "no duplicar cola de listos");
        p.comenzarCpu();
        p.solicitarIo(2);
        debeFallar(p::ponerListo, "E/S pendiente impide despertar");
        verificar(!p.avanzarIo(), "E/S no termino antes de tiempo");
        verificar(p.avanzarIo(), "E/S termina en dos ticks");
        p.ponerListo();
        p.comenzarCpu();
        p.consumirCpu();
        p.consumirCpu();
        verificar(p.estado() == EstadoProceso.TERMINADO, "terminado");
        debeFallar(p::ponerListo, "no resucitar procesos");
        debeFallar(p::consumirCpu, "no ejecutar terminados");
        debeFallar(() -> new Proceso(0, "invalido", 1, 1, 1), "PID cero invalido");
    }

    private static List<Long> ejecutar(SimuladorSO so, int ticks) {
        List<Long> secuencia = new ArrayList<>();
        for (int i = 0; i < ticks; i++) secuencia.add(so.avanzarTick().pid());
        return secuencia;
    }

    private static void fcfs() {
        SimuladorSO so = new SimuladorSO(PlanificadorCola.fcfs(), 16);
        so.crearProceso("largo", 3, 0, 8);
        so.crearProceso("corto", 1, 9, 4);
        verificar(ejecutar(so, 4).equals(List.of(1L, 1L, 1L, 2L)), "FCFS no expropia CPU");
        verificar(so.memoriaUsadaKiB() == 0, "libera memoria al terminar");
        verificar(so.avanzarTick().tipo() == EventoTick.Tipo.OCIOSO, "CPU ociosa");
    }

    private static void roundRobin() {
        SimuladorSO so = new SimuladorSO(PlanificadorCola.roundRobin(2), 16);
        so.crearProceso("largo", 4, 9, 4);
        so.crearProceso("corto", 3, 0, 4);
        verificar(ejecutar(so, 7).equals(List.of(1L, 1L, 2L, 2L, 1L, 1L, 2L)),
                "RR rota cada dos ticks, independiente de prioridad");
        verificar(so.memoriaUsadaKiB() == 0, "RR libero memoria");
        debeFallar(() -> PlanificadorCola.roundRobin(0), "quantum cero no permitido");
    }

    private static void prioridad() {
        SimuladorSO so = new SimuladorSO(new PlanificadorPrioridad(), 16);
        so.crearProceso("baja", 1, 0, 1);
        so.crearProceso("alta1", 2, 9, 1);
        so.crearProceso("alta2", 1, 9, 1);
        verificar(ejecutar(so, 4).equals(List.of(2L, 2L, 3L, 1L)),
                "prioridad no expropiativa y FIFO en empates");
    }

    private static void entradaSalida() {
        SimuladorSO so = new SimuladorSO(PlanificadorCola.fcfs(), 10);
        so.crearProceso("p1", 3, 0, 7);
        so.crearProceso("p2", 1, 0, 2);
        verificar(so.avanzarTick().pid() == 1, "p1 inicia CPU");
        verificar(so.solicitarIo(2).exito(), "p1 se bloquea");
        verificar(so.pidEnCpu() == 0, "E/S libera CPU");
        verificar(so.listarProcesos().get(0).estado() == EstadoProceso.BLOQUEADO, "p1 bloqueado");
        verificar(so.memoriaUsadaKiB() == 9, "bloqueado conserva memoria");
        verificar(so.avanzarTick().pid() == 2, "p2 avanza durante E/S");
        verificar(so.avanzarTick().tipo() == EventoTick.Tipo.OCIOSO, "p1 sigue bloqueado un tick");
        verificar(so.listarProcesos().get(0).estado() == EstadoProceso.LISTO, "p1 despierta al final");
        verificar(ejecutar(so, 2).equals(List.of(1L, 1L)), "p1 retoma su trabajo");
        verificar(so.memoriaUsadaKiB() == 0, "E/S no produce fuga de memoria");
        verificar(!so.solicitarIo(1).exito(), "no bloquear sin proceso ejecutando");
    }

    private static void memoriaYRechazos() {
        SimuladorSO so = new SimuladorSO(PlanificadorCola.fcfs(), 5);
        verificar(so.crearProceso("A", 1, 5, 4).pid() == 1, "primer PID");
        verificar(!so.crearProceso("B", 1, 5, 2).aceptado(), "sin RAM suficiente");
        verificar(so.memoriaUsadaKiB() == 4, "rechazo no altera memoria");
        verificar(so.crearProceso("C", 1, 5, 1).pid() == 2, "rechazo no consume PID");
        verificar(!so.crearProceso("", 1, 5, 1).aceptado(), "rechazo por nombre");
        verificar(!so.crearProceso("Z", -1, 5, 1).aceptado(), "rechazo por CPU");
        verificar(!so.crearProceso("Z", 1, 10, 1).aceptado(), "rechazo por prioridad");
        verificar(!so.solicitarIo(0).exito(), "rechazo E/S no valida");
        verificar(so.memoriaUsadaKiB() == 5, "sin corrupcion de memoria");
        verificar(so.listarProcesos().size() == 2, "sin procesos fantasma");
        ejecutar(so, 2);
        verificar(so.memoriaUsadaKiB() == 0, "memoria recuperada");
        verificar(so.crearProceso("D", 1, 0, 5).aceptado(), "memoria reutilizable");
    }

    private static void arbolVirtual() {
        SistemaArchivosVirtual fs = new SistemaArchivosVirtual();
        verificar(fs.arbol().equals("/\n"), "raiz vacia");
        verificar(fs.crearDirectorio("/docs").exito(), "mkdir docs");
        verificar(fs.crearDirectorio("/docs/java").exito(), "mkdir anidado");
        verificar(fs.escribir("/docs/java/nota.txt", "ñ").exito(), "write anidado");
        verificar(fs.leer("/docs/java/nota.txt").orElseThrow().equals("ñ"), "cat unicode");
        verificar(fs.bytesTotales() == 2, "tamano real UTF-8");
        verificar(fs.arbol().contains("    nota.txt (2 B)"), "arbol recursivo");
        verificar(fs.escribir("/docs/java/nota.txt", "hola").exito(), "reemplazo de archivo");
        verificar(fs.bytesTotales() == 4, "tamano tras reemplazar");
        verificar(!fs.crearDirectorio("/docs").exito(), "no duplica directorio");
        verificar(!fs.escribir("/docs", "no").exito(), "no reemplaza directorio");
        verificar(!fs.crearDirectorio("/inexistente/hijo").exito(), "sin padre no existe");
        verificar(!fs.crearDirectorio("/docs/../escape").exito(), "no permite ..");
        verificar(!fs.escribir("/docs//doble", "x").exito(), "no permite //");
        verificar(fs.leer("/docs/java/desconocido").isEmpty(), "archivo ausente");
        verificar(fs.bytesTotales() == 4, "rechazos no corrompen arbol");
        debeFallar(() -> new miniso.dominio.DirectorioVirtual(".."), "invariante directorio directo");
        debeFallar(() -> new miniso.dominio.ArchivoVirtual("/escape", "x"), "invariante archivo directo");
    }

    private static void consola() {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        String guion = "2\ncrear uno 3 5 4\nps\ntick 2\nmkdir /docs\nwrite /docs/hola hola mundo\n"
                + "cat /docs/hola\ntree\nestado\nsalir\n";
        new ConsolaMiniSO(new Scanner(guion), new PrintStream(bytes, true, StandardCharsets.UTF_8))
                .iniciar();
        String texto = bytes.toString(StandardCharsets.UTF_8);
        verificar(texto.contains("Round Robin"), "seleccion RR por consola");
        verificar(texto.contains("PID 1 admitido"), "alta por consola");
        verificar(texto.contains("hola mundo"), "archivo por consola");
        verificar(texto.contains("t=2"), "reloj por consola");
        verificar(texto.contains("Nada del SO anfitrion"), "salida segura");
    }
}
