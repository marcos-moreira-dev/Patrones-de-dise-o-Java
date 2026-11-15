package liminal.integrado.presentacion;

import java.util.ArrayList;
import java.util.List;
import liminal.integrado.aplicacion.ProcesarTurno;
import liminal.integrado.aplicacion.ResultadoTurno;
import liminal.integrado.dominio.comunicaciones.RadioLiminal;
import liminal.integrado.dominio.exploracion.Expedicion;
import liminal.integrado.dominio.exploracion.Explorador;
import liminal.integrado.dominio.mundo.Direccion;
import liminal.integrado.dominio.mundo.Habitacion;
import liminal.integrado.dominio.mundo.MapaLiminal;
import liminal.integrado.dominio.mundo.Posicion;

/** Guion 03: la radio parece responder, pero transmite mensajes fijos del mundo. */
public final class ClienteRadio {
    private ClienteRadio() {}

    public static void main(String[] args) {
        ejecutar().forEach(System.out::println);
    }

    public static List<String> ejecutar() {
        MapaLiminal mapa = new MapaLiminal("RADIO", 3, 2);
        Habitacion inicio = mapa.agregarHabitacion("R-01", "Recepción vacía", new Posicion(0, 1));
        Habitacion cabina = mapa.agregarHabitacion("R-02", "Cabina de radio", new Posicion(1, 1));
        Habitacion meta = mapa.agregarHabitacion("R-03", "Salida de mantenimiento", new Posicion(2, 1));
        Habitacion almacen = mapa.agregarHabitacion("R-04", "Almacén sin relojes", new Posicion(1, 0));
        mapa.agregarSalida("RS-01", "R-01", "R-02");
        mapa.agregarSalida("RS-02", "R-02", "R-04");
        mapa.agregarSalida("RS-03", "R-04", "R-02");
        mapa.agregarSalida("RS-04", "R-02", "R-03");

        RadioLiminal radio = new RadioLiminal(mapa, 1040);
        radio.registrarMensaje(cabina, 1040, "¿Sigues ahí? No mires el reloj.");
        radio.registrarMensaje(cabina, 880, "Sube al almacén. Hay otra señal.");
        radio.registrarMensaje(almacen, 880, "Este mensaje fue grabado mañana.");

        Expedicion sesion = new Expedicion("EXP-RADIO", mapa,
                new Explorador("EX-R", mapa, inicio), meta);
        ProcesarTurno caso = new ProcesarTurno(sesion);
        List<String> relato = new ArrayList<>();
        relato.add("LA RADIO QUE RESPONDE SOLA — emisiones predefinidas");
        relato.add("RECEPCIÓN 1040 kHz: "
                + radio.escucharEn(sesion.explorador().ubicacion()).orElse("solo estática"));

        avanzar(caso, Direccion.ESTE, relato);
        relato.add("CABINA 1040 kHz: "
                + radio.escucharEn(sesion.explorador().ubicacion()).orElse("solo estática"));
        radio.sintonizar(880);
        relato.add("CABINA 880 kHz: "
                + radio.escucharEn(sesion.explorador().ubicacion()).orElse("solo estática"));
        avanzar(caso, Direccion.NORTE, relato);
        relato.add("ALMACÉN 880 kHz: "
                + radio.escucharEn(sesion.explorador().ubicacion()).orElse("solo estática"));
        avanzar(caso, Direccion.SUR, relato);
        avanzar(caso, Direccion.ESTE, relato);
        relato.add("SALIDA " + (sesion.finalizada() ? "ENCONTRADA" : "NO ENCONTRADA"));
        return List.copyOf(relato);
    }

    private static void avanzar(ProcesarTurno caso, Direccion orden, List<String> relato) {
        ResultadoTurno r = caso.procesar(orden);
        relato.add(orden + " -> " + (r.aceptado() ? "ACEPTADO" : "RECHAZADO")
                + " | " + r.habitacionId() + " | turno=" + r.turnos());
    }
}
