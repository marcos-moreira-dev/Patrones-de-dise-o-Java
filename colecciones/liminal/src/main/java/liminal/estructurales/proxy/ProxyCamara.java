package liminal.estructurales.proxy;

import java.io.PrintStream;
import java.util.Objects;


final class ProxyCamara implements Camara {
    private final String habitacion;
    private final String paseCorrecto;
    private CamaraReal real;
    private int creaciones;

    ProxyCamara(String habitacion, String paseCorrecto) {
        this.habitacion = exigirTexto(habitacion);
        this.paseCorrecto = exigirTexto(paseCorrecto);
    }
    public String observar(String pase) {
        if (!paseCorrecto.equals(pase)) throw new IllegalArgumentException("Pase incorrecto");
        if (real == null) {
            real = new CamaraReal(habitacion);
            creaciones++;
        }
        return real.observar(pase);
    }
    int creaciones() { return creaciones; }

    private static String exigirTexto(String texto) {
        Objects.requireNonNull(texto);
        if (texto.isBlank()) throw new IllegalArgumentException("Texto vacío");
        return texto;
    }
}
