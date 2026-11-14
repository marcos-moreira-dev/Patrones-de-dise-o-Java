package liminal.creacionales.factorymethod;

import java.io.PrintStream;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/** Factory Method: la clase creadora delega el tipo de criatura. */
public final class ClienteFactoryMethod {
    private ClienteFactoryMethod() {}

    public static void ejecutar(PrintStream out) {
        RegistroApariciones registro = new RegistroApariciones(Set.of("H-01", "H-02"));
        GeneradorCriatura oscuro = new GeneradorOscuro();
        GeneradorCriatura silencioso = new GeneradorSilencioso();
        Criatura a = oscuro.aparecer("M-01", "H-01", registro);
        Criatura b = silencioso.aparecer("M-02", "H-02", registro);
        out.println(a.nombre() + " en " + a.habitacion());
        out.println(b.nombre() + " en " + b.habitacion());
    }

    public static void probar() {
        RegistroApariciones registro = new RegistroApariciones(Set.of("H-01"));
        Criatura a = new GeneradorOscuro().aparecer("M-01", "H-01", registro);
        if (!(a instanceof MonstruoOscuro)) throw new AssertionError("Producto equivocado");
        if (!a.habitacion().equals("H-01")) throw new AssertionError("Ubicación inválida");
        esperarFallo(() -> new GeneradorSilencioso().aparecer("M-01", "H-01", registro));
        esperarFallo(() -> new GeneradorSilencioso().aparecer("M-02", "H-99", registro));
        if (registro.cantidad() != 1) throw new AssertionError("Fallos no deben registrar entidades");
        esperarFallo(() -> new MonstruoOscuro(" ", "H-01"));
        esperarFallo(() -> new MonstruoSilencioso("M-03", ""));
        if (registro.cantidad() != 1) throw new AssertionError("Creación inválida no se registra");
    }

    private static void esperarFallo(Runnable accion) {
        try { accion.run(); }
        catch (IllegalArgumentException esperado) { return; }
        throw new AssertionError("El registro debe rechazar el intento");
    }

    public static void main(String[] args) { ejecutar(System.out); }
}

interface Criatura {
    String id();
    String nombre();
    String habitacion();
}

record MonstruoOscuro(String id, String habitacion) implements Criatura {
    MonstruoOscuro { validarDatos(id, habitacion); }
    public String nombre() { return "Monstruo oscuro"; }
    private static void validarDatos(String id, String habitacion) {
        Objects.requireNonNull(id);
        Objects.requireNonNull(habitacion);
        if (id.isBlank() || habitacion.isBlank()) throw new IllegalArgumentException("ID o ubicación vacíos");
    }
}
record MonstruoSilencioso(String id, String habitacion) implements Criatura {
    MonstruoSilencioso {
        Objects.requireNonNull(id);
        Objects.requireNonNull(habitacion);
        if (id.isBlank() || habitacion.isBlank()) throw new IllegalArgumentException("ID o ubicación vacíos");
    }
    public String nombre() { return "Monstruo silencioso"; }
}

abstract class GeneradorCriatura {
    public final Criatura aparecer(String id, String habitacion, RegistroApariciones registro) {
        Objects.requireNonNull(registro);
        registro.verificar(id, habitacion);
        Criatura resultado = crearCriatura(id, habitacion);
        registro.registrar(resultado);
        return resultado;
    }
    protected abstract Criatura crearCriatura(String id, String habitacion);
}
final class GeneradorOscuro extends GeneradorCriatura {
    protected Criatura crearCriatura(String id, String habitacion) {
        return new MonstruoOscuro(id, habitacion);
    }
}
final class GeneradorSilencioso extends GeneradorCriatura {
    protected Criatura crearCriatura(String id, String habitacion) {
        return new MonstruoSilencioso(id, habitacion);
    }
}

final class RegistroApariciones {
    private final Set<String> habitaciones;
    private final Set<String> ids = new HashSet<>();

    RegistroApariciones(Set<String> habitaciones) {
        this.habitaciones = Set.copyOf(habitaciones);
    }
    void verificar(String id, String habitacion) {
        Objects.requireNonNull(id);
        if (id.isBlank() || ids.contains(id)) throw new IllegalArgumentException("ID inválido o repetido");
        if (!habitaciones.contains(habitacion)) throw new IllegalArgumentException("Habitación inexistente");
    }
    void registrar(Criatura criatura) {
        verificar(criatura.id(), criatura.habitacion());
        ids.add(criatura.id());
    }
    int cantidad() { return ids.size(); }
}
