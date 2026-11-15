package liminal.creacionales.abstractfactory;

/** Producto concreto de la familia amarilla; solo describe su estado inicial. */
final class PuertaAmarilla implements PuertaDelNivel {
    public Tema tema() { return Tema.AMARILLO; }
    public String aspecto() { return "puerta beige"; }
    public String estado() { return "CERRADA"; }
}
