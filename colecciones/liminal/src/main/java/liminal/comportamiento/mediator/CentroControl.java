package liminal.comportamiento.mediator;

import java.io.PrintStream;
import java.util.Objects;


final class CentroControl implements Mediador {
    private final String sensorPermitido;
    private final Luz luz;
    private final Alarma alarma;

    CentroControl(String sensorPermitido, Luz luz, Alarma alarma) {
        this.sensorPermitido = Objects.requireNonNull(sensorPermitido);
        if (sensorPermitido.isBlank()) throw new IllegalArgumentException("Sensor sin ID");
        this.luz = Objects.requireNonNull(luz);
        this.alarma = Objects.requireNonNull(alarma);
    }

    public void notificarRuido(String idSensor) {
        if (!sensorPermitido.equals(idSensor)) throw new IllegalArgumentException("Sensor no registrado");
        luz.encender();
        alarma.avisar();
    }
}
