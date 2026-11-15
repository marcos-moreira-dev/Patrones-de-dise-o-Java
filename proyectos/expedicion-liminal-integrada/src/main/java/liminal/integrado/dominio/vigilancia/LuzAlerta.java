package liminal.integrado.dominio.vigilancia;

/** Receptor independiente: no conoce SensorRuido ni CentroVigilancia. */
public final class LuzAlerta {
    private boolean encendida;

    public void encender() { encendida = true; }
    public boolean encendida() { return encendida; }
}
