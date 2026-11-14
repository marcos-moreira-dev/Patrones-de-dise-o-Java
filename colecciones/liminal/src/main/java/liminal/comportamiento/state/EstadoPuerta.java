package liminal.comportamiento.state;

import java.io.PrintStream;
import java.util.Objects;



interface EstadoPuerta {
    String nombre();
    default boolean transitable() { return false; }
    default EstadoPuerta abrir() { throw invalido("abrir"); }
    default EstadoPuerta cerrar() { throw invalido("cerrar"); }
    default EstadoPuerta bloquear() { throw invalido("bloquear"); }
    default EstadoPuerta desbloquear(String llave, String esperada) {
        throw invalido("desbloquear");
    }
    private IllegalStateException invalido(String accion) {
        return new IllegalStateException("No se puede " + accion + " desde " + nombre());
    }
}
