package liminal.estructurales.adapter;

/** Pruebas del patrón, fuera del cliente pedagógico. */
public final class PruebaAdapter {
    private PruebaAdapter() {}

    public static void ejecutar() {
        RadioAntigua radio = new RadioAntigua();
        Comunicador equipo = new AdaptadorRadioAntigua(radio);
        equipo.enviar("Luz encendida");
        if (!radio.ultimaEmision().equals("Radio antigua: Luz encendida")) {
            throw new AssertionError("Adapter debe traducir enviar a emitir");
        }
        try {
            equipo.enviar(" ");
            throw new AssertionError("Debe rechazar mensajes vacíos");
        } catch (IllegalArgumentException esperado) {
            if (!radio.ultimaEmision().equals("Radio antigua: Luz encendida")) {
                throw new AssertionError("No debe mutar ante error");
            }
        }
    }
}
