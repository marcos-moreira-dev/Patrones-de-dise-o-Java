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

interface Linterna {
    int alcance();
    String descripcion();
}

record LinternaBasica(int alcance) implements Linterna {
    LinternaBasica {
        if (alcance < 0) throw new IllegalArgumentException("Alcance negativo");
    }
    public String descripcion() { return "linterna básica"; }
}

abstract class DecoradorLinterna implements Linterna {
    protected final Linterna envuelta;
    DecoradorLinterna(Linterna envuelta) { this.envuelta = Objects.requireNonNull(envuelta); }
    public int alcance() { return envuelta.alcance(); }
    public String descripcion() { return envuelta.descripcion(); }
}
final class LenteDeAlcance extends DecoradorLinterna {
    private final int extra;
    LenteDeAlcance(Linterna base, int extra) {
        super(base);
        if (extra < 0) throw new IllegalArgumentException("Mejora negativa");
        this.extra = extra;
    }
    public int alcance() { return Math.addExact(super.alcance(), extra); }
    public String descripcion() { return super.descripcion() + " con lente"; }
}
final class FiltroAzul extends DecoradorLinterna {
    FiltroAzul(Linterna base) { super(base); }
    public String descripcion() { return super.descripcion() + " y filtro azul"; }
}
