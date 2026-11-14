package liminal.comportamiento.chainofresponsibility.variantes.pipeline;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Set;

public final class PruebaPipeline {
    private PruebaPipeline() {}

    public static void ejecutar() {
        ValidadorCruce ubicacion = new ValidarHabitaciones();
        ValidadorCruce salida = new ValidarSalida();
        ValidadorCruce puerta = new ValidarPuerta();
        ubicacion.enlazar(salida);
        salida.enlazar(puerta);

        Set<String> habitaciones = Set.of("H-01", "H-02");
        SolicitudCruce mal = new SolicitudCruce(habitaciones, "H-99", "H-02",
                false, false, false);
        asegurar(ubicacion.verificar(mal).equals(List.of("ORIGEN_DESCONOCIDO", "SIN_SALIDA")),
                "Se acumulan fallos sin detenerse en el primero");

        SolicitudCruce abierto = new SolicitudCruce(habitaciones, "H-01", "H-02",
                true, false, false);
        asegurar(ubicacion.verificar(abierto).isEmpty(), "Sin puerta es permitido");
        SolicitudCruce cerrado = new SolicitudCruce(habitaciones, "H-01", "H-02",
                true, true, false);
        asegurar(ubicacion.verificar(cerrado).equals(List.of("PUERTA_CERRADA")),
                "La puerta cerrada impide avanzar");

        rechazar(() -> new SolicitudCruce(habitaciones, "H-01", "H-02",
                false, true, false));
        rechazar(() -> new SolicitudCruce(habitaciones, "", "H-02",
                false, false, false));
        rechazar(() -> ubicacion.enlazar(salida));

        ByteArrayOutputStream buf = new ByteArrayOutputStream();
        ClientePipeline.ejecutar(new PrintStream(buf, true, StandardCharsets.UTF_8));
        String texto = buf.toString(StandardCharsets.UTF_8);
        asegurar(texto.contains("INVALIDA: ORIGEN_DESCONOCIDO, SIN_SALIDA"),
                "Consola con todos los errores");
        asegurar(texto.contains("SIN PUERTA: PERMITIDO"), "Consola permite ruta sin puerta");
    }

    private static void asegurar(boolean bien, String motivo) {
        if (!bien) throw new AssertionError(motivo);
    }

    private static void rechazar(Runnable accion) {
        try { accion.run(); }
        catch (IllegalArgumentException esperado) { return; }
        throw new AssertionError("Debió rechazar");
    }
}
