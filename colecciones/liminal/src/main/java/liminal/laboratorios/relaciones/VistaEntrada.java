package liminal.laboratorios.relaciones;


/** Vista de lectura: retenerla no equivale a ser propietario de una parte compuesta. */
interface VistaEntrada {
    String id();
    String texto();
    String idRegistro();
    boolean activa();
}
