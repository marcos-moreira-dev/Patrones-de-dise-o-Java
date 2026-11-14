package liminal.comportamiento.chainofresponsibility;

import java.io.PrintStream;
import java.util.Objects;
import java.util.Set;


record SolicitudCruce(Set<String> habitaciones, Set<Ruta> salidas,
                       Set<Ruta> puertas, Set<Ruta> abiertas,
                       String origen, String destino) {
    SolicitudCruce {
        habitaciones = Set.copyOf(habitaciones);
        salidas = Set.copyOf(salidas);
        puertas = Set.copyOf(puertas);
        abiertas = Set.copyOf(abiertas);
        Objects.requireNonNull(origen);
        Objects.requireNonNull(destino);
        if (!salidas.containsAll(puertas) || !puertas.containsAll(abiertas)) {
            throw new IllegalArgumentException("Puerta o estado ajeno a las salidas");
        }
        for (Ruta ruta : salidas) {
            if (!habitaciones.contains(ruta.origen()) || !habitaciones.contains(ruta.destino())) {
                throw new IllegalArgumentException("La salida está fuera del mapa");
            }
        }
    }
}
