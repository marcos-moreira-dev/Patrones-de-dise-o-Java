package liminal.laboratorios.relaciones;

import java.io.PrintStream;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/** Laboratorio independiente de relaciones UML; no es un patrón GoF. */
public final class ClienteRelacionesUml {
    private ClienteRelacionesUml() {}

    public static void ejecutar(PrintStream salida) {
        Habitacion pasillo = new Habitacion("H-01", "Pasillo amarillo");
        MapaLiminal mapa = new MapaLiminal("MAPA-01");
        mapa.agregarHabitacion(pasillo);
        Explorador explorador = new Explorador("EX-01", mapa, "H-01");
        salida.println("ASOCIACIÓN: " + ServicioConsulta.describirUbicacion(explorador));
        salida.println("MISMA REFERENCIA: " + (explorador.ubicacion() == pasillo));

        EquipoExploracion equipoLuces = new EquipoExploracion("Luces");
        EquipoExploracion equipoBusqueda = new EquipoExploracion("Búsqueda");
        equipoLuces.agregar(explorador);
        equipoBusqueda.agregar(explorador);
        salida.println("AGREGACIÓN: explorador en dos equipos: "
                + (equipoLuces.contiene(explorador) && equipoBusqueda.contiene(explorador)));
        equipoLuces.disolver();
        salida.println("DISOLVER equipo: explorador sigue en " + explorador.ubicacion().id());
        salida.println("OTRO equipo conserva referencia: " + equipoBusqueda.contiene(explorador));

        RegistroExpedicion registro = new RegistroExpedicion("REG-01");
        VistaEntrada entrada = registro.anotar("E-01", "Se escucha una alarma");
        InformeExpedicion informe = new InformeExpedicion(entrada);
        salida.println("COMPOSICIÓN: " + registro.id() + " tiene " + registro.entradasActivas()
                + " entrada activa");
        salida.println("INFORME consulta misma instancia: " + (informe.entrada() == entrada));
        registro.cerrar();
        salida.println("CIERRE: entradas activas = " + registro.entradasActivas());
        salida.println("REFERENCIA Java todavía existe: " + (informe.entrada() == entrada));
        salida.println("ENTRADA activa: " + entrada.activa());
        salida.println("INFORME tras cierre: " + informe.consultar());
    }

    public static void main(String[] args) { ejecutar(System.out); }
}

/** La identidad de una habitación no depende de su apariencia. */
final class Habitacion {
    private final String id;
    private final String nombre;

    Habitacion(String id, String nombre) {
        this.id = Reglas.exigirTexto(id);
        this.nombre = Reglas.exigirTexto(nombre);
    }
    String id() { return id; }
    String nombre() { return nombre; }
}

/** El mapa conoce habitaciones, pero no se declara dueño de su ciclo de vida. */
final class MapaLiminal {
    private final String id;
    private final Map<String, Habitacion> habitaciones = new LinkedHashMap<>();

    MapaLiminal(String id) { this.id = Reglas.exigirTexto(id); }
    String id() { return id; }

    void agregarHabitacion(Habitacion habitacion) {
        Objects.requireNonNull(habitacion);
        if (habitaciones.putIfAbsent(habitacion.id(), habitacion) != null) {
            throw new IllegalArgumentException("ID de habitación duplicado");
        }
    }

    Habitacion buscar(String idHabitacion) {
        Habitacion habitacion = habitaciones.get(idHabitacion);
        if (habitacion == null) throw new IllegalArgumentException("Habitación fuera del mapa");
        return habitacion;
    }
    int cantidadHabitaciones() { return habitaciones.size(); }
}

/** Asociación: ubicación actual en una única habitación registrada. */
final class Explorador {
    private final String id;
    private final MapaLiminal mapa;
    private final Habitacion ubicacion;

    Explorador(String id, MapaLiminal mapa, String idHabitacion) {
        this.id = Reglas.exigirTexto(id);
        this.mapa = Objects.requireNonNull(mapa);
        this.ubicacion = mapa.buscar(Objects.requireNonNull(idHabitacion, "ID requerido"));
    }
    String id() { return id; }
    MapaLiminal mapa() { return mapa; }
    Habitacion ubicacion() { return ubicacion; }
}

/** Agregación compartida LOCAL: los exploradores sobreviven a la disolución del equipo. */
final class EquipoExploracion {
    private final String nombre;
    private final Set<Explorador> integrantes = new LinkedHashSet<>();
    private boolean activo = true;

    EquipoExploracion(String nombre) { this.nombre = Reglas.exigirTexto(nombre); }
    String nombre() { return nombre; }
    boolean activo() { return activo; }

    void agregar(Explorador explorador) {
        if (!activo) throw new IllegalStateException("Equipo disuelto");
        Objects.requireNonNull(explorador);
        if (!integrantes.add(explorador)) {
            throw new IllegalArgumentException("Explorador ya incorporado al equipo");
        }
    }
    boolean contiene(Explorador explorador) { return integrantes.contains(explorador); }
    int cantidadIntegrantes() { return integrantes.size(); }

    void disolver() {
        activo = false;
        integrantes.clear();
    }
}

/** Vista de lectura: retenerla no equivale a ser propietario de una parte compuesta. */
interface VistaEntrada {
    String id();
    String texto();
    String idRegistro();
    boolean activa();
}

/** Composición según R-09: crea, posee e invalida sus entradas al cerrar. */
final class RegistroExpedicion {
    private final String id;
    private final Map<String, EntradaRegistro> entradas = new LinkedHashMap<>();
    private boolean abierto = true;

    RegistroExpedicion(String id) { this.id = Reglas.exigirTexto(id); }
    String id() { return id; }
    boolean abierto() { return abierto; }
    int entradasActivas() { return entradas.size(); }

    VistaEntrada anotar(String idEntrada, String texto) {
        if (!abierto) throw new IllegalStateException("Registro cerrado");
        Reglas.exigirTexto(idEntrada);
        Reglas.exigirTexto(texto);
        if (entradas.containsKey(idEntrada)) {
            throw new IllegalArgumentException("ID de entrada repetido en el registro");
        }
        EntradaRegistro entrada = new EntradaRegistro(idEntrada, texto, id);
        entradas.put(idEntrada, entrada);
        return entrada;
    }

    List<VistaEntrada> consultarActivas() {
        return List.copyOf(entradas.values());
    }

    void cerrar() {
        if (!abierto) return;
        for (EntradaRegistro entrada : entradas.values()) entrada.invalidar();
        entradas.clear();
        abierto = false;
    }

    /** El constructor privado evita crear o transferir entradas desde otros registros. */
    private static final class EntradaRegistro implements VistaEntrada {
        private final String id;
        private final String texto;
        private final String idRegistro;
        private boolean activa = true;

        private EntradaRegistro(String id, String texto, String idRegistro) {
            this.id = id;
            this.texto = texto;
            this.idRegistro = idRegistro;
        }
        public String id() { return id; }
        public String texto() { return texto; }
        public String idRegistro() { return idRegistro; }
        public boolean activa() { return activa; }
        private void invalidar() { activa = false; }
    }
}

/** Asociación de consulta: el informe no puede adoptar ni revivir entradas. */
final class InformeExpedicion {
    private final VistaEntrada entrada;

    InformeExpedicion(VistaEntrada entrada) { this.entrada = Objects.requireNonNull(entrada); }
    VistaEntrada entrada() { return entrada; }
    String consultar() { return entrada.activa() ? entrada.texto() : "entrada no disponible"; }
}

/** Dependencia: recibe un explorador como parámetro, sin retener su referencia. */
final class ServicioConsulta {
    private ServicioConsulta() {}
    static String describirUbicacion(Explorador explorador) {
        Objects.requireNonNull(explorador);
        return explorador.id() + " está en " + explorador.ubicacion().id();
    }
}

final class Reglas {
    private Reglas() {}
    static String exigirTexto(String texto) {
        Objects.requireNonNull(texto, "Texto obligatorio");
        if (texto.isBlank()) throw new IllegalArgumentException("Texto vacío");
        return texto;
    }
}
