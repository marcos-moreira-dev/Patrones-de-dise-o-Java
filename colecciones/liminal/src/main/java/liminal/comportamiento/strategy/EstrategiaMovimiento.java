package liminal.comportamiento.strategy;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;



interface EstrategiaMovimiento {
    String elegirDestino(MapaLiminal mapa, String origen, String objetivo);
}
