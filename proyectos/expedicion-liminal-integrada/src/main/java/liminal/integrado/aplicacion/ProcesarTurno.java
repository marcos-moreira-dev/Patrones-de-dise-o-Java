package liminal.integrado.aplicacion;

import java.util.Objects;
import liminal.integrado.dominio.exploracion.AvanceExpedicion;
import liminal.integrado.dominio.exploracion.Expedicion;
import liminal.integrado.dominio.mundo.Direccion;
import liminal.integrado.dominio.mundo.Habitacion;
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
        } catch (IllegalStateException rechazoEsperado) {
            return new ResultadoTurno(false, expedicion.explorador().ubicacion().id(),
                    expedicion.turnos(), expedicion.finalizada(),
                    rechazoEsperado.getMessage());
        }

        Habitacion nueva = avance.habitacion();
        String mensaje = "Avanzas a " + nueva.nombre();
        if (avance.criatura() != null) {
            Habitacion ubicacion = avance.criatura().ubicacion();
            mensaje += ". Presencia oscura en " + ubicacion.id();
            if (ubicacion == nueva) mensaje += "; la silueta está contigo (sin combate)";
            if (avance.criaturaRechazada()) mensaje += ". Movimiento de criatura rechazado";
            // La aplicación integra el movimiento con un dispositivo situado en el mundo.
            // Ni Strategy ni la entidad monstruo conocen sensores, alarmas o vistas.
            if (avance.criaturaSeMovio() && sensor != null && sensor.vigila(ubicacion)) {
                sensor.detectarMovimiento(ubicacion);
                mensaje += ". Sensor " + sensor.id() + ": ruido detectado";
            }
        }
        return new ResultadoTurno(true, nueva.id(), expedicion.turnos(),
                expedicion.finalizada(), mensaje);
    }
}
