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

    /**
     * Conserva una instantanea del valor proporcionado.
     *
     * @param state estado que se desea preservar
     */
    public Memento(String state) {
        this.state = state;
    }

    protected String getSavedState() {
        return state;
    }

   

    /**
     * Guarda el estado encapsulado en un archivo binario.
     * Esta demostracion informa errores por consola; no los propaga.
     *
     * @param filePath ruta del archivo de salida
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
     * Lee un estado previamente serializado y reconstruye su instantanea.
     * El resultado debe comprobarse antes de llamar a {@code restore}.
     *
     * @param filePath ruta del archivo que contiene el estado
     * @return memento reconstruido o null si la lectura fallo
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
