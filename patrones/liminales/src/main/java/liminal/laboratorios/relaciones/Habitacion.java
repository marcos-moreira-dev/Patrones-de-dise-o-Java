package liminal.laboratorios.relaciones;


/** La identidad de una habitación no depende de su apariencia. */
final class Habitacion {
    private final String id;
    private final String nombre;

    Habitacion(String id, String nombre) {
        this.id = Reglas.exigirTexto(id);
        this.nombre = Reglas.exigirTexto(nombre);
    }
    String id() { return id; }
    String nombre() { return nombre; }
}
