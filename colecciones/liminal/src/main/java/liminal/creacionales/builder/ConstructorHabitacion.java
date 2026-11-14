package liminal.creacionales.builder;

/** Builder: conserva temporalmente los parámetros antes de construir el producto. */
public final class ConstructorHabitacion {
    private String id;
    private String ambiente;
    private Integer luces;
    private boolean conPuerta;

    public ConstructorHabitacion id(String valor) { id = valor; return this; }
    public ConstructorHabitacion ambiente(String valor) { ambiente = valor; return this; }
    public ConstructorHabitacion luces(int cantidad) {
        if (cantidad < 0) throw new IllegalArgumentException("Luces negativas");
        luces = cantidad;
        return this;
    }
    public ConstructorHabitacion conPuerta(boolean valor) { conPuerta = valor; return this; }

    public Habitacion construir() {
        if (luces == null) throw new IllegalStateException("Falta indicar las luces");
        return new Habitacion(id, ambiente, luces, conPuerta);
    }
}
