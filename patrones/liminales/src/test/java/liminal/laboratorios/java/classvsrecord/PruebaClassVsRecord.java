package liminal.laboratorios.java.classvsrecord;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public final class PruebaClassVsRecord {
    private PruebaClassVsRecord() {}

    public static void ejecutar() {
        unaClaveDeValorFuncionaEnMap();
        entidadEsDistintaDelValor();
        rechazarValoresInvalidos();
        verificarSalida();
    }

    private static void unaClaveDeValorFuncionaEnMap() {
        ClaveTextura a = new ClaveTextura("alfombra", "amarillo");
        ClaveTextura b = new ClaveTextura("alfombra", "amarillo");
        ClaveTextura c = new ClaveTextura("azulejo", "amarillo");
        Map<ClaveTextura, String> texturas = new HashMap<>();
        texturas.put(a, "cargado");
        comprobar(a != b && a.equals(b) && a.hashCode() == b.hashCode(),
                "Igualdad estructural del record");
        comprobar("cargado".equals(texturas.get(b)), "Clave estable en Map");
        comprobar(texturas.get(c) == null, "Valores diferentes producen claves distintas");
    }

    private static void entidadEsDistintaDelValor() {
        Habitacion a = new Habitacion("H-01", "Pasillo", 3);
        Habitacion b = new Habitacion("H-01", "Pasillo", 9);
        comprobar(a != b && a.mismaIdentidadDeDominio(b), "Instancia frente a ID");
        comprobar(!a.equals(b), "No sobreescribimos equals por accidente");
        a.cambiarLuces(4);
        comprobar(a.lucesEncendidas() == 4 && b.lucesEncendidas() == 9,
                "Cada entidad conserva su estado separado");
    }

    private static void rechazarValoresInvalidos() {
        esperarFallo(() -> new ClaveTextura(" ", "amarillo"));
        esperarFallo(() -> new Habitacion("", "Pasillo", 0));
        Habitacion h = new Habitacion("H-02", "Piscinas", 0);
        esperarFallo(() -> h.cambiarLuces(-1));
        comprobar(h.lucesEncendidas() == 0, "Un fallo no debe modificar el estado");
    }

    private static void verificarSalida() {
        ByteArrayOutputStream buf = new ByteArrayOutputStream();
        ClienteClassVsRecord.ejecutar(new PrintStream(buf, true, StandardCharsets.UTF_8));
        String salida = buf.toString(StandardCharsets.UTF_8);
        comprobar(salida.contains("RECORD: dos claves equivalentes -> true"), "Valor");
        comprobar(salida.contains("CLASS: misma instancia Java -> false"), "Instancia");
        comprobar(salida.contains("DOMINIO: mismo ID -> true"), "Identidad");
    }

    private static void comprobar(boolean condicion, String error) {
        if (!condicion) throw new AssertionError(error);
    }

    private static void esperarFallo(Runnable accion) {
        try { accion.run(); }
        catch (IllegalArgumentException esperado) { return; }
        throw new AssertionError("Debía rechazar el dato inválido");
    }
}
