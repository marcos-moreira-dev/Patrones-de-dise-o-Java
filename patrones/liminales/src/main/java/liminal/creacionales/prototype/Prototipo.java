package liminal.creacionales.prototype;

// T preserva el tipo de la copia: devolver Object obligaría al cliente a convertirlo.
interface Prototipo<T> {
    T copiarConId(String nuevoId);
}
