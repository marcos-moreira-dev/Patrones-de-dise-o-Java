package miniso.estructuras;

import java.util.NoSuchElementException;
import java.util.Objects;

/**
 * FIFO con arreglo circular y crecimiento por duplicación, escrita desde cero.
 * Encolar: O(1) amortizado; desencolar: O(1); memoria: O(n).
 */
public final class ColaCircular<E> {
    private Object[] elementos = new Object[4];
    private int primero;
    private int cantidad;

    public int cantidad() { return cantidad; }
    public boolean estaVacia() { return cantidad == 0; }

    /** Inserta al final; rechaza null para distinguirlo de la cola vacía. */
    public void encolar(E elemento) {
        Objects.requireNonNull(elemento, "elemento");
        if (cantidad == elementos.length) {
            Object[] nuevos = new Object[elementos.length * 2];
            for (int i = 0; i < cantidad; i++) {
                nuevos[i] = elementos[(primero + i) % elementos.length];
            }
            elementos = nuevos;
            primero = 0;
        }
        elementos[(primero + cantidad) % elementos.length] = elemento;
        cantidad++;
    }

    /** Retira el más antiguo; O(1), incluida la operación de giro. */
    @SuppressWarnings("unchecked")
    public E desencolar() {
        if (estaVacia()) {
            throw new NoSuchElementException("Cola vacia");
        }
        E valor = (E) elementos[primero];
        elementos[primero] = null;
        primero = (primero + 1) % elementos.length;
        cantidad--;
        return valor;
    }
}
