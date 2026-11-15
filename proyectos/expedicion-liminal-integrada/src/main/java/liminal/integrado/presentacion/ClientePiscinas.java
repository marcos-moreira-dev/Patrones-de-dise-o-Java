package liminal.integrado.presentacion;

import java.util.ArrayList;
import java.util.List;
import liminal.integrado.aplicacion.ProcesarTurno;
import liminal.integrado.aplicacion.ResultadoTurno;
import liminal.integrado.dominio.exploracion.Expedicion;
import liminal.integrado.dominio.exploracion.Explorador;
import liminal.integrado.dominio.mundo.Direccion;
import liminal.integrado.dominio.mundo.Habitacion;
import liminal.integrado.dominio.mundo.MapaLiminal;
import liminal.integrado.dominio.mundo.Posicion;

/** Guion 02: las salas parecen copias, pero tienen identidad y salidas distintas. */
public final class ClientePiscinas {
    private ClientePiscinas() {}

    public static void main(String[] args) {
        ejecutar().forEach(System.out::println);
    }

    public static List<String> ejecutar() {
        EscenaPiscinas escena = prepararEscena();
        return recorrerPiscinas(escena);
    }

    private static EscenaPiscinas prepararEscena() {
        MapaLiminal mapa = new MapaLiminal("PISCINAS", 3, 2);
        Habitacion inicio = mapa.agregarHabitacion("P-01", "Vestíbulo húmedo", new Posicion(0, 1));
        Habitacion primera = mapa.agregarHabitacion("P-02", "Piscina de azulejos", new Posicion(1, 1));
        Habitacion segunda = mapa.agregarHabitacion("P-03", "Piscina de azulejos", new Posicion(2, 1));
        Habitacion meta = mapa.agregarHabitacion("P-04", "Escalera seca", new Posicion(2, 0));
        mapa.agregarHabitacion("P-05", "Cuarto de filtros", new Posicion(1, 0));
        mapa.agregarSalida("PS-01", "P-01", "P-02");
        mapa.agregarSalida("PS-02", "P-02", "P-03");
        mapa.agregarSalida("PS-03", "P-03", "P-04");
        mapa.agregarSalida("PS-04", "P-02", "P-05");
        mapa.agregarSalida("PS-05", "P-05", "P-02");

        Expedicion sesion = new Expedicion("EXP-PISCINAS", mapa,
                new Explorador("EX-P", mapa, inicio), meta);
        ProcesarTurno caso = new ProcesarTurno(sesion);
        return new EscenaPiscinas(sesion, caso, primera, segunda);
    }

    private static List<String> recorrerPiscinas(EscenaPiscinas escena) {
        List<String> relato = new ArrayList<>();
        relato.add("PISCINA INTERMINABLE — habitaciones iguales, identidades distintas");
        relato.add(escena.primera().nombre() + ": " + escena.primera().id()
                + " != " + escena.segunda().id());
        for (Direccion orden : List.of(Direccion.ESTE, Direccion.ESTE,
                Direccion.OESTE, Direccion.NORTE)) {
            narrarPaso(escena, orden, relato);
        }
        relato.add("SALIDA " + (escena.sesion().finalizada()
                ? "ENCONTRADA" : "NO ENCONTRADA"));
        return List.copyOf(relato);
    }

    private static void narrarPaso(EscenaPiscinas escena, Direccion orden,
                                   List<String> relato) {
        ResultadoTurno resultado = escena.caso().procesar(orden);
        relato.add(orden + " -> " + (resultado.aceptado() ? "ACEPTADO" : "RECHAZADO")
                + " | " + resultado.habitacionId() + " | turno=" + resultado.turnos());
        if (resultado.aceptado() && resultado.habitacionId().equals(escena.segunda().id())) {
            relato.add("Los azulejos parecen iguales, pero ahora estás en "
                    + escena.segunda().id() + ", no en " + escena.primera().id() + ".");
        }
        if (!resultado.aceptado() && resultado.habitacionId().equals(escena.segunda().id())) {
            relato.add("Buscas el regreso hacia OESTE; ninguna salida lo permite."
                    + " Sigues en " + escena.segunda().id() + " durante el turno "
                    + resultado.turnos() + ".");
        }
    }

    private record EscenaPiscinas(Expedicion sesion, ProcesarTurno caso,
                                  Habitacion primera, Habitacion segunda) {}

}
