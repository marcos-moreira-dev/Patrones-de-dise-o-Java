package liminal.integrado.presentacion;

import java.util.ArrayList;
import java.util.List;
import liminal.integrado.aplicacion.ProcesarTurno;
import liminal.integrado.aplicacion.ResultadoTurno;
import liminal.integrado.dominio.criaturas.Esperar;
import liminal.integrado.dominio.criaturas.Patrullar;
import liminal.integrado.dominio.criaturas.Perseguir;
import liminal.integrado.dominio.exploracion.Expedicion;
import liminal.integrado.dominio.exploracion.Explorador;
import liminal.integrado.dominio.mundo.Direccion;
import liminal.integrado.dominio.mundo.Habitacion;
import liminal.integrado.dominio.mundo.MapaLiminal;
import liminal.integrado.dominio.mundo.Posicion;
import liminal.integrado.dominio.vigilancia.Alarma;
import liminal.integrado.dominio.vigilancia.CentroVigilancia;
import liminal.integrado.dominio.vigilancia.LuzAlerta;
import liminal.integrado.dominio.vigilancia.SensorRuido;

/** Cliente de estudio: prepara la expedición y relata un guion reproducible. */
public final class ClienteExpedicion {
    private ClienteExpedicion() {}

    public static void main(String[] args) {
        ejecutar().forEach(System.out::println);
    }

    /** Narración verificable sin capturar System.out en las pruebas. */
    public static List<String> ejecutar() {
        Escena escena = prepararExpedicion();
        ProcesarTurno caso = new ProcesarTurno(escena.expedicion(), escena.sensor());
        List<String> relato = new ArrayList<>();
        narrarInicio(escena, relato);

        List<Direccion> recorrido = List.of(Direccion.NORTE, Direccion.ESTE,
                Direccion.ESTE, Direccion.NORTE, Direccion.ESTE, Direccion.OESTE);
        for (int paso = 0; paso < recorrido.size(); paso++) {
            cambiarEstrategiaSegunPaso(paso, escena.expedicion(), relato);
            narrarTurno(escena, caso, recorrido.get(paso), relato);
        }
        narrarCierre(escena, relato);
        return List.copyOf(relato);
    }

    private static void narrarInicio(Escena escena, List<String> relato) {
        Expedicion expedicion = escena.expedicion();
        relato.add("EXPEDICIÓN LIMINAL — " + expedicion.id());
        relato.add("Comienzas en " + expedicion.explorador().ubicacion().nombre()
                + "; un zumbido fluorescente acompaña el pasillo.");
        relato.add("Monstruo oscuro M-01: PATRULLAR desde "
                + expedicion.estadoCriatura().orElseThrow().ubicacion().id());
    }

    private static void cambiarEstrategiaSegunPaso(int paso, Expedicion expedicion,
                                                    List<String> relato) {
        if (paso == 2) {
            expedicion.cambiarEstrategiaCriatura(new Perseguir());
            relato.add("Monstruo oscuro: PERSEGUIR");
        } else if (paso == 3) {
            expedicion.cambiarEstrategiaCriatura(new Esperar());
            relato.add("Monstruo oscuro: ESPERAR");
        }
    }

    private static void narrarTurno(Escena escena, ProcesarTurno caso,
                                    Direccion orden, List<String> relato) {
        Expedicion expedicion = escena.expedicion();
        String criaturaAntes = expedicion.estadoCriatura().orElseThrow().ubicacion().id();
        boolean luzAntes = escena.luz().encendida();
        ResultadoTurno resultado = caso.procesar(orden);
        relato.add(orden + " -> " + (resultado.aceptado() ? "ACEPTADO" : "RECHAZADO")
                + " | " + resultado.mensaje() + " | " + resultado.habitacionId()
                + " | turnos=" + resultado.turnos()
                + " | " + (resultado.finalizada() ? "FINALIZADA" : "ACTIVA"));
        String criaturaDespues = expedicion.estadoCriatura().orElseThrow().ubicacion().id();
        if (resultado.aceptado() && !criaturaAntes.equals(criaturaDespues)) {
            relato.add("Una silueta abandona " + criaturaAntes + " y alcanza "
                    + criaturaDespues + ".");
        }
        if (!luzAntes && escena.luz().encendida()) {
            relato.add("La luz de alerta se enciende: la central recibió un aviso.");
        }
        if (!resultado.aceptado()) {
            relato.add("No logras avanzar; permaneces en " + resultado.habitacionId()
                    + " sin consumir otro turno.");
        }
    }

    private static void narrarCierre(Escena escena, List<String> relato) {
        relato.add("CENTRAL: luz " + (escena.luz().encendida() ? "ENCENDIDA" : "APAGADA")
                + " | Alarma: " + escena.alarma().avisos());
    }

    private static Escena prepararExpedicion() {
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
        Expedicion expedicion = new Expedicion(
                "EXP-01", mapa, new Explorador("EX-01", mapa, inicio), meta);
        expedicion.incorporarCriatura("M-01", mapa.habitacion("H-06"), new Patrullar());

        // Observer notifica al centro; Mediator coordina dos receptores.
        SensorRuido sensor = new SensorRuido("SR-03", mapa.habitacion("H-03"));
        LuzAlerta luz = new LuzAlerta();
        Alarma alarma = new Alarma();
        sensor.suscribir(new CentroVigilancia("CV-01", sensor.id(), luz, alarma));
        return new Escena(expedicion, sensor, luz, alarma);
    }

    private record Escena(Expedicion expedicion, SensorRuido sensor,
                          LuzAlerta luz, Alarma alarma) {}
}
