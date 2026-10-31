package miniso.dominio;

/** Estados simplificados de un proceso; no representan hilos reales del anfitrión. */
public enum EstadoProceso {
    NUEVO, LISTO, EJECUTANDO, BLOQUEADO, TERMINADO
}
