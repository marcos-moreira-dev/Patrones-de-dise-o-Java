package liminal.comportamiento.command.variantes.deshacer;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

public final class PruebaDeshacer {
    private PruebaDeshacer() {}

    public static void ejecutar() {
        Puerta puerta = new Puerta("P-01");
        HistorialComandos historia = new HistorialComandos();
        rechazar(historia::deshacer);
        rechazar(historia::rehacer);

        historia.ejecutar(new AbrirPuerta(puerta));
        asegurar(puerta.estado() == EstadoPuerta.ABIERTA && historia.realizados() == 1,
                "Comando ejecutado");
        historia.deshacer();
        asegurar(puerta.estado() == EstadoPuerta.CERRADA && historia.deshechos() == 1,
                "Deshacer R-05");
        historia.rehacer();
        asegurar(puerta.estado() == EstadoPuerta.ABIERTA && historia.deshechos() == 0,
                "Rehacer R-05");

        Puerta otra = new Puerta("P-02");
        HistorialComandos segundaHistoria = new HistorialComandos();
        segundaHistoria.ejecutar(new AbrirPuerta(otra));
        otra.cerrar(); // Cambio externo: incluso si vuelve a ABIERTA, la revisión será distinta.
        otra.abrir();
        rechazar(segundaHistoria::deshacer);
        asegurar(otra.estado() == EstadoPuerta.ABIERTA
                && segundaHistoria.realizados() == 1 && segundaHistoria.deshechos() == 0,
                "Historial y puerta inmutados tras fallo");

        Puerta bloqueable = new Puerta("P-03");
        bloqueable.bloquear();
        HistorialComandos historialBloqueo = new HistorialComandos();
        rechazar(() -> historialBloqueo.ejecutar(new AbrirPuerta(bloqueable)));
        asegurar(bloqueable.estado() == EstadoPuerta.BLOQUEADA
                && historialBloqueo.realizados() == 0, "No fuerza estados bloqueados");

        ByteArrayOutputStream buf = new ByteArrayOutputStream();
        ClienteDeshacer.ejecutar(new PrintStream(buf, true, StandardCharsets.UTF_8));
        String salida = buf.toString(StandardCharsets.UTF_8);
        asegurar(salida.contains("DESHACER: CERRADA") && salida.contains("REHACER: ABIERTA"),
                "Salida reproducible");
    }

    private static void asegurar(boolean bien, String error) {
        if (!bien) throw new AssertionError(error);
    }

    private static void rechazar(Runnable accion) {
        try { accion.run(); }
        catch (IllegalStateException esperado) { return; }
        throw new AssertionError("Acción inválida permitida");
    }
}
