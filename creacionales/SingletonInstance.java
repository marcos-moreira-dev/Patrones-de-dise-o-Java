package GramaticaPatrones.creacionales;

public class SingletonInstance {
    private static SingletonInstance instancia;

    private SingletonInstance() {
        // Constructor privado para evitar instanciación externa

    }

    public static SingletonInstance getInstancia() {
        if (instancia == null){
            instancia = new SingletonInstance();
        }
        return instancia;
    }

    public void ejecutarFuncionUnica () {
        System.out.println("Instancia única del Singleton creada.");
    }

}
