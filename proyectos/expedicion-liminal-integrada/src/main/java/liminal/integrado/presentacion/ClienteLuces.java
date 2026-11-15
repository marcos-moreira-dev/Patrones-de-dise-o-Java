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

/** Guion 04: una luz se enciende sin intervención directa al llegar la criatura. */
public final class ClienteLuces {
    private ClienteLuces() {}

    public static void main(String[] args) {
        ejecutar().forEach(System.out::println);
    }

    public static List<String> ejecutar() {
        MapaLiminal mapa = new MapaLiminal("LUCES", 3, 2);
        Habitacion inicio = mapa.agregarHabitacion("L-01", "Refugio sin ventanas", new Posicion(0, 1));
        mapa.agregarHabitacion("L-02", "Corredor de tubos apagados", new Posicion(1, 1));
        Habitacion vigilada = mapa.agregarHabitacion("L-03", "Instalación fluorescente", new Posicion(2, 1));
        mapa.agregarHabitacion("L-04", "Cuarto negro", new Posicion(1, 0));
        Habitacion meta = mapa.agregarHabitacion("L-05", "Escalera de mantenimiento", new Posicion(2, 0));
        mapa.agregarSalida("LS-01", "L-01", "L-02");
        mapa.agregarSalida("LS-02", "L-02", "L-03");
        mapa.agregarSalida("LS-03", "L-03", "L-05");
        mapa.agregarSalida("LS-04", "L-04", "L-02");

        Expedicion sesion = new Expedicion("EXP-LUCES", mapa,
                new Explorador("EX-L", mapa, inicio), meta);
        sesion.incorporarCriatura("M-SOMBRA", mapa.habitacion("L-04"), new Patrullar());
        SensorRuido sensor = new SensorRuido("S-LUCES", vigilada);
        LuzAlerta luz = new LuzAlerta();
        Alarma alarma = new Alarma();
        sensor.suscribir(new CentroVigilancia("CENTRAL-OSCURA", sensor.id(), luz, alarma));
        ProcesarTurno caso = new ProcesarTurno(sesion, sensor);

        List<String> relato = new ArrayList<>();
        relato.add("EL MONSTRUO DETRÁS DE LAS LUCES — fluorescentes apagados");
        relato.add("LUZ INICIAL: " + (luz.encendida() ? "ENCENDIDA" : "APAGADA"));
        narrarAccion(sesion, caso, Direccion.NORTE, luz, relato); // Rechazo sin efectos.
        narrarAccion(sesion, caso, Direccion.ESTE, luz, relato);  // Criatura L-04 -> L-02.
        sesion.cambiarEstrategiaCriatura(new Perseguir());
        relato.add("La presencia cambia de patrulla a persecución.");
        narrarAccion(sesion, caso, Direccion.ESTE, luz, relato);  // Criatura L-02 -> L-03.
        sesion.cambiarEstrategiaCriatura(new Esperar());
        relato.add("La presencia deja de perseguirte.");
        narrarAccion(sesion, caso, Direccion.OESTE, luz, relato); // Rechazo sin otra alerta.
        narrarAccion(sesion, caso, Direccion.NORTE, luz, relato); // Meta, sin nueva alerta.
        relato.add("CENTRAL: LUZ " + (luz.encendida() ? "ENCENDIDA" : "APAGADA")
                + " | ALARMA " + alarma.avisos());
        relato.add("SALIDA " + (sesion.finalizada() ? "ENCONTRADA" : "NO ENCONTRADA"));
        return List.copyOf(relato);
    }

    /**
     * La presentación observa el estado anterior y posterior: nunca anuncia
     * una entrada, un encendido ni un rechazo que el dominio no confirmó.
     */
    private static void narrarAccion(Expedicion sesion, ProcesarTurno caso,
                                    Direccion orden, LuzAlerta luz, List<String> relato) {
        String antes = sesion.estadoCriatura().orElseThrow().ubicacion().id();
        boolean luzAntes = luz.encendida();
        ResultadoTurno resultado = caso.procesar(orden);
        relato.add((resultado.aceptado() ? "ACEPTADO: " : "RECHAZADO: ")
                + resultado.habitacionId() + " | " + resultado.mensaje());
        String despues = sesion.estadoCriatura().orElseThrow().ubicacion().id();
        if (resultado.aceptado() && !antes.equals(despues)) {
            relato.add("La sombra se desplaza de " + antes + " a " + despues + ".");
        }
        if (!luzAntes && luz.encendida()) {
            relato.add("Los fluorescentes de alerta se encienden tras la detección.");
        }
        if (!resultado.aceptado()) {
            relato.add("El paso falla; la sombra permanece en " + despues + ".");
        }
    }
}
