package arena.dominio;

/**
 * Puerto del dominio para números aleatorios; permite probar sin azar real.
 * La infraestructura adapta {@link java.util.Random} a este contrato.
 */
@FunctionalInterface
public interface FuenteAzar {
    /**
     * @param limiteExclusivo cota superior positiva y no incluida
     * @return entero entre cero y el límite excluido
     */
    int siguiente(int limiteExclusivo);
}
