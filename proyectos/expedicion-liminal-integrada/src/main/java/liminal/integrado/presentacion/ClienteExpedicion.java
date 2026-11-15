package liminal.integrado.presentacion;

import java.util.List;
import liminal.integrado.aplicacion.ProcesarTurno;
import liminal.integrado.aplicacion.ResultadoTurno;
import liminal.integrado.dominio.exploracion.Expedicion;
import liminal.integrado.dominio.exploracion.Explorador;
import liminal.integrado.dominio.mundo.Direccion;
import liminal.integrado.dominio.mundo.Habitacion;
import liminal.integrado.dominio.mundo.MapaLiminal;
import liminal.integrado.dominio.mundo.Posicion;

/** Cliente de estudio: construye la escena y narra una expedición reproducible. */
public final class ClienteExpedicion {
    private ClienteExpedicion() {}

    public static void main(String[] args) {
        Expedicion expedicion = prepararExpedicion();
        ProcesarTurno procesar = new ProcesarTurno(expedicion);
        System.out.println("EXPEDICIÓN LIMINAL — " + expedicion.id());
        System.out.println("Comienzas en " + expedicion.explorador().ubicacion().nombre());

        for (Direccion orden : List.of(Direccion.NORTE, Direccion.ESTE,
                Direccion.ESTE, Direccion.NORTE, Direccion.ESTE, Direccion.OESTE)) {
            ResultadoTurno resultado = procesar.procesar(orden);
            System.out.println(orden + " -> " + (resultado.aceptado() ? "ACEPTADO" : "RECHAZADO")
                    + " | " + resultado.mensaje() + " | " + resultado.habitacionId()
                    + " | turnos=" + resultado.turnos()
                    + " | " + (resultado.finalizada() ? "FINALIZADA" : "ACTIVA"));
        }
    }

    private static Expedicion prepararExpedicion() {
        MapaLiminal mapa = new MapaLiminal("MAP-01", 4, 3);
        Habitacion inicio = mapa.agregarHabitacion("H-01", "Pasillo amarillo", new Posicion(0, 1));
        mapa.agregarHabitacion("H-02", "Cruce de fluorescentes", new Posicion(1, 1));
        mapa.agregarHabitacion("H-03", "Oficinas silenciosas", new Posicion(2, 1));
        mapa.agregarHabitacion("H-04", "Habitación de la señal", new Posicion(2, 0));
        Habitacion meta = mapa.agregarHabitacion("H-05", "Salida segura", new Posicion(3, 0));
        mapa.agregarHabitacion("H-06", "Puesto de observación", new Posicion(1, 0));
        mapa.agregarSalida("S-01", "H-01", "H-02");
        mapa.agregarSalida("S-02", "H-02", "H-01");
        mapa.agregarSalida("S-03", "H-02", "H-03");
        mapa.agregarSalida("S-04", "H-03", "H-02");
        mapa.agregarSalida("S-05", "H-03", "H-04");
        mapa.agregarSalida("S-06", "H-04", "H-05");
        mapa.agregarSalida("S-07", "H-02", "H-06");
        mapa.agregarSalida("S-08", "H-06", "H-02");
        return new Expedicion("EXP-01", mapa, new Explorador("EX-01", mapa, inicio), meta);
    }
}

