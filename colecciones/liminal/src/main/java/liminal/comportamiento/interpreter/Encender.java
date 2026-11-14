package liminal.comportamiento.interpreter;

record Encender(String id) implements Expresion {
    public String interpretar(Contexto contexto) {
        Luz luz = contexto.luz(id);
        luz.encender();
        return "Luz " + id + " ENCENDIDA";
    }
}
