package liminal.integrado.aplicacion;

import java.util.Objects;
import liminal.integrado.dominio.exploracion.Expedicion;
import liminal.integrado.dominio.criaturas.MonstruoOscuro;
import liminal.integrado.dominio.mundo.Direccion;
import liminal.integrado.dominio.mundo.Habitacion;

/** Caso de uso: devuelve un resultado narrable sin imprimir ni revalidar el mapa. */
public final class ProcesarTurno {
    private final Expedicion expedicion;

    public ProcesarTurno(Expedicion expedicion) {
        this.expedicion = Objects.requireNonNull(expedicion);
    }

    public ResultadoTurno procesar(Direccion direccion) {
        Objects.requireNonNull(direccion, "Dirección obligatoria");
        Habitacion nueva;
        try {
            nueva = expedicion.avanzar(direccion);
        } catch (IllegalStateException rechazoEsperado) {
            // Solo traduce el rechazo del explorador (ruta o sesión).
            // Una estrategia defectuosa no debe convertir un paso YA válido en rechazo.
            return new ResultadoTurno(false, expedicion.explorador().ubicacion().id(),
                    expedicion.turnos(), expedicion.finalizada(),
                    rechazoEsperado.getMessage());
        }

        String mensaje = "Avanzas a " + nueva.nombre();
        MonstruoOscuro criatura = expedicion.criatura();
        if (criatura != null) {
            try {
                Habitacion ubicacion = criatura.avanzar(nueva);
                mensaje += ". Presencia oscura en " + ubicacion.id();
                if (ubicacion == nueva) {
                    mensaje += "; la silueta está contigo (sin combate)";
                }
            } catch (IllegalStateException movimientoInvalido) {
                // Son dos acciones de dominio: el paso del explorador permanece válido;
                // únicamente el intento de la criatura se rechaza sin moverla.
                mensaje += ". Movimiento de criatura rechazado: "
                        + movimientoInvalido.getMessage();
            }
        }
        return new ResultadoTurno(true, nueva.id(), expedicion.turnos(),
                expedicion.finalizada(), mensaje);
    }
}

