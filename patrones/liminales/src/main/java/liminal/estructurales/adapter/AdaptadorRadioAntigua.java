package liminal.estructurales.adapter;

import java.util.Objects;

final class AdaptadorRadioAntigua implements Comunicador {
    private final RadioAntigua radio;

    AdaptadorRadioAntigua(RadioAntigua radio) { this.radio = Objects.requireNonNull(radio); }
    public void enviar(String aviso) {
        Objects.requireNonNull(aviso);
        if (aviso.isBlank()) throw new IllegalArgumentException("Aviso vacío");
        radio.emitirPorFrecuencia(aviso);
    }
}
