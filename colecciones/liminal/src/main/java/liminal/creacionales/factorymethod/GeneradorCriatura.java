package liminal.creacionales.factorymethod;

import java.util.Objects;

abstract class GeneradorCriatura {
    public final Criatura aparecer(String id, String habitacion, RegistroApariciones registro) {
        Objects.requireNonNull(registro);
        // Se valida antes de crear y se registra después de comprobar el producto:
        // una fábrica defectuosa no debe contaminar el registro de apariciones.
        registro.verificar(id, habitacion);
        Criatura resultado = crearCriatura(id, habitacion);
        if (resultado == null || !id.equals(resultado.id())
                || !habitacion.equals(resultado.habitacion())) {
            throw new IllegalStateException("El producto no respeta el ID o la ubicación solicitados");
        }
        registro.registrar(resultado);
        return resultado;
    }
    protected abstract Criatura crearCriatura(String id, String habitacion);
}
