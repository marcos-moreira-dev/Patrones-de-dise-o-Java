package liminal.comportamiento.visitor;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;



interface Visitante {
    void visitar(Puerta puerta);
    void visitar(Luz luz);
    void visitar(Linterna linterna);
}
