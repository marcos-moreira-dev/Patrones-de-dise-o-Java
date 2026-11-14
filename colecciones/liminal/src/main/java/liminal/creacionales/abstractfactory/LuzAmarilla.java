package liminal.creacionales.abstractfactory;

/** Producto concreto de la familia amarilla. */
record LuzAmarilla() implements LuzDelNivel {
    public Tema tema() { return Tema.AMARILLO; }
    public String aspecto() { return "fluorescente amarilla"; }
}
