package liminal.comportamiento.strategy;

import java.io.PrintStream;

/** Strategy: el mismo monstruo cambia de algoritmo sin cambiar de identidad. */
public final class ClienteStrategy {
    private ClienteStrategy() {}

    public static void ejecutar(PrintStream out) {
        MapaLiminal mapa = new MapaLiminal();
        mapa.agregarHabitacion("Pasillo amarillo");
        mapa.agregarHabitacion("Piscina vacía");
        mapa.agregarSalida("S-01", "Pasillo amarillo", "Piscina vacía");
        mapa.agregarSalida("S-02", "Piscina vacía", "Pasillo amarillo");
        MonstruoOscuro monstruo = new MonstruoOscuro("M-01", mapa, "Pasillo amarillo", new Patrullar());
        out.println("Monstruo oscuro: " + monstruo.ubicacion());
        monstruo.avanzar("Pasillo amarillo");
        out.println("Patrullar -> " + monstruo.ubicacion());
        monstruo.cambiarEstrategia(new Perseguir());
        monstruo.avanzar("Pasillo amarillo");
        out.println("Perseguir -> " + monstruo.ubicacion());
        monstruo.cambiarEstrategia(new Esperar());
        monstruo.avanzar("Piscina vacía");
        out.println("Esperar -> " + monstruo.ubicacion());
    }

    public static void main(String[] args) { ejecutar(System.out); }
}
