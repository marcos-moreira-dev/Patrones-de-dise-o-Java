package liminal.creacionales.builder;

import java.io.PrintStream;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/** Builder: construir una habitación legible por etapas. */
public final class ClienteBuilder {
    private ClienteBuilder() {}

    public static void ejecutar(PrintStream out) {
        Habitacion habitacion = new ConstructorHabitacion()
                .id("H-01")
                .ambiente("Pasillo amarillo")
                .luces(4)
                .conPuerta(true)
                .construir();
        RegistroHabitaciones mapa = new RegistroHabitaciones();
        mapa.agregar(habitacion);
        out.println("Habitación: " + habitacion.id());
        out.println("Ambiente: " + habitacion.ambiente());
        out.println("Luces: " + habitacion.luces());
        out.println("Puerta: " + (habitacion.conPuerta() ? "sí" : "no"));
    }

    public static void probar() {
        ConstructorHabitacion constructor = new ConstructorHabitacion();
        esperarFallo(constructor::construir);
        Habitacion h = constructor.id("H-01").ambiente("Piscina vacía")
                .luces(0).conPuerta(false).construir();
        if (!h.ambiente().equals("Piscina vacía") || h.luces() != 0) {
            throw new AssertionError("Builder no respetó la configuración");
        }
        RegistroHabitaciones mapa = new RegistroHabitaciones();
        mapa.agregar(h);
        esperarFallo(() -> mapa.agregar(new ConstructorHabitacion()
                .id("H-01").ambiente("Oficina").luces(1).construir()));
        if (mapa.cantidad() != 1) throw new AssertionError("No debe registrar IDs repetidos");
        esperarFallo(() -> new ConstructorHabitacion().id("H-02")
                .ambiente("Oficina").luces(-1));
    }

    private static void esperarFallo(Runnable accion) {
        try { accion.run(); }
        catch (IllegalArgumentException | IllegalStateException esperado) { return; }
        throw new AssertionError("Falta rechazo de estado inválido");
    }

    public static void main(String[] args) { ejecutar(System.out); }
}

record Habitacion(String id, String ambiente, int luces, boolean conPuerta) {
    Habitacion {
        Objects.requireNonNull(id);
        Objects.requireNonNull(ambiente);
        if (id.isBlank() || ambiente.isBlank() || luces < 0) {
            throw new IllegalArgumentException("Configuración de habitación inválida");
        }
    }
}

final class ConstructorHabitacion {
    private String id;
    private String ambiente;
    private Integer luces;
    private boolean conPuerta;

    ConstructorHabitacion id(String valor) { id = valor; return this; }
    ConstructorHabitacion ambiente(String valor) { ambiente = valor; return this; }
    ConstructorHabitacion luces(int cantidad) {
        if (cantidad < 0) throw new IllegalArgumentException("Luces negativas");
        luces = cantidad;
        return this;
    }
    ConstructorHabitacion conPuerta(boolean valor) { conPuerta = valor; return this; }

    Habitacion construir() {
        if (luces == null) throw new IllegalStateException("Falta indicar las luces");
        return new Habitacion(id, ambiente, luces, conPuerta);
    }
}

final class RegistroHabitaciones {
    private final Set<String> ids = new HashSet<>();
    void agregar(Habitacion habitacion) {
        Objects.requireNonNull(habitacion);
        if (!ids.add(habitacion.id())) throw new IllegalArgumentException("Habitación duplicada");
    }
    int cantidad() { return ids.size(); }
}
