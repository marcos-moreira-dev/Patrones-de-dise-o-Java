package liminal.creacionales.factorymethod;

import java.io.PrintStream;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;



abstract class GeneradorCriatura {
    public final Criatura aparecer(String id, String habitacion, RegistroApariciones registro) {
        Objects.requireNonNull(registro);
        registro.verificar(id, habitacion);
        Criatura resultado = crearCriatura(id, habitacion);
        registro.registrar(resultado);
        return resultado;
    }
    protected abstract Criatura crearCriatura(String id, String habitacion);
}
