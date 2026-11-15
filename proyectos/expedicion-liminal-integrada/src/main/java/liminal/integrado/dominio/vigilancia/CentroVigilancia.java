package liminal.integrado.dominio.vigilancia;

import java.util.Objects;

/**
 * Mediator: decide qué receptores activar. El sensor no conoce ni a la luz
 * ni a la alarma; solo su contrato de observadores.
 */
public final class CentroVigilancia implements ObservadorRuido {
    private final String id;
    private final String sensorAutorizado;
    private final LuzAlerta luz;
    private final Alarma alarma;

    public CentroVigilancia(String id, String sensorAutorizado,
                            LuzAlerta luz, Alarma alarma) {
        this.id = validar(id);
        this.sensorAutorizado = validar(sensorAutorizado);
        this.luz = Objects.requireNonNull(luz);
        this.alarma = Objects.requireNonNull(alarma);
    }

    @Override
    public String id() { return id; }

    @Override
    public void alDetectar(EventoRuido evento) {
        Objects.requireNonNull(evento);
        if (!sensorAutorizado.equals(evento.sensorId())) {
            throw new IllegalArgumentException("Sensor no autorizado");
        }
        luz.encender();
        alarma.avisar(evento);
    }

    private static String validar(String texto) {
        Objects.requireNonNull(texto);
        if (texto.isBlank()) throw new IllegalArgumentException("ID vacío");
        return texto;
    }
}
