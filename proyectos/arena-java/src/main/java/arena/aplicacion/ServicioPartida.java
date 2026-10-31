package arena.aplicacion;

import arena.dominio.Accion;
import arena.dominio.MotorCombate;
import arena.dominio.Partida;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * Servicio de aplicación: admite órdenes, verifica precondiciones y publica
 * eventos. La física simplificada del combate permanece en MotorCombate.
 */
public final class ServicioPartida {
    private final Partida partida;
    private final MotorCombate motor;
    private final List<ReglasAccion.Regla> reglas;
    private final List<ObservadorPartida> observadores = new ArrayList<>();

    public ServicioPartida(Partida partida, MotorCombate motor) {
        this(partida, motor, ReglasAccion.porDefecto());
    }

    public ServicioPartida(Partida partida, MotorCombate motor, List<ReglasAccion.Regla> reglas) {
        this.partida = Objects.requireNonNull(partida);
        this.motor = Objects.requireNonNull(motor);
        this.reglas = List.copyOf(reglas);
    }

    public Partida partida() {
        return partida;
    }

    /** @param observador receptor de mensajes para cada turno válido */
    public void suscribir(ObservadorPartida observador) {
        observadores.add(Objects.requireNonNull(observador));
    }

    /**
     * Ejecuta una acción si todas las reglas la permiten.
     * Un rechazo normal no altera el estado de la partida.
     *
     * @param accion intención del usuario
     * @return {@link ResultadoAccion.Ejecutada} o {@link ResultadoAccion.Rechazada}
     */
    public ResultadoAccion ejecutar(Accion accion) {
        Objects.requireNonNull(accion);
        for (ReglasAccion.Regla regla : reglas) {
            Optional<String> rechazo = regla.revisar(partida, accion);
            if (rechazo.isPresent()) {
                return new ResultadoAccion.Rechazada(rechazo.get());
            }
        }
        List<String> eventos = motor.resolver(partida, accion);
        for (String evento : eventos) {
            for (ObservadorPartida observador : observadores) {
                observador.alOcurrir(evento);
            }
        }
        return new ResultadoAccion.Ejecutada(eventos);
    }
}
