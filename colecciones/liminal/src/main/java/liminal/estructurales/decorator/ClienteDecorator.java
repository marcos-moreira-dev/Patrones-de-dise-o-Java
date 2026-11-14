package liminal.estructurales.decorator;

import java.io.PrintStream;
import java.util.Objects;

/** Decorator: las mejoras envuelven a la linterna sin reemplazar su contrato. */
public final class ClienteDecorator {
    private ClienteDecorator() {}

    public static void ejecutar(PrintStream out) {
        Linterna base = new LinternaBasica(5);
        Linterna mejorada = new FiltroAzul(new LenteDeAlcance(base, 3));
        out.println("Base: " + base.descripcion() + " (" + base.alcance() + " m)");
        out.println("Mejorada: " + mejorada.descripcion() + " (" + mejorada.alcance() + " m)");
    }

    public static void probar() {
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
    public static void main(String[] args) { ejecutar(System.out); }
}
