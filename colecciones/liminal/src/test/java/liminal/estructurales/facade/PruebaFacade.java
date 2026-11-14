package liminal.estructurales.facade;

/** Prueba independiente del recorrido pedagógico. */
public final class PruebaFacade {
    private PruebaFacade() {}

    public static void ejecutar() {
        MapaLocal mapa = new MapaLocal();
        mapa.agregarHabitacion("H-01");
        mapa.agregarHabitacion("H-02");
        mapa.agregarHabitacion("H-03");
        mapa.agregarHabitacion("H-04");
        PuertaPaso puerta = new PuertaPaso("P-01");
        mapa.agregarSalida("H-01", "H-02", puerta);
        mapa.agregarSalida("H-02", "H-03", null); // Pasillo sin puerta.
        Explorador explorador = new Explorador("E-01", "H-01", mapa);
        FachadaCruce fachada = new FachadaCruce(explorador, mapa);
        MapaLocal mapaAjeno = new MapaLocal();
        mapaAjeno.agregarHabitacion("H-01");
        mapaAjeno.agregarHabitacion("H-99");
        mapaAjeno.agregarSalida("H-01", "H-99", null);
        esperarFallo(() -> new FachadaCruce(explorador, mapaAjeno));
        if (!explorador.ubicacion().equals("H-01") || mapa.contiene("H-99")) {
            throw new AssertionError("Un mapa ajeno no debe modificar el explorador");
        }
        esperarFallo(() -> fachada.cruzar("H-02"));
        if (!explorador.ubicacion().equals("H-01")) throw new AssertionError("La puerta cerrada no permite pasar");
        puerta.abrir();
        esperarFallo(() -> fachada.cruzar("H-03"));
        if (!explorador.ubicacion().equals("H-01")) throw new AssertionError("Salida inexistente no cambia ubicación");
        fachada.cruzar("H-02");
        if (!explorador.ubicacion().equals("H-02")) throw new AssertionError("No se cruzó la salida");
        esperarFallo(() -> fachada.cruzar("H-01"));
        if (!explorador.ubicacion().equals("H-02")) throw new AssertionError("Las salidas son dirigidas");
        fachada.cruzar("H-03");
        if (!explorador.ubicacion().equals("H-03")) {
            throw new AssertionError("R-04: una salida sin puerta debe ser transitable");
        }
        esperarFallo(() -> fachada.cruzar("H-04")); // Habitación existente, sin salida.
        if (!explorador.ubicacion().equals("H-03")) {
            throw new AssertionError("Un cruce inválido no debe mover al explorador");
        }
        esperarFallo(() -> new PuertaPaso(" "));
        if (!explorador.ubicacion().equals("H-03")) {
            throw new AssertionError("R-01: el rechazo de puerta sin ID no altera la ubicación");
        }
    }

    private static void esperarFallo(Runnable accion) {
        try { accion.run(); }
        catch (IllegalArgumentException | IllegalStateException esperado) { return; }
        throw new AssertionError("Cruce inválido aceptado");
    }
}
