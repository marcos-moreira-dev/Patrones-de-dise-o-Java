package liminal.integrado.aplicacion;

import java.util.Objects;
import liminal.integrado.dominio.exploracion.Expedicion;
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
        try {
            Habitacion nueva = expedicion.avanzar(direccion);
            return new ResultadoTurno(true, nueva.id(), expedicion.turnos(),
                    expedicion.finalizada(), "Avanzas a " + nueva.nombre());
        } catch (IllegalStateException rechazoEsperado) {
            // Solo traduce el rechazo de dominio (salida ausente/sesión terminada).
            // Otros errores, como argumentos nulos, siguen siendo excepciones.
            return new ResultadoTurno(false, expedicion.explorador().ubicacion().id(),
                    expedicion.turnos(), expedicion.finalizada(),
                    rechazoEsperado.getMessage());
        }
    }
}

