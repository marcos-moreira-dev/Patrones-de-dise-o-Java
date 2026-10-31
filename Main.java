/**
 * Clase Main que inicializa la aplicación.
 */
public class Main {
    /**
     * Inicia el laboratorio y delega la eleccion de ejemplos al menu de texto.
     *
     * @param args argumentos de consola no utilizados por esta demo
     */
    public static void main(String[] args) {
        System.out.println("=== DEMO DE PATRONES DE DISEÑO ===");
        App app = new App();
        app.mostrarMenu();
    }
}
