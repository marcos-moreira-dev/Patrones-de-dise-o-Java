package liminal.integrado.dominio.exploracion;

import java.util.Objects;
import liminal.integrado.dominio.mundo.Direccion;
import liminal.integrado.dominio.mundo.Habitacion;
import liminal.integrado.dominio.mundo.MapaLiminal;
import liminal.integrado.dominio.mundo.Salida;

/** Solo el explorador cambia su posición y solo tras validar la salida del mapa. */
public final class Explorador {
    private final String id;
    private final MapaLiminal mapa;
    private Habitacion ubicacion;

    public Explorador(String id, MapaLiminal mapa, Habitacion inicio) {
        this.id = Objects.requireNonNull(id, "ID obligatorio");
        if (id.isBlank()) throw new IllegalArgumentException("ID vacío");
        this.mapa = Objects.requireNonNull(mapa);
        this.ubicacion = Objects.requireNonNull(inicio);
        if (mapa.habitacion(inicio.id()) != inicio) {
            throw new IllegalArgumentException("Ubicación inicial ajena al mapa");
        }
    }

    public String id() { return id; }
    public Habitacion ubicacion() { return ubicacion; }
    public boolean perteneceA(MapaLiminal otro) { return mapa == otro; }

    // Solo Expedicion (mismo paquete) ordena movimientos; un cliente no puede
    // saltarse el contador de turnos usando la referencia al explorador.
    Habitacion mover(Direccion direccion) {
        Salida salida = mapa.salidaDesde(ubicacion, Objects.requireNonNull(direccion));
        ubicacion = salida.destino();
        return ubicacion;
    }
}

