package arena.dominio;

import arena.dominio.ataques.Ataque;
import arena.dominio.ataques.AtaqueBasico;
import arena.dominio.ataques.AtaquePotenciado;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Servicio de dominio: resuelve un turno entre entidades aplicando las reglas.
 * No representa a una persona ni imprime mensajes: entrega eventos descriptivos.
 */
public final class MotorCombate {
    public static final int COSTE_ESPECIAL = 3;
    private static final int BONIFICACION_ESPECIAL = 3;
    private final FuenteAzar azar;

    public MotorCombate(FuenteAzar azar) {
        this.azar = Objects.requireNonNull(azar);
    }

    /**
     * Resuelve exactamente un turno permitido por las reglas del caso de uso.
     * El héroe actúa primero; si derrota al rival, no hay contraataque.
     *
     * @param partida partida activa
     * @param accion acción validada previamente
     * @return acontecimientos del turno, en orden
     */
    public List<String> resolver(Partida partida, Accion accion) {
        Objects.requireNonNull(partida);
        Objects.requireNonNull(accion);
        if (!partida.activa()) {
            throw new IllegalStateException("El combate ya termino");
        }

        List<String> eventos = new ArrayList<>();
        Jugador heroe = partida.heroe();
        Jugador rival = partida.rival();

        switch (accion) {
            case ATACAR -> atacar(heroe, rival, false, eventos);
            case ESPECIAL -> {
                if (!heroe.consumirEnergia(COSTE_ESPECIAL)) {
                    throw new IllegalStateException("El caso de uso debe comprobar la energia");
                }
                atacar(heroe, rival, true, eventos);
            }
            case DEFENDER -> {
                heroe.recuperarEnergia(1);
                eventos.add(heroe.nombre() + " se protege y recupera 1 de energia.");
            }
            case SALTAR -> eventos.add(heroe.nombre() + " salta para esquivar peligros del terreno.");
            case HUIR -> {
                partida.finalizar(Partida.Estado.HUIDA);
                eventos.add(heroe.nombre() + " escapa del bosque. Fin del encuentro.");
                return List.copyOf(eventos);
            }
        }

        if (!rival.estaVivo()) {
            partida.finalizar(Partida.Estado.VICTORIA);
            eventos.add("Victoria: " + rival.nombre() + " ha sido derrotado.");
            return List.copyOf(eventos);
        }

        int terreno = partida.escenario().danioTerreno(partida.turno(), accion);
        if (terreno > 0) {
            heroe.recibirDanio(terreno);
            eventos.add("Las raices causan " + terreno + " de danio a " + heroe.nombre() + ".");
        } else if (accion == Accion.SALTAR) {
            eventos.add("El salto evita cualquier golpe del terreno este turno.");
        }

        if (!heroe.estaVivo()) {
            partida.finalizar(Partida.Estado.DERROTA);
            eventos.add("Derrota: el terreno ha vencido a " + heroe.nombre() + ".");
            return List.copyOf(eventos);
        }

        int respuesta = 3 + azar.siguiente(3);
        if (accion == Accion.DEFENDER) {
            respuesta = Math.max(0, respuesta - 2);
        }
        heroe.recibirDanio(respuesta);
        eventos.add(rival.nombre() + " responde y causa " + respuesta + " de danio.");

        if (!heroe.estaVivo()) {
            partida.finalizar(Partida.Estado.DERROTA);
            eventos.add("Derrota: " + heroe.nombre() + " se ha quedado sin vida.");
        } else {
            partida.avanzarTurno();
        }
        return List.copyOf(eventos);
    }

    private void atacar(Jugador atacante, Jugador objetivo, boolean especial, List<String> eventos) {
        Ataque golpe = new AtaqueBasico(4 + azar.siguiente(3));
        if (especial) {
            // Decorator: añadimos un efecto sin modificar AtaqueBasico.
            golpe = new AtaquePotenciado(golpe, BONIFICACION_ESPECIAL);
        }
        objetivo.recibirDanio(golpe.danio());
        eventos.add(atacante.nombre() + " usa " + golpe.descripcion()
                + " e inflige " + golpe.danio() + " de danio.");
    }
}
