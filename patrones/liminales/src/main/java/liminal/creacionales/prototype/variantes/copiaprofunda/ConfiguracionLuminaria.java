package liminal.creacionales.prototype.variantes.copiaprofunda;

import java.util.Objects;

/** Configuración editable; NO es una luminaria física de una expedición. */
final class ConfiguracionLuminaria {
    private final String modelo;
    private int intensidad;

    ConfiguracionLuminaria(String modelo, int intensidad) {
        this.modelo = Objects.requireNonNull(modelo, "Modelo requerido");
        if (modelo.isBlank()) throw new IllegalArgumentException("Modelo vacío");
        cambiarIntensidad(intensidad);
    }

    String modelo() { return modelo; }
    int intensidad() { return intensidad; }

    void cambiarIntensidad(int valor) {
        if (valor < 0 || valor > 100) {
            throw new IllegalArgumentException("Intensidad fuera de 0..100");
        }
        intensidad = valor;
    }

    ConfiguracionLuminaria copiar() {
        return new ConfiguracionLuminaria(modelo, intensidad);
    }
}
