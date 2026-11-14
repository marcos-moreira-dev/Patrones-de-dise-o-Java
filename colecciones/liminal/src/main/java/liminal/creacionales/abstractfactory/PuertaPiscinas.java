package liminal.creacionales.abstractfactory;

/** Producto concreto de la familia de piscinas; solo describe su estado inicial. */
record PuertaPiscinas() implements PuertaDelNivel {
    public Tema tema() { return Tema.PISCINAS; }
    public String aspecto() { return "puerta blanca"; }
    public String estado() { return "CERRADA"; }
}
