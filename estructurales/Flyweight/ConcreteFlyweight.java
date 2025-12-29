package estructurales.Flyweight;

/**
 * CLASE CONCRETA: ConcreteFlyweight
 *
 * ROL EN EL PATRÓN:
 *  - Implementa la interfaz Flyweight.
 *  - Contiene el estado intrínseco que puede ser compartido.
 *
 * PROPÓSITO:
 *  Representar un objeto compartido entre múltiples clientes.
 *
 * CRITERIOS DE USO:
 *  ✅ Cuando los objetos son costosos de crear y se repiten con frecuencia.
 *  ✅ Cuando solo cambia un contexto externo (por ejemplo, mesa o cliente).
 *
 * EJEMPLO EN BACKEND:
 *  - Conexiones reutilizables en un pool.
 *  - Plantillas de correos compartidas entre usuarios.
 *
 * EJEMPLO EN PIZZERÍA ONLINE:
 *  - La receta base de cada tipo de pizza (estado intrínseco) se reutiliza
 *    y solo cambia la mesa o el cliente (estado extrínseco).
 *
 * USO EN CLASE CLIENTE:
 *  Flyweight pizza = new ConcreteFlyweight("Hawaiana");
 *  pizza.servir("Mesa 3");
 *
 * SALIDA ESPERADA:
 *  Preparando pizza tipo Hawaiana para Mesa 3
 */
public class ConcreteFlyweight implements Flyweight {

    private final String tipo; // Estado intrínseco

    public ConcreteFlyweight(String tipo) {
        this.tipo = tipo;
    }

    @Override
    public void servir(String mesa) {
        System.out.println("Preparando pizza tipo " + tipo + " para " + mesa);
    }
}
