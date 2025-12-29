package estructurales.Adapter;

/**
 * CLASE EXISTENTE: Adaptee
 *
 * ROL EN EL PATRÓN:
 *  - Clase existente con una interfaz incompatible.
 *  - Contiene la funcionalidad que se desea reutilizar.
 *
 * PROPÓSITO:
 *  Representar un sistema o módulo que ya existe, pero no cumple
 *  con la interfaz que el cliente necesita.
 *
 * EJEMPLO EN BACKEND:
 *  - API de terceros que tiene métodos con nombres o parámetros diferentes.
 *
 * EJEMPLO EN PIZZERÍA ONLINE:
 *  - Proveedor externo de delivery que usa un método diferente para recibir pedidos.
 *
 * USO EN CLASE CLIENTE (a través del adaptador):
 *  new Adapter(new Adaptee()).request();
 *
 * SALIDA ESPERADA:
 *  [Proveedor Externo] Recibiendo solicitud en formato incompatible.
 */
public class Adaptee {
    public void specificRequest() {
        System.out.println("Solicitud específica del proveedor externo.");
    }
}