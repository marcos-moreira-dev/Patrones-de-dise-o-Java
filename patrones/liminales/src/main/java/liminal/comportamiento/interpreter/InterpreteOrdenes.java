package liminal.comportamiento.interpreter;

import java.util.Objects;

final class InterpreteOrdenes {
    Expresion analizar(String fuente) {
        Objects.requireNonNull(fuente);
        String[] partes = fuente.trim().split("\\s+");
        if (partes.length != 2 || partes[1].isBlank()) {
            throw new IllegalArgumentException("Sintaxis: ACCION ID");
        }
        return switch (partes[0]) {
            case "ABRIR" -> new Abrir(partes[1]);
            case "ENCENDER" -> new Encender(partes[1]);
            default -> throw new IllegalArgumentException("Operación desconocida");
        };
    }
}
