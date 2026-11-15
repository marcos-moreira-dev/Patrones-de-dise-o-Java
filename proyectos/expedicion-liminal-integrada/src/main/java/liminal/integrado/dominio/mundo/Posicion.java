package liminal.integrado.dominio.mundo;

/** Coordenada inmutable: igualdad por valor, sin identidad de entidad. */
public record Posicion(int x, int y) {
    public Posicion {
        if (x < 0 || y < 0) {
            throw new IllegalArgumentException("Las coordenadas no pueden ser negativas");
        }
    }
}

