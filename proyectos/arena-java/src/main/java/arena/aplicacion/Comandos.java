package arena.aplicacion;

import arena.dominio.Accion;

/** Variantes del patrón Command, sin cinco clases de relleno por separado. */
public final class Comandos {
    private Comandos() {}

    public record Atacar() implements Comando {
        @Override
        public ResultadoAccion ejecutar(ServicioPartida servicio) {
            return servicio.ejecutar(Accion.ATACAR);
        }
    }

    public record Defender() implements Comando {
        @Override
        public ResultadoAccion ejecutar(ServicioPartida servicio) {
            return servicio.ejecutar(Accion.DEFENDER);
        }
    }

    public record Especial() implements Comando {
        @Override
        public ResultadoAccion ejecutar(ServicioPartida servicio) {
            return servicio.ejecutar(Accion.ESPECIAL);
        }
    }

    public record Saltar() implements Comando {
        @Override
        public ResultadoAccion ejecutar(ServicioPartida servicio) {
            return servicio.ejecutar(Accion.SALTAR);
        }
    }

    public record Huir() implements Comando {
        @Override
        public ResultadoAccion ejecutar(ServicioPartida servicio) {
            return servicio.ejecutar(Accion.HUIR);
        }
    }
}
