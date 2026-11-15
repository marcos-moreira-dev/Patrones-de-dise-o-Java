package liminal.comportamiento.templatemethod;

import java.util.List;

abstract class RutaExploracion {
    // El algoritmo de pasos es fijo; cada ruta solo especializa observar().
    // Así se evitan secuencias divergentes de preparación y cierre.
    public final List<String> recorrer() {
        return List.of(preparar(), observar(), finalizar());
    }
    private String preparar() { return "Preparar linterna"; }
    protected abstract String observar();
    private String finalizar() { return "Regresar al punto seguro"; }
}
