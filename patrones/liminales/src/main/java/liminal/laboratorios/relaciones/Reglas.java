package liminal.laboratorios.relaciones;

import java.util.Objects;

final class Reglas {
    private Reglas() {}
    static String exigirTexto(String texto) {
        Objects.requireNonNull(texto, "Texto obligatorio");
        if (texto.isBlank()) throw new IllegalArgumentException("Texto vacío");
        return texto;
    }
}
