package liminal.integrado;

import java.util.List;
import liminal.integrado.dominio.comunicaciones.RadioLiminal;
import liminal.integrado.dominio.mundo.Habitacion;
import liminal.integrado.dominio.mundo.MapaLiminal;
import liminal.integrado.dominio.mundo.Posicion;
import liminal.integrado.presentacion.ClienteExpedicion;
import liminal.integrado.presentacion.ClienteLuces;
import liminal.integrado.presentacion.ClientePiscinas;
import liminal.integrado.presentacion.ClienteRadio;

/** Prueba que los tres guiones reutilizan el dominio sin acoplarse entre sí. */
public final class PruebasGuiones {
    private PruebasGuiones() {}

    public static void main(String[] args) {
        oficinas();
        piscinas();
        radio();
        luces();
        comprobarContratoRadio();
        System.out.println("OK: piscina, radio y fluorescentes con dominio compartido.");
    }

    private static void oficinas() {
        List<String> relato = ClienteExpedicion.ejecutar();
        igual(relato, ClienteExpedicion.ejecutar());
        contiene(relato, "La luz de alerta se enciende: la central recibió un aviso.");
        igual(1L, contar(relato, "La luz de alerta se enciende:"));
        igual(2L, contar(relato, "Una silueta abandona "));
        igual(2L, contar(relato, "No logras avanzar; permaneces en "));
        contiene(relato, "CENTRAL: luz ENCENDIDA | Alarma: 1");
        contiene(relato, "FINALIZADA");
    }

    private static void piscinas() {
        List<String> guion = ClientePiscinas.ejecutar();
        igual(guion, ClientePiscinas.ejecutar()); // Escena reproducible.
        contiene(guion, "P-02 != P-03");
        contiene(guion, "OESTE -> RECHAZADO | P-03 | turno=2");
        contiene(guion, "NORTE -> ACEPTADO | P-04 | turno=3");
        contiene(guion, "SALIDA ENCONTRADA");
        contiene(guion, "Los azulejos parecen iguales, pero ahora estás en P-03, no en P-02.");
        contiene(guion, "Buscas el regreso hacia OESTE; ninguna salida lo permite.");
        igual(1L, contar(guion, "RECHAZADO"));
        igual(1L, contar(guion, "Buscas el regreso hacia OESTE"));
    }

    private static void radio() {
        List<String> guion = ClienteRadio.ejecutar();
        igual(guion, ClienteRadio.ejecutar());
        contiene(guion, "RECEPCIÓN 1040 kHz: solo estática");
        contiene(guion, "CABINA 1040 kHz: ¿Sigues ahí?");
        contiene(guion, "CABINA 880 kHz: Sube al almacén.");
        contiene(guion, "ALMACÉN 880 kHz: Este mensaje fue grabado mañana.");
        contiene(guion, "Solo queda estática: aquí no hay una emisión registrada.");
        contiene(guion, "Cambias la sintonía de 1040 a 880 kHz sin salir de R-02.");
        igual(1L, contar(guion, "Cambias la sintonía de "));
        contiene(guion, "ESTE -> ACEPTADO | R-03 | turno=4");
        contiene(guion, "SALIDA ENCONTRADA");
    }

    private static void luces() {
        List<String> guion = ClienteLuces.ejecutar();
        igual(guion, ClienteLuces.ejecutar());
        contiene(guion, "LUZ INICIAL: APAGADA");
        igual(2L, contar(guion, "RECHAZADO:"));
        igual(1L, contar(guion, "ruido detectado"));
        igual(1L, contar(guion, "Los fluorescentes de alerta se encienden tras la detección."));
        igual(2L, contar(guion, "La sombra se desplaza de "));
        igual(2L, contar(guion, "El paso falla; la sombra permanece en "));
        contiene(guion, "CENTRAL: LUZ ENCENDIDA | ALARMA 1");
        contiene(guion, "SALIDA ENCONTRADA");
    }

    private static void comprobarContratoRadio() {
        MapaLiminal mapa = new MapaLiminal("RADIO", 2, 1);
        Habitacion a = mapa.agregarHabitacion("A", "Cabina", new Posicion(0, 0));
        Habitacion b = mapa.agregarHabitacion("B", "Cabina", new Posicion(1, 0));
        RadioLiminal radio = new RadioLiminal(mapa, 950);
        radio.registrarMensaje(a, 950, "Voz antigua");
        radio.registrarMensaje(a, 780, "Voz diferente");
        igual("Voz antigua", radio.escucharEn(a).orElseThrow());
        comprobar(radio.escucharEn(b).isEmpty(), "Mismo nombre no implica misma señal");
        radio.sintonizar(780);
        igual("Voz diferente", radio.escucharEn(a).orElseThrow());
        comprobar(radio.escucharEn(b).isEmpty(), "Una sala no emite en toda la red");
        esperarFallo(IllegalArgumentException.class, () -> radio.sintonizar(0));
        igual(780, radio.frecuenciaKhz()); // Rechazo no cambia la sintonía.
        esperarFallo(IllegalArgumentException.class,
                () -> radio.registrarMensaje(a, 780, "Duplicado"));
        esperarFallo(IllegalArgumentException.class,
                () -> radio.registrarMensaje(a, 650, " "));
        MapaLiminal extranjero = new MapaLiminal("OTRO", 2, 1);
        Habitacion copia = extranjero.agregarHabitacion("A", "Cabina", new Posicion(0, 0));
        esperarFallo(IllegalArgumentException.class, () -> radio.escucharEn(copia));
        esperarFallo(IllegalArgumentException.class,
                () -> radio.registrarMensaje(copia, 950, "Intruso"));
        igual("Voz diferente", radio.escucharEn(a).orElseThrow());
    }

    private static long contar(List<String> texto, String fragmento) {
        return texto.stream().filter(linea -> linea.contains(fragmento)).count();
    }

    private static void contiene(List<String> texto, String fragmento) {
        comprobar(texto.stream().anyMatch(linea -> linea.contains(fragmento)),
                "No aparece en el guion: " + fragmento);
    }

    private static void comprobar(boolean condicion, String mensaje) {
        if (!condicion) throw new AssertionError(mensaje);
    }
    private static void igual(Object esperado, Object actual) {
        if (!esperado.equals(actual)) throw new AssertionError(esperado + " != " + actual);
    }
    private static void esperarFallo(Class<? extends Throwable> tipo, Runnable accion) {
        try {
            accion.run();
        } catch (Throwable e) {
            if (tipo.isInstance(e)) return;
            throw new AssertionError("Excepción incorrecta: " + e, e);
        }
        throw new AssertionError("Se esperaba un rechazo");
    }
}
