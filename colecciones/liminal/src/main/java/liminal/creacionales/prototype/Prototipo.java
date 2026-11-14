package liminal.creacionales.prototype;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


interface Prototipo<T> {
    T copiarConId(String nuevoId);
}
