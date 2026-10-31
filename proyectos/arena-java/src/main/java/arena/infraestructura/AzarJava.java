package arena.infraestructura;

import arena.dominio.FuenteAzar;
import java.util.Objects;
import java.util.Random;

/**
 * Adapter: traduce la API estándar Random al contrato del dominio.
 * Al sustituir este objeto por una fuente fija, las pruebas son deterministas.
 */
public final class AzarJava implements FuenteAzar {
    private final Random random;

    public AzarJava() {
        this(new Random());
    }

    public AzarJava(Random random) {
        this.random = Objects.requireNonNull(random);
    }

    @Override
    public int siguiente(int limiteExclusivo) {
        if (limiteExclusivo <= 0) {
            throw new IllegalArgumentException("El limite debe ser positivo");
        }
        return random.nextInt(limiteExclusivo);
    }
}
