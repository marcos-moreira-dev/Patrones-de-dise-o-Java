package liminal.comportamiento.strategy;

import java.io.PrintStream;

/** Strategy: una misma criatura cambia su política de movimiento. */
public final class ClienteStrategy {
    private ClienteStrategy() {}

    public static void main(String[] args) { ejecutar(System.out); }

    public static void ejecutar(PrintStream out) {
        MapaLiminal mapa = prepararMapa();
        MonstruoOscuro monstruo = new MonstruoOscuro(
                "M-01", mapa, "Pasillo amarillo", new Patrullar());
        out.println("Monstruo oscuro: " + monstruo.ubicacion());
        patrullar(monstruo, out);
        perseguir(monstruo, out);
        esperar(monstruo, out);
    }

    private static MapaLiminal prepararMapa() {
        MapaLiminal mapa = new MapaLiminal();
        mapa.agregarHabitacion("Pasillo amarillo");
        mapa.agregarHabitacion("Piscina vacía");
        mapa.agregarSalida("S-01", "Pasillo amarillo", "Piscina vacía");
        mapa.agregarSalida("S-02", "Piscina vacía", "Pasillo amarillo");
        return mapa;
    }

    private static void patrullar(MonstruoOscuro monstruo, PrintStream out) {
        monstruo.avanzar("Pasillo amarillo");
        out.println("Patrullar -> " + monstruo.ubicacion());
    }

    private static void perseguir(MonstruoOscuro monstruo, PrintStream out) {
        monstruo.cambiarEstrategia(new Perseguir());
        monstruo.avanzar("Pasillo amarillo");
        out.println("Perseguir -> " + monstruo.ubicacion());
    }

    private static void esperar(MonstruoOscuro monstruo, PrintStream out) {
        monstruo.cambiarEstrategia(new Esperar());
        monstruo.avanzar("Piscina vacía");
        out.println("Esperar -> " + monstruo.ubicacion());
    }
}
