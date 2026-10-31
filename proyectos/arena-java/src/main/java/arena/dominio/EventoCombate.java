package arena.dominio;

import java.util.Objects;

/**
 * Evento inmutable y tipado: los observadores pueden reaccionar por tipo sin
 * examinar cadenas de texto. El mensaje es solo una explicación legible.
 */
public record EventoCombate(Tipo tipo, String mensaje) {
    public enum Tipo {
        ATAQUE, DEFENSA, SALTO, HUIDA, TERRENO, RESPUESTA, VICTORIA, DERROTA
    }

    public EventoCombate {
        Objects.requireNonNull(tipo);
        if (mensaje == null || mensaje.isBlank()) {
            throw new IllegalArgumentException("Un evento necesita una descripcion");
        }
    }
}
