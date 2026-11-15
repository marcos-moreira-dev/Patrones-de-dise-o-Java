package liminal.laboratorios.relaciones;

import java.util.Objects;

/** Asociación de consulta: el informe no puede adoptar ni revivir entradas. */
final class InformeExpedicion {
    private final VistaEntrada entrada;

    InformeExpedicion(VistaEntrada entrada) { this.entrada = Objects.requireNonNull(entrada); }
    VistaEntrada entrada() { return entrada; }
    String consultar() { return entrada.activa() ? entrada.texto() : "entrada no disponible"; }
}
