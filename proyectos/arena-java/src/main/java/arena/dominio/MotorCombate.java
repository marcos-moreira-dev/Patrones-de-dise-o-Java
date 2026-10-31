package arena.dominio;

import arena.dominio.ataques.Ataque;
import arena.dominio.ataques.AtaqueBasico;
import arena.dominio.ataques.AtaquePotenciado;
import arena.dominio.EventoCombate.Tipo;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Servicio de dominio: resuelve un turno entre entidades aplicando las reglas.
 * No representa a una persona ni imprime mensajes: entrega eventos descriptivos.
 */
public final class MotorCombate {
    private static final int BONIFICACION_ESPECIAL = 3;
    private final FuenteAzar azar;

    public MotorCombate(FuenteAzar azar) {
        this.azar = Objects.requireNonNull(azar);
    }

    /**
     * Resuelve exactamente un turno permitido por las reglas del dominio.
     * Verifica también las restricciones para que una llamada directa no las
     * eluda. Una llamada inválida de código es un error de contrato.
     * El héroe actúa primero; si derrota al rival, no hay contraataque.
     *
     * @param partida partida activa
     * @param accion acción solicitada
     * @return acontecimientos del turno, en orden
     * @throws IllegalStateException si la acción infringe las reglas
     */
    public List<EventoCombate> resolver(Partida partida, Accion accion) {
        Objects.requireNonNull(partida);
        Objects.requireNonNull(accion);
        ReglasAccion.impedimento(partida, accion).ifPresent(
                motivo -> { throw new IllegalStateException("Accion invalida: " + motivo); });

        List<EventoCombate> eventos = new ArrayList<>();
        Jugador heroe = partida.heroe();
        Jugador rival = partida.rival();

        switch (accion) {
            case ATACAR -> atacar(heroe, rival, false, eventos);
            case ESPECIAL -> {
                if (!heroe.consumirEnergia(accion.costeEnergia())) {
                    throw new IllegalStateException("La energia cambio despues de validarse");
                }
                atacar(heroe, rival, true, eventos);
            }
            case DEFENDER -> {
                heroe.recuperarEnergia(1);
                agregar(eventos, Tipo.DEFENSA,
                        heroe.nombre() + " se protege y recupera 1 de energia.");
            }
            case SALTAR -> agregar(eventos, Tipo.SALTO,
                    heroe.nombre() + " salta para esquivar peligros del terreno.");
            case HUIR -> {
                partida.finalizar(Partida.Estado.HUIDA);
                agregar(eventos, Tipo.HUIDA,
                        heroe.nombre() + " escapa del bosque. Fin del encuentro.");
                return List.copyOf(eventos);
            }
        }

        if (!rival.estaVivo()) {
            partida.finalizar(Partida.Estado.VICTORIA);
            agregar(eventos, Tipo.VICTORIA, "Victoria: " + rival.nombre() + " ha sido derrotado.");
            return List.copyOf(eventos);
        }

        int terreno = partida.escenario().danioTerreno(partida.turno(), accion);
        if (terreno > 0) {
            heroe.recibirDanio(terreno);
            agregar(eventos, Tipo.TERRENO,
                    "Las raices causan " + terreno + " de danio a " + heroe.nombre() + ".");
        } else if (accion == Accion.SALTAR) {
            agregar(eventos, Tipo.TERRENO,
                    "El salto evita cualquier golpe del terreno este turno.");
        }

        if (!heroe.estaVivo()) {
            partida.finalizar(Partida.Estado.DERROTA);
            agregar(eventos, Tipo.DERROTA,
                    "Derrota: el terreno ha vencido a " + heroe.nombre() + ".");
            return List.copyOf(eventos);
        }

        int respuesta = 3 + azar.siguiente(3);
        if (accion == Accion.DEFENDER) {
            respuesta = Math.max(0, respuesta - 2);
        }
        heroe.recibirDanio(respuesta);
        agregar(eventos, Tipo.RESPUESTA,
                rival.nombre() + " responde y causa " + respuesta + " de danio.");

        if (!heroe.estaVivo()) {
            partida.finalizar(Partida.Estado.DERROTA);
            agregar(eventos, Tipo.DERROTA,
                    "Derrota: " + heroe.nombre() + " se ha quedado sin vida.");
        } else {
            partida.avanzarTurno();
        }
        return List.copyOf(eventos);
    }

    private void atacar(Jugador atacante, Jugador objetivo, boolean especial,
                        List<EventoCombate> eventos) {
        Ataque golpe = new AtaqueBasico(4 + azar.siguiente(3));
        if (especial) {
            // Decorator: añadimos un efecto sin modificar AtaqueBasico.
            golpe = new AtaquePotenciado(golpe, BONIFICACION_ESPECIAL);
        }
        objetivo.recibirDanio(golpe.danio());
        agregar(eventos, Tipo.ATAQUE, atacante.nombre() + " usa " + golpe.descripcion()
                + " e inflige " + golpe.danio() + " de danio.");
    }

    private void agregar(List<EventoCombate> eventos, Tipo tipo, String mensaje) {
        eventos.add(new EventoCombate(tipo, mensaje));
    }
}
