package liminal.estructurales.decorator;

/** Prueba independiente del recorrido pedagógico. */
public final class PruebaDecorator {
    private PruebaDecorator() {}

    public static void ejecutar() {
        Linterna base = new LinternaBasica(5);
        Linterna mejorada = new FiltroAzul(new LenteDeAlcance(base, 3));
        if (base.alcance() != 5 || mejorada.alcance() != 8) {
            throw new AssertionError("El envoltorio debe añadir alcance sin mutar la base");
        }
        if (!mejorada.descripcion().contains("filtro azul")) throw new AssertionError("Falta el filtro");
        try {
            new LenteDeAlcance(base, -1);
            throw new AssertionError("No se permite una mejora negativa");
        } catch (IllegalArgumentException esperado) {
            if (base.alcance() != 5) throw new AssertionError("La base no debe mutar");
        }
    }
}
