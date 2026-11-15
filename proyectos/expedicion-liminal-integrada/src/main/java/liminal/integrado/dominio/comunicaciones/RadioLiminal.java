package liminal.integrado.dominio.comunicaciones;

import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import liminal.integrado.dominio.mundo.Habitacion;
import liminal.integrado.dominio.mundo.MapaLiminal;

/**
 * Receptor de ficción: mensajes predefinidos por habitación y frecuencia.
 * No simula ondas, conexión de red ni una radio bidireccional real.
 */
public final class RadioLiminal {
    private final MapaLiminal mapa;
    private final Map<Habitacion, Map<Integer, String>> mensajes = new IdentityHashMap<>();
    private int frecuenciaKhz;

    public RadioLiminal(MapaLiminal mapa, int frecuenciaInicialKhz) {
        this.mapa = Objects.requireNonNull(mapa, "Mapa obligatorio");
        sintonizar(frecuenciaInicialKhz);
    }

    public int frecuenciaKhz() { return frecuenciaKhz; }

    public void sintonizar(int nuevaFrecuenciaKhz) {
        validarFrecuencia(nuevaFrecuenciaKhz);
        frecuenciaKhz = nuevaFrecuenciaKhz;
    }

    public void registrarMensaje(Habitacion lugar, int frecuencia, String texto) {
        validarLugar(lugar);
        validarFrecuencia(frecuencia);
        Objects.requireNonNull(texto, "Mensaje obligatorio");
        if (texto.isBlank()) throw new IllegalArgumentException("Mensaje vacío");
        Map<Integer, String> previos = mensajes.get(lugar);
        if (previos != null && previos.containsKey(frecuencia)) {
            throw new IllegalArgumentException("Señal duplicada en habitación y frecuencia");
        }
        // Solo se modifica la tabla después de validar lugar, frecuencia y texto.
        mensajes.computeIfAbsent(lugar, clave -> new HashMap<>()).put(frecuencia, texto);
    }

    public Optional<String> escucharEn(Habitacion lugar) {
        validarLugar(lugar);
        Map<Integer, String> disponibles = mensajes.get(lugar);
        return Optional.ofNullable(
                disponibles == null ? null : disponibles.get(frecuenciaKhz));
    }

    private void validarLugar(Habitacion lugar) {
        Objects.requireNonNull(lugar, "Habitación obligatoria");
        if (mapa.habitacion(lugar.id()) != lugar) {
            throw new IllegalArgumentException("Habitación de otro mapa");
        }
    }

    private static void validarFrecuencia(int frecuencia) {
        if (frecuencia <= 0) throw new IllegalArgumentException("Frecuencia inválida");
    }
}
