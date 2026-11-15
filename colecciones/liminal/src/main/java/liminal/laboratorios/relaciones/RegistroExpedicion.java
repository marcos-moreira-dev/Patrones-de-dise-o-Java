package liminal.laboratorios.relaciones;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/** Composición según R-09: crea, posee e invalida sus entradas al cerrar. */
final class RegistroExpedicion {
    private final String id;
    private final Map<String, EntradaRegistro> entradas = new LinkedHashMap<>();
    private boolean abierto = true;

    RegistroExpedicion(String id) { this.id = Reglas.exigirTexto(id); }
    String id() { return id; }
    boolean abierto() { return abierto; }
    int entradasActivas() { return entradas.size(); }

    VistaEntrada anotar(String idEntrada, String texto) {
        if (!abierto) throw new IllegalStateException("Registro cerrado");
        Reglas.exigirTexto(idEntrada);
        Reglas.exigirTexto(texto);
        if (entradas.containsKey(idEntrada)) {
            throw new IllegalArgumentException("ID de entrada repetido en el registro");
        }
        EntradaRegistro entrada = new EntradaRegistro(idEntrada, texto, id);
        entradas.put(idEntrada, entrada);
        return entrada;
    }

    List<VistaEntrada> consultarActivas() {
        return List.copyOf(entradas.values());
    }

    void cerrar() {
        if (!abierto) return;
        for (EntradaRegistro entrada : entradas.values()) entrada.invalidar();
        entradas.clear();
        abierto = false;
    }

    /** El constructor privado evita crear o transferir entradas desde otros registros. */
    private static final class EntradaRegistro implements VistaEntrada {
        private final String id;
        private final String texto;
        private final String idRegistro;
        private boolean activa = true;

        private EntradaRegistro(String id, String texto, String idRegistro) {
            this.id = id;
            this.texto = texto;
            this.idRegistro = idRegistro;
        }
        public String id() { return id; }
        public String texto() { return texto; }
        public String idRegistro() { return idRegistro; }
        public boolean activa() { return activa; }
        private void invalidar() { activa = false; }
    }
}
