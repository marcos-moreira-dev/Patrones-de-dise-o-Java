package liminal.estructurales.decorator;

import java.io.PrintStream;

/** Decorator: las mejoras envuelven a la linterna sin reemplazar su contrato. */
public final class ClienteDecorator {
    private ClienteDecorator() {}

    public static void ejecutar(PrintStream out) {
        Linterna base = new LinternaBasica(5);
        Linterna mejorada = new FiltroAzul(new LenteDeAlcance(base, 3));
        out.println("Base: " + base.descripcion() + " (" + base.alcance() + " m)");
        out.println("Mejorada: " + mejorada.descripcion() + " (" + mejorada.alcance() + " m)");
    }

    public static void main(String[] args) { ejecutar(System.out); }
}
