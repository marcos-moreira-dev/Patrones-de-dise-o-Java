

/**
 * CLASE: Originator
 *
 * ROL EN EL PATRÓN:
 *  - Crea objetos Memento que capturan su estado interno.
 *  - Restaura su estado desde un Memento.
 *
 * PROPÓSITO:
 *  - Representar el objeto cuyo estado queremos guardar y restaurar.
 *
 * CRITERIOS DE USO:
 *  ✅ Cuando el objeto cambia con frecuencia y se requiere volver atrás.
 *  ✅ Cuando se desea separar el control de versiones del objeto principal.
 *
 * EJEMPLO EN BACKEND:
 *  - Editor de texto que guarda y restaura versiones.
 *
 * EJEMPLO EN PIZZERÍA ONLINE:
 *  - Configuración del menú o promociones guardadas y restauradas.
 *
 * USO EN CLASE CLIENTE:
 *  Originator editor = new Originator();
 *  editor.setState("Menú inicial");
 *  Memento backup = editor.createMemento();
 *  editor.setState("Menú cambiado");
 *  editor.restore(backup);
 *
 * SALIDA ESPERADA:
 *  Estado actual: Menú inicial
 *  Estado actual: Menú cambiado
 *  Estado restaurado: Menú inicial
 */
public class Originator {
    private String state;

    public void setState(String state) {
        this.state = state;
        System.out.println("Estado actual: " + state);
    }

    public Memento createMemento() {
        return new Memento(state);
    }

    public void restore(Memento memento) {
        this.state = memento.getSavedState();
        System.out.println("Estado restaurado: " + state);
    }
}
