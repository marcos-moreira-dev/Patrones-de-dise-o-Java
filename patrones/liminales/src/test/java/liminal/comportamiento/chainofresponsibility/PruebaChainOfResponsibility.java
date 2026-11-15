package liminal.comportamiento.chainofresponsibility;

import java.util.Set;

/** Prueba independiente del recorrido pedagógico. */
public final class PruebaChainOfResponsibility {
    private PruebaChainOfResponsibility() {}

    public static void ejecutar() {
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

    private static SolicitudCruce escena(boolean abierta) {
        Ruta ruta = new Ruta("H-01", "H-02");
        return new SolicitudCruce(
                Set.of("H-01", "H-02"), Set.of(ruta), Set.of(ruta),
                abierta ? Set.of(ruta) : Set.of(), "H-01", "H-02");
    }
}
