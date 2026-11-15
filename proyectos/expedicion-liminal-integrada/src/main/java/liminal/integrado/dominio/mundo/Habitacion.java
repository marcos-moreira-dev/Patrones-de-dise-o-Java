package liminal.integrado.dominio.mundo;

import java.util.Objects;

/** Entidad del mapa: dos habitaciones visualmente iguales pueden tener IDs distintos. */
public final class Habitacion {
    private final String id;
    private final String nombre;
    private final Posicion posicion;

    Habitacion(String id, String nombre, Posicion posicion) {
        this.id = validarTexto(id);
        this.nombre = validarTexto(nombre);
        this.posicion = Objects.requireNonNull(posicion);
    }

    public String id() { return id; }
    public String nombre() { return nombre; }
    public Posicion posicion() { return posicion; }

    private static String validarTexto(String texto) {
        Objects.requireNonNull(texto, "Texto obligatorio");
        if (texto.isBlank()) throw new IllegalArgumentException("Texto vacío");
        return texto;
    }
}

