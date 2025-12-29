

import creacionales.AbstractFactory.*;
import creacionales.Builder.*;
import creacionales.FactoryMethod.*;
import creacionales.Prototype.*;
import creacionales.SingletonInstance.*;

public class ProbarCreacionales {
    public ProbarCreacionales() {
        System.out.println("=== ProbarCreacionales ===");
    }

    // ----------- ABSTRACT FACTORY -----------
    public void probarAbstractFactory() {
        System.out.println("\n[Abstract Factory]");
        PersistenceFactory sqlFactory = new SQLFactory();
        PersistenceFactory noSqlFactory = new NoSQLFactory();

        Repository sqlRepo = sqlFactory.createRepository();
        Mapper sqlMapper = sqlFactory.createMapper();
        sqlRepo.save();
        sqlMapper.mapData();

        Repository noSqlRepo = noSqlFactory.createRepository();
        Mapper noSqlMapper = noSqlFactory.createMapper();
        noSqlRepo.save();
        noSqlMapper.mapData();
    }

    // ----------- BUILDER -----------
    public void probarBuilder() {
        System.out.println("\n[Builder]");
        Response response = new ResponseBuilder()
                .statusCode(200)
                .message("Operación exitosa")
                .body("{data:'OK'}")
                .build();
        System.out.println(response);
    }

    // ----------- FACTORY METHOD -----------
    public void probarFactoryMethod() {
        System.out.println("\n[Factory Method]");
        ServiceFactory userFactory = new UserServiceFactory();
        Service userService = userFactory.createService();
        userService.execute();

        ServiceFactory paymentFactory = new PaymentServiceFactory();
        Service paymentService = paymentFactory.createService();
        paymentService.execute();
    }

    // ----------- PROTOTYPE -----------
    public void probarPrototype() {
        System.out.println("\n[Prototype]");
        Configuration original = new Configuration("localhost", "8080");
        Configuration copia = original.clone();

        System.out.println("Original: " + original);
        System.out.println("Copia: " + copia);
        System.out.println("¿Son iguales? " + (original == copia));
    }

    // ----------- SINGLETON -----------
    public void probarSingleton() {
        System.out.println("\n[Singleton]");
        SingletonInstance s1 = SingletonInstance.getInstance();
        SingletonInstance s2 = SingletonInstance.getInstance();
        s1.doSomethingUnique();
        System.out.println("¿Ambas instancias son iguales? " + (s1 == s2));
    }
}
