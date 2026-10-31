package miniso.estructuras;

import java.util.NoSuchElementException;
import java.util.Objects;

/**
 * Montículo binario máximo con desempate por orden de llegada a la cola.
 * Insertar/extraer: O(log n); consultar cantidad: O(1); memoria: O(n).
 */
public final class MonticuloPrioridad<E> {
    private record Entrada<E>(E valor, int prioridad, long orden) {}

    private Object[] nodos = new Object[4];
    private int cantidad;
    private long siguienteOrden;

    public int cantidad() { return cantidad; }
    public boolean estaVacio() { return cantidad == 0; }

    @SuppressWarnings("unchecked")
    private Entrada<E> nodo(int indice) { return (Entrada<E>) nodos[indice]; }

    private static boolean precede(Entrada<?> a, Entrada<?> b) {
        return a.prioridad() > b.prioridad()
                || (a.prioridad() == b.prioridad() && a.orden() < b.orden());
    }

    /** Prioridad de 0 a 9; 9 se atiende primero. */
    public void insertar(E valor, int prioridad) {
        Objects.requireNonNull(valor, "valor");
        if (prioridad < 0 || prioridad > 9) {
            throw new IllegalArgumentException("Prioridad fuera de 0..9");
        }
        if (cantidad == nodos.length) {
            Object[] nuevos = new Object[nodos.length * 2];
            System.arraycopy(nodos, 0, nuevos, 0, cantidad);
            nodos = nuevos;
        }
        int i = cantidad++;
        Entrada<E> entrante = new Entrada<>(valor, prioridad, siguienteOrden++);
        while (i > 0) {
            int padre = (i - 1) / 2;
            if (!precede(entrante, nodo(padre))) break;
            nodos[i] = nodos[padre];
            i = padre;
        }
        nodos[i] = entrante;
    }

    /** Devuelve el más prioritario; con empate, el que entró primero. */
    public E extraer() {
        if (estaVacio()) throw new NoSuchElementException("Monticulo vacio");
        Entrada<E> mejor = nodo(0);
        Entrada<E> ultimo = nodo(--cantidad);
        nodos[cantidad] = null;
        if (cantidad > 0) {
            int i = 0;
            while (2 * i + 1 < cantidad) {
                int hijo = 2 * i + 1;
                if (hijo + 1 < cantidad && precede(nodo(hijo + 1), nodo(hijo))) hijo++;
                if (!precede(nodo(hijo), ultimo)) break;
                nodos[i] = nodos[hijo];
                i = hijo;
            }
            nodos[i] = ultimo;
        }
        return mejor.valor();
    }
}
