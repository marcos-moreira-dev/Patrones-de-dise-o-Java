package liminal.estructurales.decorator;

record LinternaBasica(int alcance) implements Linterna {
    LinternaBasica {
        if (alcance < 0) throw new IllegalArgumentException("Alcance negativo");
    }
    public String descripcion() { return "linterna básica"; }
}
