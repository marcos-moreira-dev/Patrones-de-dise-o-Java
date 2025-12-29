
import java.util.HashMap;
import java.util.Map;

/**
 * CLASE: FlyweightFactory
 *
 * ROL EN EL PATRÓN:
 *  - Crea y administra los objetos Flyweight.
 *  - Devuelve instancias existentes si ya fueron creadas (cache interna).
 *
 * PROPÓSITO:
 *  Evitar la creación redundante de objetos similares.
 *
 * CRITERIOS DE USO:
 *  ✅ Cuando el número de instancias similares es muy alto.
 *  ✅ Cuando los objetos pueden compartirse entre contextos.
 *
 * EJEMPLO EN BACKEND:
 *  - Cache de objetos DAO, sesiones, o plantillas HTML.
 *
 * EJEMPLO EN PIZZERÍA ONLINE:
 *  - La fábrica devuelve una pizza base existente si ya se creó anteriormente.
 *
 * USO EN CLASE CLIENTE:
 *  FlyweightFactory factory = new FlyweightFactory();
 *  Flyweight pizza = factory.getFlyweight("Margarita");
 *  pizza.servir("Mesa 5");
 *
 * SALIDA ESPERADA:
 *  Reutilizando objeto Flyweight existente para tipo: Margarita
 *  Preparando pizza tipo Margarita para Mesa 5
 */
public class FlyweightFactory {
    private final Map<String, Flyweight> flyweights = new HashMap<>();

    public Flyweight getFlyweight(String tipo) {
        Flyweight flyweight = flyweights.get(tipo);
        if (flyweight == null) {
            flyweight = new ConcreteFlyweight(tipo);
            flyweights.put(tipo, flyweight);
            System.out.println("Creando nuevo objeto Flyweight para tipo: " + tipo);
        } else {
            System.out.println("Reutilizando objeto Flyweight existente para tipo: " + tipo);
        }
        return flyweight;
    }
}
