package liminal.creacionales.builder;

import java.io.PrintStream;

/**
 * Cliente de Builder.
 * Recorrido: configurar el producto, registrarlo y mostrar el resultado.
 */
public final class ClienteBuilder {
    private ClienteBuilder() {}

    public static void main(String[] args) {
        ejecutar(System.out);
    }

    public static void ejecutar(PrintStream out) {
        Habitacion habitacion = construirHabitacion();
        registrarHabitacion(habitacion);
        mostrarHabitacion(habitacion, out);
    }

    /** 1. Builder: configurar el objeto antes de crearlo. */
    private static Habitacion construirHabitacion() {
        return new ConstructorHabitacion()
                .id("H-01")
                .ambiente("Pasillo amarillo")
                .luces(4)
                .conPuerta(true)
                .construir();
    }

    /** 2. Dominio: comprobar el ID al insertar en el mapa (no es Builder). */
    private static void registrarHabitacion(Habitacion habitacion) {
        RegistroHabitaciones mapa = new RegistroHabitaciones();
        mapa.agregar(habitacion);
    }

    /** 3. Presentación: mostrar el producto ya construido. */
    private static void mostrarHabitacion(Habitacion habitacion, PrintStream out) {
        out.println("Habitación: " + habitacion.id());
        out.println("Ambiente: " + habitacion.ambiente());
        out.println("Luces: " + habitacion.luces());
        out.println("Puerta: " + (habitacion.conPuerta() ? "sí" : "no"));
    }
}
