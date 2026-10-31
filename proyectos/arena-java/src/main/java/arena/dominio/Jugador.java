package arena.dominio;

/**
 * Entidad del dominio: conserva los recursos del personaje y sus invariantes.
 * No conoce la consola, las reglas del terreno ni el orden de los turnos.
 */
public final class Jugador {
    private final String nombre;
    private final int vidaMaxima;
    private final int energiaMaxima;
    private int vida;
    private int energia;

    /**
     * Crea un personaje con sus recursos completos.
     *
     * @param nombre nombre no vacío
     * @param vidaMaxima cantidad de vida estrictamente positiva
     * @param energiaMaxima energía inicial no negativa
     * @throws IllegalArgumentException si los parámetros no son válidos
     */
    public Jugador(String nombre, int vidaMaxima, int energiaMaxima) {
        if (nombre == null || nombre.isBlank() || vidaMaxima <= 0 || energiaMaxima < 0) {
            throw new IllegalArgumentException("Nombre o recursos iniciales invalidos");
        }
        this.nombre = nombre;
        this.vidaMaxima = vidaMaxima;
        this.energiaMaxima = energiaMaxima;
        this.vida = vidaMaxima;
        this.energia = energiaMaxima;
    }

    public String nombre() {
        return nombre;
    }

    public int vida() {
        return vida;
    }

    public int vidaMaxima() {
        return vidaMaxima;
    }

    public int energia() {
        return energia;
    }

    public int energiaMaxima() {
        return energiaMaxima;
    }

    public boolean estaVivo() {
        return vida > 0;
    }

    /**
     * Aplica un daño no negativo sin permitir que la vida baje de cero.
     *
     * @param puntos daño recibido
     * @throws IllegalArgumentException si el daño es negativo
     */
    public void recibirDanio(int puntos) {
        if (puntos < 0) {
            throw new IllegalArgumentException("El danio no puede ser negativo");
        }
        vida = Math.max(0, vida - puntos);
    }

    /**
     * Comprueba y consume energía de manera indivisible.
     *
     * @param puntos energía solicitada, no negativa
     * @return {@code true} si se consumió; {@code false} si era insuficiente
     * @throws IllegalArgumentException si la cantidad es negativa
     */
    public boolean consumirEnergia(int puntos) {
        if (puntos < 0) {
            throw new IllegalArgumentException("La energia solicitada no puede ser negativa");
        }
        if (energia < puntos) {
            return false;
        }
        energia -= puntos;
        return true;
    }

    /**
     * Recupera energía sin superar el máximo configurado.
     *
     * @param puntos cantidad no negativa
     * @throws IllegalArgumentException si la cantidad es negativa
     */
    public void recuperarEnergia(int puntos) {
        if (puntos < 0) {
            throw new IllegalArgumentException("La recuperacion no puede ser negativa");
        }
        energia = (int) Math.min(energiaMaxima, (long) energia + puntos);
    }
}
