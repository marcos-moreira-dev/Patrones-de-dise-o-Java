package liminal.laboratorios.relaciones;

import java.io.PrintStream;

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
