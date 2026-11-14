package liminal.comportamiento.strategy;

public final class PruebaStrategy {
    private PruebaStrategy() {}

    public static void ejecutar() {
        MapaLiminal mapa = new MapaLiminal();
        mapa.agregarHabitacion("Pasillo");
        mapa.agregarHabitacion("Piscina");
        mapa.agregarHabitacion("Oficina");
        mapa.agregarSalida("S-01", "Pasillo", "Piscina");
        mapa.agregarSalida("S-02", "Piscina", "Pasillo");
        esperarFallo(() -> mapa.agregarSalida("S-03", "Pasillo", "Fuera"));
        comprobar(mapa.destinosDesde("Pasillo").size() == 1, "Salida inválida no altera el mapa");
        MonstruoOscuro monstruo = new MonstruoOscuro("M-01", mapa, "Pasillo", new Patrullar());
        MonstruoOscuro mismaInstancia = monstruo;
        monstruo.avanzar("Oficina");
        igual("Piscina", monstruo.ubicacion());
        monstruo.cambiarEstrategia(new Perseguir());
        monstruo.avanzar("Pasillo");
        igual("Pasillo", monstruo.ubicacion());
        monstruo.cambiarEstrategia(new Esperar());
        monstruo.avanzar("Piscina");
        igual("Pasillo", monstruo.ubicacion());
        comprobar(monstruo == mismaInstancia, "Strategy no duplica al monstruo");
        monstruo.cambiarEstrategia((m, origen, objetivo) -> "Habitación inexistente");
        esperarFallo(() -> monstruo.avanzar("Piscina"));
        igual("Pasillo", monstruo.ubicacion());
    }
    private static void igual(Object esperado, Object actual) {
        if (!esperado.equals(actual)) throw new AssertionError(esperado + " != " + actual);
    }
    private static void comprobar(boolean ok, String detalle) {
        if (!ok) throw new AssertionError(detalle);
    }
    private static void esperarFallo(Runnable accion) {
        try { accion.run(); }
        catch (IllegalArgumentException | IllegalStateException esperado) { return; }
        throw new AssertionError("Falta rechazo de operación inválida");
    }
}
