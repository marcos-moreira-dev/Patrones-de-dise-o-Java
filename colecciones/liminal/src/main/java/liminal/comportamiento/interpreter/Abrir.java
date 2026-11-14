package liminal.comportamiento.interpreter;

record Abrir(String id) implements Expresion {
    public String interpretar(Contexto contexto) {
        Puerta puerta = contexto.puerta(id);
        puerta.abrir();
        return "Puerta " + id + " ABIERTA";
    }
}
