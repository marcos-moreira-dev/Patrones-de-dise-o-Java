package liminal.laboratorios.java.classvsrecord;

import java.io.PrintStream;
import java.util.HashMap;
import java.util.Map;

/** Compara un valor para caché con una entidad que conserva identidad propia. */
public final class ClienteClassVsRecord {
    private ClienteClassVsRecord() {}

    public static void main(String[] args) { ejecutar(System.out); }

    public static void ejecutar(PrintStream out) {
        demostrarClaveDeTextura(out);
        demostrarEntidadConIdentidad(out);
    }

    private static void demostrarClaveDeTextura(PrintStream out) {
        ClaveTextura primera = new ClaveTextura("alfombra", "amarillo repetido");
        ClaveTextura equivalente = new ClaveTextura("alfombra", "amarillo repetido");
        Map<ClaveTextura, String> cache = new HashMap<>();
        cache.put(primera, "Textura cargada una vez");
        out.println("RECORD: dos claves equivalentes -> " + primera.equals(equivalente));
        out.println("CACHE: consultar con otra clave -> " + cache.get(equivalente));
    }

    private static void demostrarEntidadConIdentidad(PrintStream out) {
        Habitacion una = new Habitacion("H-01", "Pasillo amarillo", 4);
        Habitacion otraReferencia = new Habitacion("H-01", "Pasillo amarillo", 2);
        out.println("CLASS: misma instancia Java -> " + (una == otraReferencia));
        out.println("DOMINIO: mismo ID -> " + una.mismaIdentidadDeDominio(otraReferencia));
        una.cambiarLuces(3);
        out.println("ESTADO: luces de H-01 -> " + una.lucesEncendidas());
    }
}
