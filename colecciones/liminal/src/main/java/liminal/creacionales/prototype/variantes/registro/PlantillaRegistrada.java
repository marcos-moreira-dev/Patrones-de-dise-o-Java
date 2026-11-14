package liminal.creacionales.prototype.variantes.registro;

import java.util.Objects;

/** Plantilla sin ID: ambiente y configuración, no habitación activa. */
final class PlantillaRegistrada implements PrototipoRegistrable {
    private final String ambiente;
    private final int luces;
    private final boolean conPuerta;

    PlantillaRegistrada(String ambiente, int luces, boolean conPuerta) {
        this.ambiente = Objects.requireNonNull(ambiente, "Ambiente requerido");
        if (ambiente.isBlank() || luces < 0) {
            throw new IllegalArgumentException("Plantilla inválida");
        }
        this.luces = luces;
        this.conPuerta = conPuerta;
    }

    @Override
    public HabitacionGenerada crearConId(String nuevoId) {
        return new HabitacionGenerada(nuevoId, ambiente, luces, conPuerta);
    }
}
