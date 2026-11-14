package liminal.creacionales.prototype.variantes.copiaprofunda;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/** Prototipo de configuración, sin ID de habitación activa. */
final class PlantillaHabitacion {
    private final String ambiente;
    private final List<ConfiguracionLuminaria> luminarias;

    PlantillaHabitacion(String ambiente, List<ConfiguracionLuminaria> luminarias) {
        this.ambiente = Objects.requireNonNull(ambiente);
        if (ambiente.isBlank()) throw new IllegalArgumentException("Ambiente vacío");
        this.luminarias = List.copyOf(Objects.requireNonNull(luminarias));
    }

    String ambiente() { return ambiente; }
    int cantidadLuminarias() { return luminarias.size(); }
    ConfiguracionLuminaria luminaria(int indice) { return luminarias.get(indice); }

    PlantillaHabitacion copiarSuperficial() {
        return new PlantillaHabitacion(ambiente, luminarias);
    }

    PlantillaHabitacion copiarProfunda() {
        List<ConfiguracionLuminaria> nuevas = new ArrayList<>();
        for (ConfiguracionLuminaria luminaria : luminarias) nuevas.add(luminaria.copiar());
        return new PlantillaHabitacion(ambiente, nuevas);
    }
}
