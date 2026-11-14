package liminal.laboratorios.relaciones;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

/** Verifica invariantes de asociación, agregación y composición UML. */
public final class PruebaRelacionesUml {
    private PruebaRelacionesUml() {}

    public static void ejecutar() {
        asociacionNoDuplicaObjetos();
        agregacionNoPoseeExploradores();
        composicionTieneDuenoExclusivoYBajaLogica();
        demostracionPorConsolaEsReproducible();
    }

    private static void asociacionNoDuplicaObjetos() {
        Habitacion habitacion = new Habitacion("H-01", "Pasillo amarillo");
        MapaLiminal mapa = new MapaLiminal("M-01");
        mapa.agregarHabitacion(habitacion);
        Explorador explorador = new Explorador("EX-01", mapa, "H-01");
        comprobar(mapa.buscar("H-01") == habitacion, "El mapa debe conservar la referencia");
        comprobar(explorador.ubicacion() == habitacion, "La asociación no crea una copia");
        comprobar(explorador.mapa() == mapa, "El explorador conserva su mapa");
        comprobar(ServicioConsulta.describirUbicacion(explorador).contains("H-01"),
                "Dependencia funcional");
        esperarFallo(IllegalArgumentException.class,
                () -> mapa.agregarHabitacion(new Habitacion("H-01", "Otra habitación")));
        comprobar(mapa.cantidadHabitaciones() == 1, "Alta duplicada no modifica el mapa");
        esperarFallo(IllegalArgumentException.class,
                () -> new Explorador("EX-02", mapa, "H-99"));
        esperarFallo(IllegalArgumentException.class, () -> new Habitacion(" ", "Pasillo"));
    }

    private static void agregacionNoPoseeExploradores() {
        MapaLiminal mapa = new MapaLiminal("M-01");
        Habitacion habitacion = new Habitacion("H-01", "Oficina");
        mapa.agregarHabitacion(habitacion);
        Explorador e = new Explorador("EX-01", mapa, "H-01");
        EquipoExploracion uno = new EquipoExploracion("Luces");
        EquipoExploracion dos = new EquipoExploracion("Búsqueda");
        uno.agregar(e);
        dos.agregar(e);
        comprobar(uno.contiene(e) && dos.contiene(e),
                "Un explorador puede estar en dos equipos");
        esperarFallo(IllegalArgumentException.class, () -> uno.agregar(e));
        comprobar(uno.cantidadIntegrantes() == 1,
                "No se duplican referencias en un equipo");
        uno.disolver();
        comprobar(!uno.activo() && !uno.contiene(e),
                "Equipo disuelto sin integrantes");
        comprobar(dos.contiene(e) && e.ubicacion() == habitacion,
                "El explorador sigue existiendo");
        esperarFallo(IllegalStateException.class, () -> uno.agregar(e));
    }

    private static void composicionTieneDuenoExclusivoYBajaLogica() {
        RegistroExpedicion uno = new RegistroExpedicion("R-01");
        RegistroExpedicion dos = new RegistroExpedicion("R-02");
        VistaEntrada a = uno.anotar("E-01", "Se escucha una alarma");
        VistaEntrada b = dos.anotar("E-01", "Se escucha una alarma");
        comprobar(a != b, "Mismo ID local no implica misma instancia");
        comprobar(a.idRegistro().equals("R-01") && b.idRegistro().equals("R-02"),
                "Propietarios distintos por construcción");
        comprobar(uno.consultarActivas().get(0) == a, "La lista no clona partes");
        InformeExpedicion informe = new InformeExpedicion(a);
        comprobar(informe.entrada() == a,
                "El informe solo retiene una referencia");
        comprobar(informe.consultar().equals("Se escucha una alarma"),
                "Lectura activa válida");
        esperarFallo(IllegalArgumentException.class,
                () -> uno.anotar("E-01", "Duplicado"));
        comprobar(uno.entradasActivas() == 1, "Fallo no altera el registro");
        esperarFallo(IllegalArgumentException.class,
                () -> uno.anotar("E-02", "  "));
        comprobar(uno.entradasActivas() == 1, "Texto inválido no crea partes");
        uno.cerrar();
        comprobar(!uno.abierto() && uno.entradasActivas() == 0,
                "Cierre elimina partes del modelo");
        comprobar(!a.activa() && informe.entrada() == a,
                "Referencia existente, parte inactiva");
        comprobar(informe.consultar().equals("entrada no disponible"),
                "No se revive entrada eliminada");
        comprobar(b.activa() && dos.entradasActivas() == 1,
                "Otro registro independiente");
        esperarFallo(IllegalStateException.class,
                () -> uno.anotar("E-03", "No permitido"));
        comprobar(uno.entradasActivas() == 0,
                "El registro cerrado no se modifica");
        uno.cerrar();
    }

    private static void demostracionPorConsolaEsReproducible() {
        ByteArrayOutputStream a = new ByteArrayOutputStream();
        ByteArrayOutputStream b = new ByteArrayOutputStream();
        ClienteRelacionesUml.ejecutar(
                new PrintStream(a, true, StandardCharsets.UTF_8));
        ClienteRelacionesUml.ejecutar(
                new PrintStream(b, true, StandardCharsets.UTF_8));
        String salida = a.toString(StandardCharsets.UTF_8);
        comprobar(salida.equals(b.toString(StandardCharsets.UTF_8)),
                "Consola reproducible");
        for (String palabra : new String[]{
                "ASOCIACIÓN:", "AGREGACIÓN:", "COMPOSICIÓN:",
                "CIERRE:", "REFERENCIA Java todavía existe: true",
                "ENTRADA activa: false"}) {
            comprobar(salida.contains(palabra),
                    "Falta la prueba visual: " + palabra);
        }
    }

    private static void comprobar(boolean condicion, String mensaje) {
        if (!condicion) throw new AssertionError(mensaje);
    }

    private static void esperarFallo(
            Class<? extends RuntimeException> tipo, Runnable accion) {
        try {
            accion.run();
        } catch (RuntimeException ex) {
            if (tipo.isInstance(ex)) return;
            throw new AssertionError("Excepción distinta a la esperada", ex);
        }
        throw new AssertionError("La operación inválida no fue rechazada");
    }
}
