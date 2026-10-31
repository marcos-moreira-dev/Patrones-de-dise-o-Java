package arena.dominio.ataques;

/** Contrato de cálculo de daño que puede envolverse con Decorator. */
public interface Ataque {
    int danio();

    String descripcion();
}
