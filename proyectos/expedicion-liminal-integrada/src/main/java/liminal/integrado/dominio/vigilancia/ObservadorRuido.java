package liminal.integrado.dominio.vigilancia;

/** Observer: los destinatarios conocen eventos, no las clases del sensor. */
public interface ObservadorRuido {
    String id();
    void alDetectar(EventoRuido evento);
}
