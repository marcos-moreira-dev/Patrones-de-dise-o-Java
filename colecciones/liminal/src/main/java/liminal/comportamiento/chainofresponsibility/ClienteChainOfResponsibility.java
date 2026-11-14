package liminal.comportamiento.chainofresponsibility;

import java.io.PrintStream;
import java.util.Objects;
import java.util.Set;

/** Chain of Responsibility: las comprobaciones de un cruce forman una cadena. */
public final class ClienteChainOfResponsibility {
    private ClienteChainOfResponsibility() {}

    public static void ejecutar(PrintStream out) {
        ValidadorCruce cadena = new VerificarDestino(
                new VerificarSalida(new VerificarPuerta(null)));
        SolicitudCruce cerrado = escena(false);
        SolicitudCruce abierto = escena(true);
        out.println("Puerta cerrada -> " + cadena.verificar(cerrado));
        out.println("Puerta abierta -> " + cadena.verificar(abierto));
    }

    public static void probar() {
        ValidadorCruce cadena = new VerificarDestino(
                new VerificarSalida(new VerificarPuerta(null)));
        if (!cadena.verificar(escena(true)).equals("PERMITIDO")) {
            throw new AssertionError("Cruce autorizado");
        }
        if (!cadena.verificar(escena(false)).equals("PUERTA_CERRADA")) {
            throw new AssertionError("Falta comprobar puerta");
        }
        SolicitudCruce sinDestino = new SolicitudCruce(
                Set.of("H-01"), Set.of(), Set.of(), Set.of(), "H-01", "H-02");
        if (!cadena.verificar(sinDestino).equals("DESTINO_INEXISTENTE")) {
            throw new AssertionError("Debe detenerse en el primer validador");
        }
        SolicitudCruce sinSalida = new SolicitudCruce(
                Set.of("H-01", "H-02"), Set.of(), Set.of(), Set.of(), "H-01", "H-02");
        if (!cadena.verificar(sinSalida).equals("SALIDA_INEXISTENTE")) {
            throw new AssertionError("Debe verificar la conexión dirigida");
        }

        Ruta ruta = new Ruta("H-01", "H-02");
        SolicitudCruce sinPuerta = new SolicitudCruce(
                Set.of("H-01", "H-02"), Set.of(ruta), Set.of(), Set.of(), "H-01", "H-02");
        if (!cadena.verificar(sinPuerta).equals("PERMITIDO")) {
            throw new AssertionError("R-04: una salida sin puerta es transitable");
        }

        SolicitudCruce origenInexistente = new SolicitudCruce(
                Set.of("H-02"), Set.of(), Set.of(), Set.of(), "H-99", "H-02");
        if (!cadena.verificar(origenInexistente).equals("ORIGEN_INEXISTENTE")) {
            throw new AssertionError("Debe validar también el origen");
        }

        try {
            new SolicitudCruce(Set.of("H-01", "H-02"),
                    Set.of(ruta), Set.of(), Set.of(ruta), "H-01", "H-02");
            throw new AssertionError("Una puerta inexistente no puede figurar abierta");
        } catch (IllegalArgumentException esperado) {
            // Se conserva la integridad del modelo de puertas.
        }
    }
    public static void main(String[] args) { ejecutar(System.out); }

    private static SolicitudCruce escena(boolean abierta) {
        Ruta ruta = new Ruta("H-01", "H-02");
        return new SolicitudCruce(
                Set.of("H-01", "H-02"), Set.of(ruta), Set.of(ruta),
                abierta ? Set.of(ruta) : Set.of(), "H-01", "H-02");
    }
}

record Ruta(String origen, String destino) {}
record SolicitudCruce(Set<String> habitaciones, Set<Ruta> salidas,
                       Set<Ruta> puertas, Set<Ruta> abiertas,
                       String origen, String destino) {
    SolicitudCruce {
        habitaciones = Set.copyOf(habitaciones);
        salidas = Set.copyOf(salidas);
        puertas = Set.copyOf(puertas);
        abiertas = Set.copyOf(abiertas);
        Objects.requireNonNull(origen);
        Objects.requireNonNull(destino);
        if (!salidas.containsAll(puertas) || !puertas.containsAll(abiertas)) {
            throw new IllegalArgumentException("Puerta o estado ajeno a las salidas");
        }
        for (Ruta ruta : salidas) {
            if (!habitaciones.contains(ruta.origen()) || !habitaciones.contains(ruta.destino())) {
                throw new IllegalArgumentException("La salida está fuera del mapa");
            }
        }
    }
}

abstract class ValidadorCruce {
    private final ValidadorCruce siguiente;

    ValidadorCruce(ValidadorCruce siguiente) { this.siguiente = siguiente; }
    final String verificar(SolicitudCruce solicitud) {
        String rechazo = detectarRechazo(solicitud);
        if (rechazo != null) return rechazo;
        return siguiente == null ? "PERMITIDO" : siguiente.verificar(solicitud);
    }
    protected abstract String detectarRechazo(SolicitudCruce solicitud);
}
final class VerificarDestino extends ValidadorCruce {
    VerificarDestino(ValidadorCruce siguiente) { super(siguiente); }
    protected String detectarRechazo(SolicitudCruce s) {
        if (!s.habitaciones().contains(s.origen())) return "ORIGEN_INEXISTENTE";
        return s.habitaciones().contains(s.destino()) ? null : "DESTINO_INEXISTENTE";
    }
}
final class VerificarSalida extends ValidadorCruce {
    VerificarSalida(ValidadorCruce siguiente) { super(siguiente); }
    protected String detectarRechazo(SolicitudCruce s) {
        return s.salidas().contains(new Ruta(s.origen(), s.destino())) ? null : "SALIDA_INEXISTENTE";
    }
}
final class VerificarPuerta extends ValidadorCruce {
    VerificarPuerta(ValidadorCruce siguiente) { super(siguiente); }
    protected String detectarRechazo(SolicitudCruce s) {
        Ruta ruta = new Ruta(s.origen(), s.destino());
        if (!s.puertas().contains(ruta)) return null;
        return s.abiertas().contains(ruta) ? null : "PUERTA_CERRADA";
    }
}
