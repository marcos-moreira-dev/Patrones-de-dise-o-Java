package liminal.integrado.aplicacion;

import java.util.Objects;
import liminal.integrado.dominio.exploracion.AvanceExpedicion;
import liminal.integrado.dominio.exploracion.Expedicion;
import liminal.integrado.dominio.mundo.Direccion;
import liminal.integrado.dominio.mundo.Habitacion;
import liminal.integrado.dominio.mundo.MovimientoRechazadoException;
import liminal.integrado.dominio.vigilancia.SensorRuido;

/** Caso de uso que adapta el resultado de dominio a un relato breve. */
public final class ProcesarTurno {
    private final Expedicion expedicion;
    private final SensorRuido sensor;

    public ProcesarTurno(Expedicion expedicion) {
        this(expedicion, null);
    }

    public ProcesarTurno(Expedicion expedicion, SensorRuido sensor) {
        this.expedicion = Objects.requireNonNull(expedicion);
        this.sensor = sensor; // Sin vigilancia es una configuración válida.
    }

    public ResultadoTurno procesar(Direccion direccion) {
        Objects.requireNonNull(direccion, "Dirección obligatoria");
        AvanceExpedicion avance;
        try {
            avance = expedicion.avanzar(direccion);
        } catch (MovimientoRechazadoException rechazoEsperado) {
            return new ResultadoTurno(false, expedicion.explorador().ubicacion().id(),
                    expedicion.turnos(), expedicion.finalizada(),
                    rechazoEsperado.getMessage());
        }

        boolean huboDeteccion = notificarSensorSiCorresponde(avance);
        return new ResultadoTurno(true, avance.habitacion().id(), expedicion.turnos(),
                expedicion.finalizada(), describirAvance(avance, huboDeteccion));
    }

    private boolean notificarSensorSiCorresponde(AvanceExpedicion avance) {
        if (sensor == null || !avance.criaturaSeMovio() || avance.criatura() == null) {
            return false;
        }
        Habitacion ubicacion = avance.criatura().ubicacion();
        if (!sensor.vigila(ubicacion)) return false;

        // La notificación es síncrona: si el observador falla, el error se propaga.
        sensor.detectarMovimiento(ubicacion);
        return true;
    }

    private String describirAvance(AvanceExpedicion avance, boolean huboDeteccion) {
        Habitacion nueva = avance.habitacion();
        String mensaje = "Avanzas a " + nueva.nombre();
        if (avance.criatura() == null) return mensaje;

        Habitacion ubicacion = avance.criatura().ubicacion();
        mensaje += ". Presencia oscura en " + ubicacion.id();
        if (ubicacion == nueva) mensaje += "; la silueta está contigo (sin combate)";
        if (avance.criaturaRechazada()) mensaje += ". Movimiento de criatura rechazado";
        if (huboDeteccion) mensaje += ". Sensor " + sensor.id() + ": ruido detectado";
        return mensaje;
    }

}
