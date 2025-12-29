package comportamiento.Memento;
import java.io.*;
/**
 * PATRÓN: Memento
 *
 * CLASE: Memento
 *
 * ROL EN EL PATRÓN:
 *  - Guarda el estado interno del objeto Originator.
 *  - No permite acceso directo al contenido desde el exterior (encapsulamiento).
 *
 * PROPÓSITO:
 *  - Permitir restaurar el estado anterior de un objeto sin violar el principio de encapsulación.
 *
 * CRITERIOS DE USO:
 *  ✅ Cuando se necesita implementar “deshacer” (undo/rollback).
 *  ✅ Cuando se requiere guardar snapshots del estado de un objeto.
 *
 * ⚠️ Evitarlo cuando:
 *  - Guardar el estado completo consume demasiada memoria.
 *
 * VENTAJAS:
 *  + Permite revertir cambios fácilmente.
 *  + Mantiene la encapsulación (el Originator controla el acceso).
 *
 * DESVENTAJAS:
 *  - Puede generar gran consumo de memoria si hay muchos estados.
 *
 * EJEMPLO EN BACKEND:
 *  - Restaurar configuración previa de usuario o rollback de transacciones.
 *
 * EJEMPLO EN PIZZERÍA ONLINE:
 *  - Permitir que un administrador deshaga cambios en el menú o en precios.
 *
 * USO EN CLASE CLIENTE:
 *  Originator editor = new Originator();
 *  Caretaker history = new Caretaker();
 *  editor.setState("Menú inicial");
 *  history.save(editor.createMemento());
 *  editor.setState("Menú modificado");
 *  editor.restore(history.undo());
 *
 * SALIDA ESPERADA (en Originator):
 *  Estado actual: Menú inicial
 *  Estado actual: Menú modificado
 *  Estado restaurado: Menú inicial
 */
public class Memento implements Serializable{
    private final String state;
    private static final long serialVersionUID = 1L;
    public Memento(String state) {
        this.state = state;
    }

    protected String getSavedState() {
        return state;
    }

   

    /**
     * Método que permite serializar el estado del Memento
     * a un archivo binario (útil si el estado ocupa mucha memoria).
     * 
     *  Memento backup = editor.createMemento();
        backup.saveToFile("backup_menu.ser");
     */
    public void saveToFile(String filePath) {

        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath))) {
            oos.writeObject(this.state);
            System.out.println("Memento serializado correctamente en: " + filePath);
        } catch (IOException e) {
            System.err.println("Error al serializar el Memento: " + e.getMessage());
        }
    }

    /**
     * Método estático que permite cargar un estado previamente serializado
     * y crear un nuevo Memento a partir de él.
     * 
     * Memento loaded = Memento.loadFromFile("backup_menu.ser");
       editor.restore(loaded);
     */
    public static Memento loadFromFile(String filePath) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filePath))) {
            String restoredState = (String) ois.readObject();
            System.out.println("Memento deserializado desde: " + filePath);
            return new Memento(restoredState);
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error al deserializar el Memento: " + e.getMessage());
            return null;
        }
    }
}
