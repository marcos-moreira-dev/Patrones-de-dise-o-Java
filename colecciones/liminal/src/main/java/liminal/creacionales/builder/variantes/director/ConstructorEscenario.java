package liminal.creacionales.builder.variantes.director;

/** Builder GoF: pasos que el Director puede ordenar. */
interface ConstructorEscenario {
    void comenzar(String id);
    void elegirAmbiente(String nombre);
    void instalarLuces(int cantidad);
    void colocarPuerta(boolean conPuerta);
    Habitacion construir();
}
