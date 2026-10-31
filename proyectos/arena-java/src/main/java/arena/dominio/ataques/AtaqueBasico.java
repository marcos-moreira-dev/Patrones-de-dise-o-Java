package arena.dominio.ataques;

/** Ataque sin modificadores. */
public record AtaqueBasico(int danio) implements Ataque {
    public AtaqueBasico {
        if (danio < 0) {
            throw new IllegalArgumentException("El ataque no puede causar danio negativo");
        }
    }

    @Override
    public String descripcion() {
        return "golpe normal";
    }
}
