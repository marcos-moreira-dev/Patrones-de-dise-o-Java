package liminal.comportamiento.command.variantes.deshacer;

import java.util.Objects;

/** Undo/redo seguro: no restaura estados obsoletos por la fuerza. */
final class AbrirPuerta implements ComandoReversible {
    private final Puerta puerta;
    private boolean aplicado;
    private boolean deshecho;
    private int revisionEsperada;

    AbrirPuerta(Puerta puerta) { this.puerta = Objects.requireNonNull(puerta); }

    @Override
    public void ejecutar() {
        if (aplicado && !deshecho) throw new IllegalStateException("Comando ya ejecutado");
        if (deshecho && puerta.revision() != revisionEsperada) {
            throw new IllegalStateException("No se puede rehacer una acción obsoleta");
        }
        puerta.abrir();
        aplicado = true;
        deshecho = false;
        revisionEsperada = puerta.revision();
    }

    @Override
    public void deshacer() {
        if (!aplicado || deshecho || puerta.revision() != revisionEsperada) {
            throw new IllegalStateException("No se puede deshacer una acción obsoleta");
        }
        puerta.cerrar();
        deshecho = true;
        revisionEsperada = puerta.revision();
    }
}
