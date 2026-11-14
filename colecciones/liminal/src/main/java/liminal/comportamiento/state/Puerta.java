package liminal.comportamiento.state;

import java.io.PrintStream;
import java.util.Objects;



final class Puerta {
    private final String id;
    private final String codigoLlave;
    private EstadoPuerta estado = new EstadoCerrada();

    Puerta(String id, String codigoLlave) {
        this.id = validar(id);
        this.codigoLlave = validar(codigoLlave);
    }
    String id() { return id; }
    String estado() { return estado.nombre(); }
    boolean transitable() { return estado.transitable(); }
    void abrir() { estado = estado.abrir(); }
    void cerrar() { estado = estado.cerrar(); }
    void bloquear() { estado = estado.bloquear(); }
    void desbloquear(String llave) { estado = estado.desbloquear(validar(llave), codigoLlave); }

    private static String validar(String valor) {
        Objects.requireNonNull(valor);
        if (valor.isBlank()) throw new IllegalArgumentException("Texto vacío");
        return valor;
    }
}
