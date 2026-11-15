package liminal.creacionales.prototype.variantes.registro;

/** Contrato de prototipo: fabricar una nueva instancia con ID de entidad. */
interface PrototipoRegistrable {
    HabitacionGenerada crearConId(String nuevoId);
}
