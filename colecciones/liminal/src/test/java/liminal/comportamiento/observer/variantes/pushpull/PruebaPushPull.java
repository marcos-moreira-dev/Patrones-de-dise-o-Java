package liminal.comportamiento.observer.variantes.pushpull;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

public final class PruebaPushPull {
    private PruebaPushPull() {}

    public static void ejecutar() {
        Interruptor i = new Interruptor("I-01");
        LuzPush push = new LuzPush("L-01");
        LuzPull pull = new LuzPull("L-02");
        i.suscribir(push);
        i.suscribir(pull);
        rechazar(() -> i.suscribir(new LuzPush("L-01")));
        asegurar(!i.activado() && push.avisos() == 0 && pull.avisos() == 0,
                "Rechazo sin notificación ni cambio");
        i.pulsar();
        asegurar(i.activado() && push.encendida() && pull.encendida(), "Ambas modalidades");
        asegurar(push.avisos() == 1 && pull.avisos() == 1, "Un aviso por cambio");
        i.retirar("L-02");
        i.pulsar();
        asegurar(!i.activado() && !push.encendida(), "Push recibe segundo cambio");
        asegurar(pull.encendida() && pull.avisos() == 1, "Pull retirado no recibe otro");
        asegurar(push.avisos() == 2, "Conteo push");
        rechazar(() -> new Interruptor(" "));

        ByteArrayOutputStream buf = new ByteArrayOutputStream();
        ClientePushPull.ejecutar(new PrintStream(buf, true, StandardCharsets.UTF_8));
        String salida = buf.toString(StandardCharsets.UTF_8);
        asegurar(salida.contains("PUSH tras pulsar: ENCENDIDA"), "Consola push");
        asegurar(salida.contains("PULL tras pulsar: ENCENDIDA"), "Consola pull");
        asegurar(salida.contains("PULL sin suscripción: ENCENDIDA"), "Consola retirado");
    }

    private static void asegurar(boolean bien, String motivo) {
        if (!bien) throw new AssertionError(motivo);
    }

    private static void rechazar(Runnable accion) {
        try { accion.run(); }
        catch (IllegalArgumentException esperado) { return; }
        throw new AssertionError("Alta inválida aceptada");
    }
}
