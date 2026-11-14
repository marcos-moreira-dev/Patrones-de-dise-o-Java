package liminal.comportamiento.visitor;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;



interface Elemento {
    void aceptar(Visitante visitante);
}
