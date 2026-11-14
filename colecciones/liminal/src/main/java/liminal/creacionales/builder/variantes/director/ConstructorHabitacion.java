package liminal.creacionales.builder.variantes.director;

import java.util.Objects;

/** Builder concreto: recopila y valida antes de entregar la habitación. */
final class ConstructorHabitacion implements ConstructorEscenario {
    private String id;
    private String ambiente;
    private Integer luces;
    private Boolean puerta;

    @Override
    public void comenzar(String nuevoId) {
        Objects.requireNonNull(nuevoId);
        if (nuevoId.isBlank()) throw new IllegalArgumentException("ID vacío");
        id = nuevoId;
        ambiente = null;
        luces = null;
        puerta = null;
    }

    @Override
    public void elegirAmbiente(String nombre) {
        Objects.requireNonNull(nombre);
        if (nombre.isBlank()) throw new IllegalArgumentException("Ambiente vacío");
        ambiente = nombre;
    }

    @Override
    public void instalarLuces(int cantidad) {
        if (cantidad < 0) throw new IllegalArgumentException("Luces negativas");
        luces = cantidad;
    }

    @Override
    public void colocarPuerta(boolean conPuerta) { puerta = conPuerta; }

    @Override
    public Habitacion construir() {
        if (id == null || ambiente == null || luces == null || puerta == null) {
            throw new IllegalStateException("Construcción incompleta");
        }
        return new Habitacion(id, ambiente, luces, puerta);
    }
}
