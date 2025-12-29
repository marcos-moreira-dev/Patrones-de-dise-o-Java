

import java.util.Stack;

/**
 * CLASE: Caretaker
 *
 * ROL EN EL PATRÓN:
 *  - Administra la lista (historial) de Mementos.
 *  - No modifica ni conoce su contenido.
 *
 * PROPÓSITO:
 *  - Controlar el ciclo de vida de los estados guardados.
 *
 * CRITERIOS DE USO:
 *  ✅ Cuando se requiere un mecanismo centralizado de undo/redo.
 *  ✅ Cuando varias versiones de estado deben mantenerse accesibles.
 *
 * EJEMPLO EN BACKEND:
 *  - Controlador de versiones de configuración.
 *
 * EJEMPLO EN PIZZERÍA ONLINE:
 *  - Registro de cambios en el sistema de menú o precios.
 *
 * USO EN CLASE CLIENTE:
 *  Caretaker history = new Caretaker();
 *  history.save(originator.createMemento());
 *  originator.restore(history.undo());
 *
 * SALIDA ESPERADA:
 *  Estado restaurado: Menú inicial
 */
public class Caretaker {
    private final Stack<Memento> history = new Stack<>();

    public void save(Memento memento) {
        history.push(memento);
    }

    public Memento undo() {
        if (!history.isEmpty()) {
            return history.pop();
        }
        System.out.println("No hay estados previos para restaurar.");
        return null;
    }
}
